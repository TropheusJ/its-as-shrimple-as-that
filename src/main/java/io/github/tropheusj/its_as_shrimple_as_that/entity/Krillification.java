package io.github.tropheusj.its_as_shrimple_as_that.entity;

import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEffects;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEntities;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatGameRules;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatTriggers;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntitySpawnReason;

import net.minecraft.world.entity.Mob;

import net.minecraft.world.entity.ai.control.FlyingMoveControl;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Krillification {
	// namespacing this (which adds a colon) breaks the command
	public static final String KRILLABLE_TAG = "krillable";

	public static void transform(LivingEntity entity, ServerLevel level, @Nullable Entity kriller, boolean force) {
		if (entity instanceof ShrimpEntity)
			return;

		if (!force && level.getGameRules().getRule(ItsAsShrimpleAsThatGameRules.KRILLING_REQUIRES_TAG).get()) {
			if (!entity.getTags().contains(KRILLABLE_TAG)) {
				return;
			}
		}

		if (kriller instanceof ServerPlayer player) {
			ItsAsShrimpleAsThatTriggers.KRILL.trigger(player);
		}

		// particles
		AABB bounds = entity.getBoundingBox();
		BlockPos.betweenClosedStream(bounds).forEach(pos -> {
			Vec3 center = Vec3.atCenterOf(pos);
			level.sendParticles(
					ParticleTypes.POOF,
					center.x, center.y, center.z,
					7, // count
					0.5, 0.5, 0.5, // offset
					0.1 // max speed
			);
		});

		// sound
		level.playSound(
				null, entity.blockPosition(),
				SoundEvents.ZOMBIE_VILLAGER_CURE,
				entity.getSoundSource(),
                1 + entity.getRandom().nextFloat(),
				(float) (entity.getRandom().nextFloat() * 0.7 + 0.3)
		);

		if (entity instanceof ServerPlayer player) {
			transformPlayer(player, kriller);
			return;
		}

		convert(entity);
	}

	private static void transformPlayer(ServerPlayer player, Entity kriller) {
		player.addEffect(new MobEffectInstance(ItsAsShrimpleAsThatEffects.KRILLED, 30 * 20));
		if (player == kriller) {
			// krilled self
			ItsAsShrimpleAsThatTriggers.KRILL_SELF.trigger(player);
		}
	}

	/**
	 * Creates and spawns a new shrimp entity in the given entity's place.
	 */
	private static void convert(LivingEntity entity) {
		if (entity instanceof Mob mob) {
			ConversionParams params = ConversionParams.single(mob, false, false);
			// convertTo adds the entity to the level
			mob.convertTo(ItsAsShrimpleAsThatEntities.SHRIMP, params, shrimp -> {
				// FlyingMoveControl enables noGravity, and conversion copies that.
				// hilarity ensues, but probably for the best to fix that.
				if (mob.getMoveControl() instanceof FlyingMoveControl) {
					shrimp.setNoGravity(false);
				}
			});
		} else {
			ServerLevel level = (ServerLevel) entity.level();
			ShrimpEntity krill = ItsAsShrimpleAsThatEntities.SHRIMP.create(level, EntitySpawnReason.CONVERSION);
			if (krill != null) {
				krill.setPos(entity.position());
				level.addFreshEntity(krill);
				entity.discard();
			}
		}
	}
}

package io.github.tropheusj.its_as_shrimple_as_that.entity;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;

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

	public static void transform(LivingEntity entity, ServerLevel level, @Nullable Entity kriller) {
		if (entity instanceof ShrimpEntity)
			return;

		if (level.getGameRules().getRule(ItsAsShrimpleAsThat.KRILLING_REQUIRES_TAG).get()) {
			if (!entity.getTags().contains(KRILLABLE_TAG)) {
				return;
			}
		}

		if (kriller instanceof ServerPlayer player)
			ItsAsShrimpleAsThat.KRILL_TRIGGER.trigger(player);

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

		// replace existing entity
		ShrimpEntity krill = ItsAsShrimpleAsThat.SHRIMP_TYPE.create(level);
		if (krill != null) {
			krill.setPos(entity.position());
			level.addFreshEntity(krill);
			entity.discard();
		}
	}

	private static void transformPlayer(ServerPlayer player, Entity kriller) {
		player.addEffect(new MobEffectInstance(ItsAsShrimpleAsThat.KRILLED, 30 * 20));
		if (player == kriller) {
			// krilled self
			ItsAsShrimpleAsThat.KRILL_SELF_TRIGGER.trigger(player);
		}
	}
}

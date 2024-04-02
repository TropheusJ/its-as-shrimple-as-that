package io.github.tropheusj.its_as_shrimple_as_that.entity;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Krillification {
	public static void transform(LivingEntity entity) {
		if (!(entity.level() instanceof ServerLevel level))
			throw new IllegalStateException();

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
			transformPlayer(player);
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

	private static void transformPlayer(ServerPlayer player) {
		// apply status effect
		Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ItsAsShrimpleAsThat.KRILLED);
		player.addEffect(new MobEffectInstance(holder, 30 * 20));
	}
}

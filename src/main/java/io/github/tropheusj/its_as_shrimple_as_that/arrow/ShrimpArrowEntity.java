package io.github.tropheusj.its_as_shrimple_as_that.arrow;

import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import io.github.tropheusj.its_as_shrimple_as_that.entity.Krillification;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

public class ShrimpArrowEntity extends AbstractArrow {
	public ShrimpArrowEntity(EntityType<? extends ShrimpArrowEntity> entityType, Level level) {
		super(entityType, level);
		this.setSilent(true);
	}

	@Override
	protected void onHitBlock(BlockHitResult hit) {
		if (this.level().isClientSide())
			return;
		// free him
		BlockPos offset = hit.getBlockPos().relative(hit.getDirection());
		this.spawnShrimp(Vec3.atBottomCenterOf(offset));
	}

	@Override
	protected void onHitEntity(EntityHitResult hit) {
		if (!(this.level() instanceof ServerLevel level))
			return;
		if (hit.getEntity() instanceof LivingEntity living)
			Krillification.transform(living, level, this.getOwner(), false);
		this.spawnShrimp(hit.getLocation());
		this.discard();
	}

	private void spawnShrimp(Vec3 pos) {
		ShrimpEntity shrimp = ItsAsShrimpleAsThatEntities.SHRIMP.create(this.level(), EntitySpawnReason.TRIGGERED);
		if (shrimp != null) {
			shrimp.snapTo(pos);
			this.level().addFreshEntity(shrimp);
			this.discard();
		}
	}

	@Override
	@NotNull
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(Items.ARROW);
	}
}

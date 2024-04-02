package io.github.tropheusj.its_as_shrimple_as_that.arrow;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import io.github.tropheusj.its_as_shrimple_as_that.entity.Krillification;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.phys.EntityHitResult;

import org.jetbrains.annotations.NotNull;

public class ShrimpArrowEntity extends AbstractArrow {
	public ShrimpArrowEntity(EntityType<? extends ShrimpArrowEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void onHitBlock(BlockHitResult hit) {
		super.onHitBlock(hit);
		// free him
		BlockPos offset = hit.getBlockPos().relative(hit.getDirection());
		ShrimpEntity krill = ItsAsShrimpleAsThat.SHRIMP_TYPE.create(this.level());
		if (krill != null) {
			krill.moveTo(offset, 0, 0);
			this.level().addFreshEntity(krill);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hit) {
		super.onHitEntity(hit);
		if (this.level().isClientSide)
			return;
		if (this.ownedBy(hit.getEntity())) {
			// krilled self
		}
		if (hit.getEntity() instanceof LivingEntity living)
			Krillification.transform(living);
	}

	@Override
	@NotNull
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(Items.ARROW);
	}
}

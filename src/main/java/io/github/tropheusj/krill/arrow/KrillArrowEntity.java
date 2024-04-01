package io.github.tropheusj.krill.arrow;

import io.github.tropheusj.krill.Krill;
import io.github.tropheusj.krill.entity.KrillEntity;
import io.github.tropheusj.krill.entity.Krilling;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.phys.EntityHitResult;

import org.jetbrains.annotations.NotNull;

public class KrillArrowEntity extends AbstractArrow {
	public KrillArrowEntity(EntityType<? extends KrillArrowEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void onHitBlock(BlockHitResult hit) {
		super.onHitBlock(hit);
		// free him
		BlockPos offset = hit.getBlockPos().relative(hit.getDirection());
		KrillEntity krill = Krill.KRILL_TYPE.create(this.level());
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
			Krilling.transform(living);
	}

	@Override
	@NotNull
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(Items.ARROW);
	}
}

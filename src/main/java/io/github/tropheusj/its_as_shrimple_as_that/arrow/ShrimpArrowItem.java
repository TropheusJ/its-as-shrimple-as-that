package io.github.tropheusj.its_as_shrimple_as_that.arrow;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class ShrimpArrowItem extends ArrowItem {
	public ShrimpArrowItem(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity shooter, @Nullable ItemStack itemStack2) {
		ShrimpArrowEntity arrow = new ShrimpArrowEntity(ItsAsShrimpleAsThat.SHRIMP_ARROW_TYPE, level);
		arrow.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
		arrow.setOwner(shooter);
		return arrow;
	}
}

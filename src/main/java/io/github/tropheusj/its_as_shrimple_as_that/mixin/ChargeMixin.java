package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import com.llamalad7.mixinextras.sugar.Local;

import io.github.tropheusj.its_as_shrimple_as_that.item.ShrimpCrossbowChargeType;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.Charge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;

import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Charge.class)
public class ChargeMixin {
	@Inject(
			method = "get(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/entity/LivingEntity;ILnet/minecraft/world/item/ItemDisplayContext;)Lnet/minecraft/world/item/CrossbowItem$ChargeType;",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/component/ChargedProjectiles;contains(Lnet/minecraft/world/item/Item;)Z"
			),
			cancellable = true
	)
	private void handleShrimp(ItemStack stack, ClientLevel level, LivingEntity entity, int i, ItemDisplayContext ctx,
							  CallbackInfoReturnable<CrossbowItem.ChargeType> cir, @Local ChargedProjectiles projectiles) {
		if (projectiles.contains(ItsAsShrimpleAsThatItems.SHRIMP_ARROW)) {
			cir.setReturnValue(ShrimpCrossbowChargeType.INSTANCE);
		}
	}
}

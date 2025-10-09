package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
	@ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
	private static AttributeSupplier.Builder addKrilledAttribute(AttributeSupplier.Builder builder) {
		builder.add(ItsAsShrimpleAsThatAttributes.KRILLED, 0);
		return builder;
	}
}

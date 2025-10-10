package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.world.item.CrossbowItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(CrossbowItem.ChargeType.class)
public class CrossbowItem$ChargeTypeMixin {
	@Invoker("<init>")
	private static CrossbowItem.ChargeType create(String name, int ordinal, String serializedName) {
		throw new AbstractMethodError();
	}

	@ModifyReturnValue(method = "$values", at = @At("RETURN"))
	private static CrossbowItem.ChargeType[] addShrimp(CrossbowItem.ChargeType[] original) {
		CrossbowItem.ChargeType[] newValues = Arrays.copyOf(original, original.length + 1);
		CrossbowItem.ChargeType shrimp = create(
				"ITS_AS_SHRIMPLE_AS_THAT$SHRIMP", original.length, "its_as_shrimple_as_that:shrimp"
		);
		newValues[shrimp.ordinal()] = shrimp;
		return newValues;
	}
}

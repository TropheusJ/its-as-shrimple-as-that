package io.github.tropheusj.its_as_shrimple_as_that.registry;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class ItsAsShrimpleAsThatEffects {
	public static final Holder<MobEffect> KRILLED = Registry.registerForHolder(
			BuiltInRegistries.MOB_EFFECT,
			ItsAsShrimpleAsThat.id("krilled"),
			new MobEffect(MobEffectCategory.BENEFICIAL, 0xFF977C66){}
					.addAttributeModifier(
							Attributes.SCALE, ItsAsShrimpleAsThatAttributeModifiers.KRILLED_EFFECT_SIZE,
							-0.75, AttributeModifier.Operation.ADD_VALUE
					).addAttributeModifier(
							ItsAsShrimpleAsThatAttributes.KRILLED, ItsAsShrimpleAsThatAttributeModifiers.KRILLED_EFFECT_KRILLED,
							1, AttributeModifier.Operation.ADD_VALUE
					)
	);

	public static void init() {
	}
}

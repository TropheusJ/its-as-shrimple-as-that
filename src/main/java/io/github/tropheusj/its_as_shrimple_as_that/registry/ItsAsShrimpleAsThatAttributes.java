package io.github.tropheusj.its_as_shrimple_as_that.registry;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public final class ItsAsShrimpleAsThatAttributes {
	// this attribute is used as a flag to determine if players are krilled,
	// since effects on remote players aren't synced, just their attributes.
	// this is done instead of adding a flag so it stays in sync with the status effect.
	public static final Holder<Attribute> KRILLED = Registry.registerForHolder(
			BuiltInRegistries.ATTRIBUTE, ItsAsShrimpleAsThat.id("krilled"), new RangedAttribute(
					"attribute.name.its_as_shrimple_as_that.krilled",
					0, 0, 1
			).setSyncable(true)
	);

	public static void init() {
	}
}

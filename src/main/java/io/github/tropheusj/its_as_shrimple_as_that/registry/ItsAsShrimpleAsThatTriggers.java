package io.github.tropheusj.its_as_shrimple_as_that.registry;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.KrillSelfTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.KrillTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.LoadShrimpTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.ShrimpAccomplishDreamsTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public final class ItsAsShrimpleAsThatTriggers {
	public static final KrillTrigger KRILL = register("krill", new KrillTrigger());
	public static final KrillSelfTrigger KRILL_SELF = register("krill_self", new KrillSelfTrigger());
	public static final LoadShrimpTrigger LOAD_SHRIMP = register("load_shrimp", new LoadShrimpTrigger());
	public static final ShrimpAccomplishDreamsTrigger ACCOMPLISH_DREAMS = register(
			"accomplish_dreams", new ShrimpAccomplishDreamsTrigger()
	);

	public static void init() {
	}

	private static <T extends CriterionTrigger<?>> T register(String name, T trigger) {
		ResourceLocation id = ItsAsShrimpleAsThat.id(name);
		return Registry.register(BuiltInRegistries.TRIGGER_TYPES, id, trigger);
	}
}

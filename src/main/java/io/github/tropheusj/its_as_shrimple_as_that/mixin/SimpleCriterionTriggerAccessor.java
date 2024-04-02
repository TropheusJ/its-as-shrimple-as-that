package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

@Mixin(SimpleCriterionTrigger.class)
public interface SimpleCriterionTriggerAccessor<T extends SimpleCriterionTrigger.SimpleInstance> {
	@Invoker
	void callTrigger(ServerPlayer serverPlayer, Predicate<T> predicate);
}

package io.github.tropheusj.its_as_shrimple_as_that.criteria;

import java.util.Optional;

import com.mojang.serialization.Codec;

import io.github.tropheusj.its_as_shrimple_as_that.criteria.KrillSelfTrigger.TriggerInstance;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class KrillSelfTrigger extends SimpleCriterionTrigger<TriggerInstance> {
	public void trigger(ServerPlayer player) {
		this.trigger(player, instance -> true);
	}

	@Override
	@NotNull
	public Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public record TriggerInstance() implements SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = Codec.unit(new TriggerInstance());

		@Override
		@NotNull
		public Optional<ContextAwarePredicate> player() {
			return Optional.empty();
		}
	}
}

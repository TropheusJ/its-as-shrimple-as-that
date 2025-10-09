package io.github.tropheusj.its_as_shrimple_as_that.registry;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public final class ItsAsShrimpleAsThatGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> KRILLING_REQUIRES_TAG = GameRuleRegistry.register(
			ItsAsShrimpleAsThat.ID + ":krillingRequiresTag", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false)
	);

	public static void init() {
	}
}

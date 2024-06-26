package io.github.tropheusj.its_as_shrimple_as_that.item;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FriedRiceItem extends Item {
	public FriedRiceItem(Properties properties) {
		super(properties);
	}

	public static Properties makeProperties() {
		return new Properties().stacksTo(1).food(new FoodProperties.Builder().nutrition(5).build());
	}

	@Override
	@NotNull
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (entity instanceof ServerPlayer player) {
			CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
			player.awardStat(Stats.ITEM_USED.get(this));
		}

		stack.consume(1, entity);

		return stack.isEmpty() ? new ItemStack(Items.BOWL) : stack;
	}
}

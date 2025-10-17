package io.github.tropheusj.its_as_shrimple_as_that.registry;

import java.util.function.Consumer;
import java.util.function.Function;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowItem;
import io.github.tropheusj.its_as_shrimple_as_that.item.FriedRiceItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public final class ItsAsShrimpleAsThatItems {
	public static final Item FRIED_RICE = register(
			"fried_rice", FriedRiceItem::new,
			properties -> properties.stacksTo(1)
					.food(new FoodProperties.Builder()
							.nutrition(5)
							.build()
					)
	);

	public static final Item SHRIMP_ARROW = register("shrimp_arrow", ShrimpArrowItem::new);

	public static final Item SHRIMP_EGG = register(
			"shrimp_spawn_egg", SpawnEggItem::new,
			properties -> properties.spawnEgg(ItsAsShrimpleAsThatEntities.SHRIMP)
	);

	public static final Item RAW_SHRIMP = register("raw_shrimp", Item::new, properties -> properties.food((new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build()));
	public static final Item COOKED_SHRIMP = register("cooked_shrimp", Item::new, properties -> properties.food((new FoodProperties.Builder()).nutrition(8).saturationModifier(0.8F).build()));

	public static final CreativeModeTab SHRIMPLE_ITEMS = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ItsAsShrimpleAsThat.id("shrimple_items"),
			CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
					.icon(() -> new ItemStack(RAW_SHRIMP))
					.title(Component.translatable("itemGroup.its_as_shrimple_as_that.shrimple_items"))
					.displayItems((params, output) -> {
						output.accept(FRIED_RICE);
						output.accept(SHRIMP_ARROW);
						output.accept(SHRIMP_EGG);
						output.accept(RAW_SHRIMP);
						output.accept(COOKED_SHRIMP);
					})
					.build());

	public static void init() {

	}

	private static Item register(String name, Function<Item.Properties, Item> factory) {
		return register(name, factory, properties -> {});
	}

	private static Item register(String name, Function<Item.Properties, Item> factory, Consumer<Item.Properties> consumer) {
		ResourceLocation id = ItsAsShrimpleAsThat.id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);

		Item.Properties properties = new Item.Properties();
		properties.setId(key);
		consumer.accept(properties);

		return Registry.register(BuiltInRegistries.ITEM, id, factory.apply(properties));
	}
}

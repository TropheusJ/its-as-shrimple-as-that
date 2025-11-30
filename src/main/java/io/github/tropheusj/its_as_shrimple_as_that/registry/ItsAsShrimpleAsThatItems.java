package io.github.tropheusj.its_as_shrimple_as_that.registry;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Consumer;
import java.util.function.Function;

public final class ItsAsShrimpleAsThatItems {
	public static final Item FRIED_RICE = register(
			"fried_rice", Item::new,
			properties -> properties.stacksTo(1)
					.food(new FoodProperties.Builder()
							.nutrition(6)
							.saturationModifier(0.6f)
							.build()
					).usingConvertsTo(Items.BOWL)
	);

	public static final Item SHRIMP_ARROW = register("shrimp_arrow", ShrimpArrowItem::new);

	public static final Item SHRIMP_EGG = register(
			"shrimp_spawn_egg", SpawnEggItem::new,
			properties -> properties.spawnEgg(ItsAsShrimpleAsThatEntities.SHRIMP)
	);

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

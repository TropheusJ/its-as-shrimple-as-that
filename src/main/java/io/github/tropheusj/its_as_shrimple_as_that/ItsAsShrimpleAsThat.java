package io.github.tropheusj.its_as_shrimple_as_that;

import io.github.tropheusj.its_as_shrimple_as_that.command.KrillCommand;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatAttributes;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEffects;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEntities;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatGameRules;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatItems;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatTriggers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class ItsAsShrimpleAsThat implements ModInitializer {
	public static final String ID = "its_as_shrimple_as_that";

	@Override
	public void onInitialize() {
		ItsAsShrimpleAsThatAttributes.init();
		ItsAsShrimpleAsThatEffects.init();
		ItsAsShrimpleAsThatEntities.init();
		ItsAsShrimpleAsThatGameRules.init();
		ItsAsShrimpleAsThatItems.init();
		ItsAsShrimpleAsThatTriggers.init();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(
				entries -> entries.addAfter(Items.RABBIT_STEW, ItsAsShrimpleAsThatItems.FRIED_RICE)
		);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(
				entries -> entries.accept(ItsAsShrimpleAsThatItems.SHRIMP_EGG)
		);

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> dispatcher.register(KrillCommand.build())
		);

		FabricDefaultAttributeRegistry.register(ItsAsShrimpleAsThatEntities.SHRIMP, ShrimpEntity.createAttributes().build());

		BiomeModifications.addSpawn(
				BiomeSelectors.tag(BiomeTags.IS_OCEAN),
				MobCategory.WATER_AMBIENT, ItsAsShrimpleAsThatEntities.SHRIMP,
				5, 3, 5
		);

		SpawnPlacements.register(ItsAsShrimpleAsThatEntities.SHRIMP, SpawnPlacementTypes.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, ShrimpEntity::checkShrimpSpawnRules);
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}

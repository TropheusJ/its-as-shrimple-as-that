package io.github.tropheusj.its_as_shrimple_as_that;

import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowEntity;
import io.github.tropheusj.its_as_shrimple_as_that.command.KrillCommand;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsAsShrimpleAsThat implements ModInitializer {
	public static final String ID = "its_as_shrimple_as_that";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final EntityType<ShrimpEntity> SHRIMP_TYPE = FabricEntityTypeBuilder.create()
			.entityFactory(ShrimpEntity::new)
			.spawnGroup(MobCategory.WATER_AMBIENT)
			.dimensions(EntityDimensions.fixed(0.9f, 0.5f))
			.build();

	public static final EntityType<ShrimpArrowEntity> SHRIMP_ARROW_TYPE = FabricEntityTypeBuilder.create()
			.entityFactory(ShrimpArrowEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.build();

	public static final Item SHRIMP_ARROW = new ArrowItem(new Item.Properties());

	public static final MobEffect KRILLED = new MobEffect(MobEffectCategory.BENEFICIAL, 1){}.addAttributeModifier(
			Attributes.SCALE, "5876bde6-b02e-42e9-84c4-e3317b37cb26",
			-0.66, AttributeModifier.Operation.ADD_VALUE
	);

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id("shrimp"), SHRIMP_TYPE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id("shrimp_arrow"), SHRIMP_ARROW_TYPE);
		Registry.register(BuiltInRegistries.ITEM, id("shrimp_arrow"), SHRIMP_ARROW);
		Registry.register(BuiltInRegistries.MOB_EFFECT, id("krilled"), KRILLED);

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> dispatcher.register(KrillCommand.build())
		);

		FabricDefaultAttributeRegistry.register(SHRIMP_TYPE, ShrimpEntity.createAttributes().build());
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}

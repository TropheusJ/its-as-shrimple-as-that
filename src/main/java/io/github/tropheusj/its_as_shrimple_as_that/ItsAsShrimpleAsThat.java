package io.github.tropheusj.its_as_shrimple_as_that;

import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowEntity;
import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowItem;
import io.github.tropheusj.its_as_shrimple_as_that.command.KrillCommand;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.LoadShrimpTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.KrillSelfTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.KrillTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.criteria.ShrimpAccomplishDreamsTrigger;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import io.github.tropheusj.its_as_shrimple_as_that.item.FriedRiceItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsAsShrimpleAsThat implements ModInitializer {
	public static final String ID = "its_as_shrimple_as_that";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final EntityType<ShrimpEntity> SHRIMP_TYPE = EntityType.Builder.of(
			ShrimpEntity::new, MobCategory.WATER_AMBIENT
			).sized(0.9f, 0.5f).build();

	public static final EntityType<ShrimpArrowEntity> SHRIMP_ARROW_TYPE = EntityType.Builder.of(
			ShrimpArrowEntity::new, MobCategory.MISC
	).sized(0.5f, 0.5f).build();

	public static final Item FRIED_RICE = new FriedRiceItem(FriedRiceItem.makeProperties());

	public static final Item SHRIMP_ARROW = new ShrimpArrowItem(new Item.Properties());

	public static final Item SHRIMP_EGG = new SpawnEggItem(SHRIMP_TYPE, 0xFF977C66, 0xFFFFFFFF, new Properties());

	// this attribute is used as a flag to determine if players are krilled,
	// since effects aren't synced, just their attributes.
	// this is done instead of adding a flag so it stays in sync with the status effect.
	public static final Holder<Attribute> KRILLED_ATTRIBUTE = Registry.registerForHolder(
			BuiltInRegistries.ATTRIBUTE, id("krilled"), new RangedAttribute(
					"attribute.name.its_as_shrimple_as_that.krilled",
					0, 0, 1
			).setSyncable(true)
	);

	public static final ResourceLocation KRILLED_ATTRIBUTE_MODIFIER = id("krilled");
	public static final ResourceLocation KRILLED_SIZE_MODIFIER = id("krilled_size_modifier");

	public static final Holder<MobEffect> KRILLED_EFFECT = Registry.registerForHolder(
			BuiltInRegistries.MOB_EFFECT,
			id("krilled"),
			new MobEffect(MobEffectCategory.BENEFICIAL, 0xFF977C66){}
					.addAttributeModifier(
							Attributes.SCALE, KRILLED_SIZE_MODIFIER,
							-0.75, AttributeModifier.Operation.ADD_VALUE
					).addAttributeModifier(
							KRILLED_ATTRIBUTE, KRILLED_ATTRIBUTE_MODIFIER,
							1, AttributeModifier.Operation.ADD_VALUE
					)
	);

	public static final ShrimpAccomplishDreamsTrigger ACCOMPLISH_DREAMS_TRIGGER = new ShrimpAccomplishDreamsTrigger();
	public static final KrillTrigger KRILL_TRIGGER = new KrillTrigger();
	public static final KrillSelfTrigger KRILL_SELF_TRIGGER = new KrillSelfTrigger();
	public static final LoadShrimpTrigger LOAD_SHRIMP_TRIGGER = new LoadShrimpTrigger();

	public static final GameRules.Key<GameRules.BooleanValue> KRILLING_REQUIRES_TAG = GameRuleRegistry.register(
			ID + ":krillingRequiresTag", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false)
	);

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id("shrimp"), SHRIMP_TYPE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id("shrimp_arrow"), SHRIMP_ARROW_TYPE);
		Registry.register(BuiltInRegistries.ITEM, id("shrimp_arrow"), SHRIMP_ARROW);
		Registry.register(BuiltInRegistries.ITEM, id("fried_rice"), FRIED_RICE);
		Registry.register(BuiltInRegistries.ITEM, id("shrimp_spawn_egg"), SHRIMP_EGG);
		Registry.register(BuiltInRegistries.TRIGGER_TYPES, id("accomplish_dreams"), ACCOMPLISH_DREAMS_TRIGGER);
		Registry.register(BuiltInRegistries.TRIGGER_TYPES, id("krill"), KRILL_TRIGGER);
		Registry.register(BuiltInRegistries.TRIGGER_TYPES, id("krill_self"), KRILL_SELF_TRIGGER);
		Registry.register(BuiltInRegistries.TRIGGER_TYPES, id("load_shrimp"), LOAD_SHRIMP_TRIGGER);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> entries.accept(SHRIMP_EGG));

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> dispatcher.register(KrillCommand.build())
		);

		FabricDefaultAttributeRegistry.register(SHRIMP_TYPE, ShrimpEntity.createAttributes().build());

		BiomeModifications.addSpawn(
				BiomeSelectors.tag(BiomeTags.IS_OCEAN),
				MobCategory.WATER_AMBIENT, SHRIMP_TYPE,
				5, 3, 5
		);

		SpawnPlacements.register(SHRIMP_TYPE, SpawnPlacementTypes.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, ShrimpEntity::checkShrimpSpawnRules);
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}

package io.github.tropheusj.krill;

import io.github.tropheusj.krill.arrow.KrillArrowEntity;
import io.github.tropheusj.krill.entity.KrillEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Krill implements ModInitializer {
	public static final String ID = "krill";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final EntityType<KrillEntity> KRILL_TYPE = FabricEntityTypeBuilder.create()
			.entityFactory(KrillEntity::new)
			.spawnGroup(MobCategory.WATER_AMBIENT)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.build();

	public static final EntityType<KrillArrowEntity> KRILL_ARROW_TYPE = FabricEntityTypeBuilder.create()
			.entityFactory(KrillArrowEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.build();

	public static final Item KRILL_ARROW = new ArrowItem(new Item.Properties());

	public static final MobEffect KRILLED = new MobEffect(MobEffectCategory.BENEFICIAL, 1){}.addAttributeModifier(
			Attributes.SCALE, "5876bde6-b02e-42e9-84c4-e3317b37cb26",
			-0.66, AttributeModifier.Operation.ADD_VALUE
	);

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id("krill"), KRILL_TYPE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id("krill_arrow"), KRILL_ARROW_TYPE);
		Registry.register(BuiltInRegistries.ITEM, id("krill_arrow"), KRILL_ARROW);
		Registry.register(BuiltInRegistries.MOB_EFFECT, id("krilled"), KRILLED);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}

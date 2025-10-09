package io.github.tropheusj.its_as_shrimple_as_that.registry;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowEntity;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ItsAsShrimpleAsThatEntities {
	public static final EntityType<ShrimpEntity> SHRIMP = register(
			"shrimp", ShrimpEntity::new, MobCategory.WATER_AMBIENT,
			0.9f, 0.5f
	);
	public static final EntityType<ShrimpArrowEntity> SHRIMP_ARROW = register(
			"shrimp_arrow", ShrimpArrowEntity::new, MobCategory.MISC,
			0.5f, 0.5f
	);

	public static void init() {
	}

	private static <T extends Entity> EntityType<T> register(String name, EntityType.EntityFactory<T> factory,
															 MobCategory category, float width, float height) {
		ResourceLocation id = ItsAsShrimpleAsThat.id(name);
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);

		EntityType.Builder<T> builder = EntityType.Builder.of(factory, category);
		builder.sized(width, height);
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(key));
	}
}

package io.github.tropheusj.its_as_shrimple_as_that;

import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

public class ItsAsShrimpleAsThatClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemProperties.register(Items.CROSSBOW, ItsAsShrimpleAsThat.id("krill"), (stack, level, entity, i) -> {
			ChargedProjectiles projectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
			return projectiles != null && projectiles.contains(Items.FIREWORK_ROCKET) ? 1 : 0;
		});

		EntityModelLayerRegistry.registerModelLayer(ShrimpModel.LAYER_LOCATION, ShrimpModel::createBodyLayer);

		EntityRendererRegistry.register(ItsAsShrimpleAsThat.SHRIMP_TYPE, ShrimpRenderer::new);
		EntityRendererRegistry.register(ItsAsShrimpleAsThat.SHRIMP_ARROW_TYPE, NoopRenderer::new);
	}
}

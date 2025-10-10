package io.github.tropheusj.its_as_shrimple_as_that;

import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowRenderer;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;
import io.github.tropheusj.its_as_shrimple_as_that.item.ShrimpSpecialRenderer;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class ItsAsShrimpleAsThatClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(ShrimpModel.LAYER_LOCATION, ShrimpModel::createBodyLayer);

		EntityRenderers.register(ItsAsShrimpleAsThatEntities.SHRIMP, ShrimpRenderer::new);
		EntityRenderers.register(ItsAsShrimpleAsThatEntities.SHRIMP_ARROW, ShrimpArrowRenderer::new);

		SpecialModelRenderers.ID_MAPPER.put(ItsAsShrimpleAsThat.id("shrimp"), ShrimpSpecialRenderer.Unbaked.CODEC);
	}
}

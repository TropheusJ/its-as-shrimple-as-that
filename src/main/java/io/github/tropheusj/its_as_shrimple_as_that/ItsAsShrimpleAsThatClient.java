package io.github.tropheusj.its_as_shrimple_as_that;

import io.github.tropheusj.its_as_shrimple_as_that.arrow.ShrimpArrowRenderer;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

import net.minecraft.client.renderer.entity.EntityRenderers;

public class ItsAsShrimpleAsThatClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// ItemProperties.register(Items.CROSSBOW, ItsAsShrimpleAsThat.id("shrimp"), (stack, level, entity, i) -> {
		// 	ChargedProjectiles projectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
		// 	return projectiles != null && projectiles.contains(ItsAsShrimpleAsThatItems.SHRIMP_ARROW) ? 1 : 0;
		// });

		EntityModelLayerRegistry.registerModelLayer(ShrimpModel.LAYER_LOCATION, ShrimpModel::createBodyLayer);

		EntityRenderers.register(ItsAsShrimpleAsThatEntities.SHRIMP, ShrimpRenderer::new);
		EntityRenderers.register(ItsAsShrimpleAsThatEntities.SHRIMP_ARROW, ShrimpArrowRenderer::new);

		// BuiltinItemRendererRegistry.INSTANCE.register(Items.CROSSBOW, new ShrimpCrossbowRenderer());
	}
}

package io.github.tropheusj.krill;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

public class KrillClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemProperties.register(Items.CROSSBOW, Krill.id("krill"), (stack, level, entity, i) -> {
			ChargedProjectiles projectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
			return projectiles != null && projectiles.contains(Items.FIREWORK_ROCKET) ? 1 : 0;
		});

		EntityRendererRegistry.register(Krill.KRILL_TYPE, NoopRenderer::new);
		EntityRendererRegistry.register(Krill.KRILL_ARROW_TYPE, NoopRenderer::new);
	}
}

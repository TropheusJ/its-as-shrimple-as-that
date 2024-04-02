package io.github.tropheusj.its_as_shrimple_as_that.item;

import java.util.Objects;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import com.mojang.math.Axis;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

public class ShrimpCrossbowRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
	private static final ItemStack normalCrossbow = Util.make(
			new ItemStack(Items.CROSSBOW),
			stack -> stack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(new ItemStack(Items.ARROW)))
	);

	private static final Supplier<ShrimpEntity> shrimp = Suppliers.memoize(() -> {
		ClientLevel level = Minecraft.getInstance().level;
		Objects.requireNonNull(level);
		return new ShrimpEntity(ItsAsShrimpleAsThat.SHRIMP_TYPE, level);
	});

	@Override
	public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
		Minecraft mc = Minecraft.getInstance();
		EntityRenderDispatcher entityRenderer = mc.getEntityRenderDispatcher();
		ItemRenderer itemRenderer = mc.getItemRenderer();

		matrices.pushPose();
		matrices.translate(0.5, 0.5, 0.5);

		matrices.pushPose();
		itemRenderer.renderStatic(normalCrossbow, mode, light, overlay, matrices, buffers, null, 0);
		matrices.popPose();

		matrices.scale(0.5f, 0.5f, 0.5f);
		matrices.mulPose(Axis.YP.rotationDegrees(170));
		matrices.translate(-0.17, 0.5, -0.3);

		entityRenderer.render(shrimp.get(), 0, 0, 0, 0, 1, matrices, buffers, 15728880);
		matrices.popPose();
	}
}

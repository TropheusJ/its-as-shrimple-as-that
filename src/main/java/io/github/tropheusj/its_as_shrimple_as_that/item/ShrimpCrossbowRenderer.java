package io.github.tropheusj.its_as_shrimple_as_that.item;

import java.util.Map;
import com.google.common.collect.MapMaker;
import com.mojang.math.Axis;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;

public class ShrimpCrossbowRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
	private static final ItemStack normalCrossbow = Util.make(
			new ItemStack(Items.CROSSBOW),
			stack -> stack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(new ItemStack(Items.ARROW)))
	);

	private static final Map<Level, ShrimpEntity> shrimpCache = new MapMaker().weakKeys().makeMap();

	@Override
	public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
		Minecraft mc = Minecraft.getInstance();
		EntityRenderDispatcher entityRenderer = mc.getEntityRenderDispatcher();
		ItemRenderer itemRenderer = mc.getItemRenderer();

		matrices.pushPose();
		matrices.translate(0.5, 0.5, 0.5);

		BakedModel crossbowModel = itemRenderer.getModel(normalCrossbow, null, null, 0);
		boolean leftHand = mode == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || mode == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

		matrices.pushPose();
		Lighting.setupForFlatItems();
		itemRenderer.render(normalCrossbow, mode, leftHand, matrices, buffers, light, overlay, crossbowModel);
		matrices.popPose();

		crossbowModel.getTransforms().getTransform(mode).apply(leftHand, matrices);
		matrices.mulPose(Axis.XP.rotationDegrees(95));
		matrices.mulPose(Axis.YP.rotationDegrees(225));
		matrices.mulPose(Axis.ZP.rotationDegrees(3));
		matrices.translate(0, 0, 0.1);

		Lighting.setupLevel();
		entityRenderer.render(getShrimp(), 0, 0, 0, 0, 1, matrices, buffers, light);
		matrices.popPose();
	}

	private static ShrimpEntity getShrimp() {
		return shrimpCache.computeIfAbsent(
				Minecraft.getInstance().level,
				level -> new ShrimpEntity(ItsAsShrimpleAsThat.SHRIMP_TYPE, level)
		);
	}
}

package io.github.tropheusj.its_as_shrimple_as_that.arrow;

import com.mojang.math.Axis;

import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ShrimpArrowRenderer extends EntityRenderer<ShrimpArrowEntity> {
	private final ShrimpModel<ShrimpArrowEntity> model;

	public ShrimpArrowRenderer(Context context) {
		super(context);
		this.model = new ShrimpModel<>(context);
	}

	@Override
	public void render(ShrimpArrowEntity shrimp, float f, float g, PoseStack matrices, MultiBufferSource buffers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, shrimp.yRotO, shrimp.getYRot()) - 90.0F));
		matrices.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, shrimp.xRotO, shrimp.getXRot())));

		matrices.mulPose(Axis.ZP.rotationDegrees(90));
		matrices.mulPose(Axis.YP.rotationDegrees(90));
		matrices.mulPose(Axis.XN.rotationDegrees(90));
		matrices.translate(0, -1.4, 0);

		VertexConsumer vertexConsumer = buffers.getBuffer(RenderType.entityCutout(this.getTextureLocation(shrimp)));

		this.model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
		matrices.popPose();
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(ShrimpArrowEntity entity) {
		return ShrimpRenderer.TEXTURE;
	}
}

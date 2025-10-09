package io.github.tropheusj.its_as_shrimple_as_that.arrow;

import com.mojang.math.Axis;

import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderState;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ShrimpArrowRenderer extends EntityRenderer<ShrimpArrowEntity, ShrimpRenderState> {
	private final ShrimpModel<ShrimpRenderState> model;

	public ShrimpArrowRenderer(Context context) {
		super(context);
		this.model = new ShrimpModel<>(context);
	}

	@Override
	public ShrimpRenderState createRenderState() {
		return new ShrimpRenderState();
	}

	@Override
	public void submit(ShrimpRenderState state, PoseStack matrices, SubmitNodeCollector collector, CameraRenderState camera) {
		super.submit(state, matrices, collector, camera);

		matrices.pushPose();
		// matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, shrimp.yRotO, shrimp.getYRot()) - 90.0F));
		// matrices.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, shrimp.xRotO, shrimp.getXRot())));

		matrices.mulPose(Axis.ZP.rotationDegrees(90));
		matrices.mulPose(Axis.YP.rotationDegrees(90));
		matrices.mulPose(Axis.XN.rotationDegrees(90));
		matrices.translate(0, -1.4, 0);

		RenderType renderType = this.model.renderType(ShrimpRenderer.TEXTURE);
		collector.submitModel(this.model, state, matrices, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF, null);
		matrices.popPose();
	}
}

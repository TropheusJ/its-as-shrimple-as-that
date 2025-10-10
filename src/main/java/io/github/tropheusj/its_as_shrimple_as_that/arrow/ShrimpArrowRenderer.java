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
import net.minecraft.util.Mth;

public class ShrimpArrowRenderer extends EntityRenderer<ShrimpArrowEntity, ShrimpRenderState> {
	private final ShrimpModel<ShrimpRenderState> model;

	public ShrimpArrowRenderer(Context context) {
		super(context);
		this.model = new ShrimpModel<>(context.getModelSet());
	}

	@Override
	public ShrimpRenderState createRenderState() {
		return new ShrimpRenderState();
	}

	@Override
	public void extractRenderState(ShrimpArrowEntity entity, ShrimpRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot =  entity.getYRot(partialTicks);
		state.xRot = entity.getXRot(partialTicks);
	}

	@Override
	public void submit(ShrimpRenderState state, PoseStack matrices, SubmitNodeCollector collector, CameraRenderState camera) {
		super.submit(state, matrices, collector, camera);

		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(state.yRot - 90));
		matrices.mulPose(Axis.ZP.rotationDegrees(state.xRot));
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		matrices.mulPose(Axis.YP.rotationDegrees(-90));
		matrices.translate(0, -1.4, 0);

		RenderType renderType = this.model.renderType(ShrimpRenderer.TEXTURE);
		collector.submitModel(this.model, state, matrices, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);
		matrices.popPose();
	}
}

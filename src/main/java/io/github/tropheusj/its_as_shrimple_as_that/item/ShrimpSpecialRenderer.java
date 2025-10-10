package io.github.tropheusj.its_as_shrimple_as_that.item;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;

import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;

import org.joml.Vector3f;

import java.util.Set;

public final class ShrimpSpecialRenderer implements NoDataSpecialModelRenderer {
	private final ShrimpModel<LivingEntityRenderState> model;
	private final LivingEntityRenderState state;

	public ShrimpSpecialRenderer(EntityModelSet models) {
		this.model = new ShrimpModel<>(models);
		this.state = new LivingEntityRenderState();
	}

	@Override
	public void submit(ItemDisplayContext ctx, PoseStack matrices, SubmitNodeCollector collector, int i, int j, boolean bl, int k) {
		matrices.pushPose();

		matrices.mulPose(Axis.XP.rotationDegrees(95));
		matrices.mulPose(Axis.YP.rotationDegrees(225));
		matrices.mulPose(Axis.ZP.rotationDegrees(3));
		matrices.translate(0, 0, 0.1);

		matrices.mulPose(Axis.YP.rotationDegrees(180));
		matrices.scale(-1, -1, 1);
		matrices.translate(-0.715, -2, 0);

		RenderType renderType = this.model.renderType(ShrimpRenderer.TEXTURE);
		collector.submitModel(this.model, this.state, matrices, renderType, i, j, 0, null);
		matrices.popPose();
	}

	@Override
	public void getExtents(Set<Vector3f> set) {
		this.model.root().getExtentsForGui(new PoseStack(), set);
	}

	public enum Unbaked implements SpecialModelRenderer.Unbaked {
		INSTANCE;

		public static final MapCodec<Unbaked> CODEC = MapCodec.unit(INSTANCE);

		@Override
		public SpecialModelRenderer<?> bake(BakingContext ctx) {
			return new ShrimpSpecialRenderer(ctx.entityModelSet());
		}

		@Override
		public MapCodec<? extends SpecialModelRenderer.Unbaked> type() {
			return CODEC;
		}
	}
}

package io.github.tropheusj.its_as_shrimple_as_that.entity.render;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShrimpRenderer extends MobRenderer<ShrimpEntity, ShrimpRenderState, ShrimpModel<ShrimpRenderState>> {
	public static final ResourceLocation TEXTURE = ItsAsShrimpleAsThat.id("textures/entity/shrimp/shrimp.png");
	public static final ResourceLocation CHEF_TEXTURE = ItsAsShrimpleAsThat.id("textures/entity/shrimp/chef_shrimp.png");
	public static final float SHADOW_RADIUS = 0.5f;

	public ShrimpRenderer(Context context) {
		super(context, new ShrimpModel<>(context), SHADOW_RADIUS);
	}

	@Override
	public ShrimpRenderState createRenderState() {
		return new ShrimpRenderState();
	}

	@Override
	public void extractRenderState(ShrimpEntity shrimp, ShrimpRenderState state, float partialTicks) {
		super.extractRenderState(shrimp, state, partialTicks);
		state.isChef = shrimp.isChef();
	}

	@Override
	public ResourceLocation getTextureLocation(ShrimpRenderState state) {
		return state.isChef ? CHEF_TEXTURE : TEXTURE;
	}
}

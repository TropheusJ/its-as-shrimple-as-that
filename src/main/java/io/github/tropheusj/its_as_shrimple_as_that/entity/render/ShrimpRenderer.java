package io.github.tropheusj.its_as_shrimple_as_that.entity.render;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShrimpRenderer extends LivingEntityRenderer<ShrimpEntity, ShrimpModel<ShrimpEntity>> {
	public static final ResourceLocation TEXTURE = ItsAsShrimpleAsThat.id("textures/entity/shrimp/shrimp.png");
	public static final ResourceLocation CHEF_TEXTURE = ItsAsShrimpleAsThat.id("textures/entity/shrimp/chef_shrimp.png");
	public static final float SHADOW_RADIUS = 0.5f;

	public ShrimpRenderer(Context context) {
		super(context, new ShrimpModel<>(context), SHADOW_RADIUS);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(ShrimpEntity entity) {
		return entity.isChef() ? CHEF_TEXTURE : TEXTURE;
	}
}

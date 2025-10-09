package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;

import io.github.tropheusj.its_as_shrimple_as_that.ext.AvatarRendererExtensions;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatRenderStateDataKeys;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
	@WrapOperation(
			method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"
			)
	)
	private void submitShrimp(SubmitNodeCollector collector, Model<?> model, Object context, PoseStack matrices,
							  RenderType renderType, int light, int overlay, int color, TextureAtlasSprite sprite,
							  int outlineColor, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, Operation<Void> original) {
		if (this instanceof AvatarRendererExtensions avatar) {
			EntityRenderState state = (EntityRenderState) context;
			if (state.getData(ItsAsShrimpleAsThatRenderStateDataKeys.KRILLED) != null) {
				model = avatar.its_as_shrimple_as_that$shrimpModel();
			}
		}

		original.call(collector, model, context, matrices, renderType, light, overlay, color, sprite, outlineColor, crumblingOverlay);
	}
}

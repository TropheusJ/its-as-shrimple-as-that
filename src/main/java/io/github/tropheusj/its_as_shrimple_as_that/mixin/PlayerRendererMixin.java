package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import com.mojang.math.Axis;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;

import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
	@Unique
	private ShrimpModel<Player> shrimpModel;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(Context context, boolean bl, CallbackInfo ci) {
		this.shrimpModel = new ShrimpModel<>(context);
	}

	@Inject(
			method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
			at = @At("HEAD"),
			cancellable = true
	)
	private void renderShrimpInstead(AbstractClientPlayer player, float f, float g, PoseStack matrices, MultiBufferSource buffers, int i, CallbackInfo ci) {
		if (player.hasEffect(ItsAsShrimpleAsThat.KRILLED)) {
			matrices.pushPose();
			matrices.scale(-1, -1, 1);
			matrices.translate(0, -1.5, 0);
			matrices.mulPose(Axis.YP.rotationDegrees(player.getYRot() + 180));
			VertexConsumer buffer = buffers.getBuffer(RenderType.entityCutout(ShrimpRenderer.TEXTURE));
			this.shrimpModel.renderToBuffer(matrices, buffer, i, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
			matrices.popPose();
			ci.cancel();
		}
	}
}

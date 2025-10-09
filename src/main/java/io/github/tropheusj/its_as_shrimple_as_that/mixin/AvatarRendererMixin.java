package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import io.github.tropheusj.its_as_shrimple_as_that.ext.AvatarRendererExtensions;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatAttributes;
import io.github.tropheusj.its_as_shrimple_as_that.registry.ItsAsShrimpleAsThatRenderStateDataKeys;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpModel;
import io.github.tropheusj.its_as_shrimple_as_that.entity.render.ShrimpRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin implements AvatarRendererExtensions {
	@Unique
	private ShrimpModel<AvatarRenderState> shrimpModel;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(Context context, boolean bl, CallbackInfo ci) {
		this.shrimpModel = new ShrimpModel<>(context);
	}

	@Inject(
			method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
			at = @At("HEAD")
	)
	private void extractShrimpState(Avatar avatar, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
		AttributeInstance attribute = avatar.getAttribute(ItsAsShrimpleAsThatAttributes.KRILLED);
		state.setData(
				ItsAsShrimpleAsThatRenderStateDataKeys.KRILLED,
				attribute == null || attribute.getValue() <= 0 ? null : Unit.INSTANCE
		);
	}

	@Inject(
			method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)Lnet/minecraft/resources/ResourceLocation;",
			at = @At("HEAD"),
			cancellable = true
	)
	private void shrimpTexture(AvatarRenderState state, CallbackInfoReturnable<ResourceLocation> cir) {
		if (state.getData(ItsAsShrimpleAsThatRenderStateDataKeys.KRILLED) != null) {
			cir.setReturnValue(ShrimpRenderer.TEXTURE);
		}
	}

	@Override
	public ShrimpModel<AvatarRenderState> its_as_shrimple_as_that$shrimpModel() {
		return this.shrimpModel;
	}
}

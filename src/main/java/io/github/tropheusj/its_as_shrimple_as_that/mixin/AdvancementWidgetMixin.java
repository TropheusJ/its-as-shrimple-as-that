package io.github.tropheusj.its_as_shrimple_as_that.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.network.chat.FormattedText;

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin {
	@WrapOperation(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/Font;substrByWidth(Lnet/minecraft/network/chat/FormattedText;I)Lnet/minecraft/network/chat/FormattedText;"
			)
	)
	private FormattedText extend(Font instance, FormattedText text, int width, Operation<FormattedText> original) {
		// don't shorten at all
		return text;
	}
}

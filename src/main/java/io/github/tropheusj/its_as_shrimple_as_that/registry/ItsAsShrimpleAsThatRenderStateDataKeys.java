package io.github.tropheusj.its_as_shrimple_as_that.registry;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.util.Unit;

public final class ItsAsShrimpleAsThatRenderStateDataKeys {
	public static final RenderStateDataKey<Unit> KRILLED = RenderStateDataKey.create(() -> "krilled");
}

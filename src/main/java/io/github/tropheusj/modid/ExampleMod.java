package io.github.tropheusj.modid;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

public class ExampleMod implements ModInitializer {
	public static final String ID = "modid";

	@Override
	public void onInitialize() {
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}

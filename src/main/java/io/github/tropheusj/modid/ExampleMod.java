package io.github.tropheusj.modid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
	public static final String ID = "modid";

	@Override
	public void onInitialize() {
	}

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
}

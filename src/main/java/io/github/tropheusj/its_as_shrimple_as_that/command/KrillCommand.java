package io.github.tropheusj.its_as_shrimple_as_that.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import io.github.tropheusj.its_as_shrimple_as_that.entity.Krillification;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class KrillCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> build() {
		return literal("krill")
				.requires(source -> source.hasPermission(2))
				.executes(ctx -> krill(ctx.getSource(), ImmutableList.of(ctx.getSource().getEntityOrException())))
				.then(argument("targets", EntityArgument.entities())
						.executes(ctx -> krill(ctx.getSource(), EntityArgument.getEntities(ctx, "targets")))
				);
	}

	private static int krill(CommandSourceStack source, Collection<? extends Entity> collection) {
		for (Entity entity : collection) {
			if (entity instanceof LivingEntity living)
				Krillification.transform(living);
		}

		Component feedback = getFeedback(collection);
		source.sendSuccess(() -> feedback, true);
		return collection.size();
	}

	private static Component getFeedback(Collection<? extends Entity> entities) {
		int count = entities.size();
		if (count == 1) {
			Entity entity = entities.iterator().next();
			Component name = entity.getDisplayName();
			return Component.translatable("commands.krill.success.single", name);
		} else {
			return Component.translatable("commands.krill.success.multiple", count);
		}
	}
}

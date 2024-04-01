package io.github.tropheusj.krill.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class KrillEntity extends AbstractVillager {
	public KrillEntity(EntityType<? extends KrillEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void rewardTradeXp(MerchantOffer merchantOffer) {
		if (merchantOffer.shouldRewardExp()) {
			int i = 3 + this.random.nextInt(4);
			this.level().addFreshEntity(new ExperienceOrb(
					this.level(), this.getX(), this.getY() + 0.5, this.getZ(), i
			));
		}
	}

	@Override
	protected void updateTrades() {
		this.getOffers().add(new MerchantOffer(
				new ItemCost(Items.AMBER_GEM),
				new ItemStack(Items.POISONOUS_POTATO_FRIES),
				7, 1, 1
		));
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return null;
	}
}

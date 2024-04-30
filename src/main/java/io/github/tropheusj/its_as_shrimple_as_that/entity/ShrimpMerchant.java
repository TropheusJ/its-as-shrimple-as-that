package io.github.tropheusj.its_as_shrimple_as_that.entity;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;

import io.github.tropheusj.its_as_shrimple_as_that.mixin.SimpleCriterionTriggerAccessor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.TradeTrigger.TriggerInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.storage.loot.LootContext;

public class ShrimpMerchant implements Merchant {
	// normal trigger method unnecessarily requires an AbstractVillager
	@SuppressWarnings("unchecked")
	public static final SimpleCriterionTriggerAccessor<TriggerInstance> TRADE_TRIGGER = (SimpleCriterionTriggerAccessor<TriggerInstance>) CriteriaTriggers.TRADE;

	private final ShrimpEntity shrimp;

	@Nullable
	private Player tradingPlayer;

	private MerchantOffers offers;

	public ShrimpMerchant(ShrimpEntity shrimp) {
		this.shrimp = shrimp;
		this.offers = new MerchantOffers();
		this.offers.add(itemForEmeralds(ItsAsShrimpleAsThat.FRIED_RICE, 2));
	}

	private MerchantOffer itemForEmeralds(Item item, int count) {
		return new MerchantOffer(
				new ItemCost(Items.EMERALD, count),
				new ItemStack(item),
				Integer.MAX_VALUE, // max uses
				1, // xp
				1 // price multiplier
		);
	}

	@Override
	public void setTradingPlayer(@Nullable Player player) {
		this.tradingPlayer = player;
	}

	@Nullable
	@Override
	public Player getTradingPlayer() {
		return this.tradingPlayer;
	}

	public boolean isTrading() {
		return this.tradingPlayer != null;
	}

	@Override
	@NotNull
	public MerchantOffers getOffers() {
		return this.offers;
	}

	@Override
	public void overrideOffers(MerchantOffers merchantOffers) {
		this.offers = merchantOffers;
	}

	@Override
	public void notifyTrade(MerchantOffer offer) {
		offer.increaseUses();
		if (this.tradingPlayer instanceof ServerPlayer player) {
			// replicate TradeTrigger#trigger
			LootContext lootContext = EntityPredicate.createContext(player, this.shrimp);
			TRADE_TRIGGER.callTrigger(player, instance -> instance.matches(lootContext, offer.getResult()));
		}
	}

	@Override
	@NotNull
	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.VILLAGER_YES;
	}

	@Override
	public boolean isClientSide() {
		return false;
	}

	// don't care

	@Override
	public void notifyTradeUpdated(ItemStack itemStack) {
	}

	@Override
	public int getVillagerXp() {
		return 0;
	}

	@Override
	public void overrideXp(int i) {
	}

	@Override
	public boolean showProgressBar() {
		return false;
	}
}

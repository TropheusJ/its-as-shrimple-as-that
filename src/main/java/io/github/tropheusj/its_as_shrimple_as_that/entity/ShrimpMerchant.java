package io.github.tropheusj.its_as_shrimple_as_that.entity;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;
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

public class ShrimpMerchant implements Merchant {
	@Nullable
	private Player tradingPlayer;

	private MerchantOffers offers;

	public ShrimpMerchant() {
		this.offers = new MerchantOffers();
		this.offers.add(itemForGems(ItsAsShrimpleAsThat.FRIED_RICE, 2));
	}

	private MerchantOffer itemForGems(Item item, int count) {
		return new MerchantOffer(
				new ItemCost(Items.AMBER_GEM, count),
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
	public void notifyTrade(MerchantOffer merchantOffer) {
		merchantOffer.increaseUses();
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

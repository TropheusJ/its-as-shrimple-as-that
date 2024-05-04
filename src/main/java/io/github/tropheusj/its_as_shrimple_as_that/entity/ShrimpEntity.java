package io.github.tropheusj.its_as_shrimple_as_that.entity;

import java.util.List;
import java.util.Optional;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.goal.FollowDreamsGoal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;

public class ShrimpEntity extends MerchantEntity implements Merchant {
	public static final EntityDataAccessor<Optional<BlockPos>> WORKSTATION = SynchedEntityData.defineId(ShrimpEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

	public ShrimpEntity(EntityType<? extends ShrimpEntity> type, Level level) {
		super(type, level);
		this.setPathfindingMalus(PathType.WATER, 0);
		this.offers.add(itemForEmeralds(ItsAsShrimpleAsThat.FRIED_RICE, 2));
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
		builder.define(WORKSTATION, Optional.empty());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(2, new FollowDreamsGoal(this, 1, 16, 2));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.45));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10, 0.1f));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	@Override
	@NotNull
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		if (!this.level().isClientSide) {
			ItemStack held = player.getItemInHand(interactionHand);
			if (held.is(Items.CROSSBOW)) {
				ChargedProjectiles ammo = held.get(DataComponents.CHARGED_PROJECTILES);
				if (ammo != null && ammo.isEmpty()) {
					// lock and load
					if (player instanceof ServerPlayer serverPlayer) {
						ChargedProjectiles newAmmo = ChargedProjectiles.of(new ItemStack(ItsAsShrimpleAsThat.SHRIMP_ARROW));
						held.set(DataComponents.CHARGED_PROJECTILES, newAmmo);
						this.makeSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM);
						ItsAsShrimpleAsThat.LOAD_SHRIMP_TRIGGER.trigger(serverPlayer);
						this.discard();
					}
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}
			}

			if (this.isChef() && !this.isTrading()) {
				this.setTradingPlayer(player);
				this.openTradingScreen(player, this.getDisplayName(), 0);
				return InteractionResult.CONSUME;
			}
		}

		return InteractionResult.sidedSuccess(this.level().isClientSide);
	}

	@Override
	public int getAirSupply() {
		// never drown
		return 100;
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
		BlockState state = levelReader.getBlockState(blockPos);
		if (state.is(Blocks.WATER))
			return 10;

		return levelReader.getPathfindingCostFromLightLevels(blockPos);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader levelReader) {
		return levelReader.isUnobstructed(this);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public void remove(RemovalReason removalReason) {
		super.remove(removalReason);
		this.setTradingPlayer(null);
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerLevel serverLevel) {
		this.setTradingPlayer(null);
		return super.changeDimension(serverLevel);
	}

	public void becomeChef(BlockPos workstation) {
		this.entityData.set(WORKSTATION, Optional.of(workstation));
		List<ServerPlayer> nearbyPlayers = level().getEntitiesOfClass(ServerPlayer.class, new AABB(this.blockPosition()).inflate(16));
		nearbyPlayers.forEach(ItsAsShrimpleAsThat.ACCOMPLISH_DREAMS_TRIGGER::trigger);
	}

	public void crushDreams() {
		this.entityData.set(WORKSTATION, Optional.empty());
	}

	public boolean isChef() {
		return this.entityData.get(WORKSTATION).isPresent();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8);
	}

	// from WaterAnimal
	public static boolean checkShrimpSpawnRules(EntityType<? extends ShrimpEntity> type, LevelAccessor level,
												MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		int i = level.getSeaLevel();
		int j = i - 13;
		return pos.getY() >= j
				&& pos.getY() <= i
				&& level.getFluidState(pos.below()).is(FluidTags.WATER)
				&& level.getBlockState(pos.above()).is(Blocks.WATER);
	}
}

package io.github.tropheusj.its_as_shrimple_as_that.entity;

import java.util.List;
import java.util.Optional;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import io.github.tropheusj.its_as_shrimple_as_that.entity.goal.FollowDreamsGoal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;

public class ShrimpEntity extends PathfinderMob {
	public static final EntityDataAccessor<Optional<BlockPos>> WORKSTATION = SynchedEntityData.defineId(ShrimpEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

	@Nullable
	private ShrimpMerchant merchant;

	public ShrimpEntity(EntityType<? extends ShrimpEntity> type, Level level) {
		super(type, level);
		this.setPathfindingMalus(PathType.WATER, 0);
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
	public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
		super.onSyncedDataUpdated(data);
		if (data == WORKSTATION) {
			Optional<BlockPos> workstation = this.entityData.get(WORKSTATION);
			this.merchant = workstation.isPresent() ? new ShrimpMerchant(this) : null;
		}
	}

	@Override
	@NotNull
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		if (!this.level().isClientSide && this.merchant != null && !this.merchant.isTrading()) {
			this.merchant.setTradingPlayer(player);
			this.merchant.openTradingScreen(player, this.getDisplayName(), 0);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
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
		if (this.merchant != null) {
			this.merchant.setTradingPlayer(null);
		}
	}

	@Nullable
	@Override
	public Entity changeDimension(ServerLevel serverLevel, boolean bl) {
		if (this.merchant != null) {
			this.merchant.setTradingPlayer(null);
		}
		return super.changeDimension(serverLevel, bl);
	}

	public void becomeChef(BlockPos workstation) {
		this.entityData.set(WORKSTATION, Optional.of(workstation));
		List<ServerPlayer> nearbyPlayers = level().getEntitiesOfClass(ServerPlayer.class, new AABB(this.blockPosition()).inflate(16));
		nearbyPlayers.forEach(ItsAsShrimpleAsThat.ACCOMPLISH_DREAMS_TRIGGER::trigger);
	}

	public void crushDreams() {
		this.entityData.set(WORKSTATION,Optional.empty());
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

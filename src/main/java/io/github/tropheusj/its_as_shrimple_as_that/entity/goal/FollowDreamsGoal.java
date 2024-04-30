package io.github.tropheusj.its_as_shrimple_as_that.entity.goal;

import io.github.tropheusj.its_as_shrimple_as_that.entity.ShrimpEntity;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class FollowDreamsGoal extends MoveToBlockGoal {
	private final ShrimpEntity shrimp;

	public FollowDreamsGoal(ShrimpEntity shrimp, double speed, int range, int verticalRange) {
		super(shrimp, speed, range, verticalRange);
		this.shrimp = shrimp;
	}

	@Override
	public void start() {
		super.start();
		this.shrimp.crushDreams();
	}

	@Override
	public void stop() {
		super.stop();
		this.shrimp.crushDreams();
	}

	@Override
	protected int nextStartTick(PathfinderMob pathfinderMob) {
		return 40;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			shrimp.becomeChef(this.blockPos);
		} else {
			shrimp.crushDreams();
		}
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		if (!level.isEmptyBlock(pos) && !level.getFluidState(pos).is(FluidTags.WATER))
			return false;
		for (Direction direction : Plane.HORIZONTAL) {
			BlockPos adjacent = pos.relative(direction);
			if (level.getBlockState(adjacent).is(BlockTags.CAMPFIRES))
				return true;
		}
		return false;
	}

	@Override
	@NotNull
	protected BlockPos getMoveToTarget() {
		return this.blockPos;
	}

	@Override
	public double acceptedDistance() {
		return 2;
	}
}

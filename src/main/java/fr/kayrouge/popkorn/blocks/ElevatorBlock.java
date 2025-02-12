package fr.kayrouge.popkorn.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class ElevatorBlock extends Block {

	public static final MapCodec<ElevatorBlock> CODEC = createCodec(ElevatorBlock::new);
	public static final BooleanProperty POWERED;
	public static final DirectionProperty DIRECTION;


	public ElevatorBlock(Settings settings) {
		super(settings.pistonBehavior(PistonBehavior.BLOCK));
		this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false).with(DIRECTION, Direction.UP));
	}

	@Override
	protected MapCodec<? extends Block> getCodec() {
		return CODEC;
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		if(state.get(POWERED)) {
			world.setBlockState(pos, state.with(POWERED, false));
		}
	}

	public void scheduleTick(WorldAccess world, BlockPos pos) {
		if (!world.isClient() && !world.getBlockTickScheduler().isQueued(pos, this)) {
			world.scheduleBlockTick(pos, this, 4);
		}

	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(POWERED,DIRECTION);
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		super.neighborUpdate(state, world, pos, block, fromPos, notify);
		if(fromPos == pos) return;
		if(!(world.getBlockState(fromPos).getBlock() instanceof ElevatorBlock)) return;
		if(world.getBlockState(pos).getBlock() instanceof ElevatorBlock) {
			world.setBlockState(pos, world.getBlockState(fromPos));
		}
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		if(state.get(POWERED) && state.get(DIRECTION) == Direction.UP) {
			return 10;
		}
		else if(state.get(POWERED) && state.get(DIRECTION) == Direction.DOWN) {
			return 5;
		}
		else {
			return 0;
		}
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState();
	}

	static {
		POWERED = Properties.POWERED;
		DIRECTION = Properties.VERTICAL_DIRECTION;
	}
}

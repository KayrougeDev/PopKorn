package fr.kayrouge.popkorn.blocks;

import com.mojang.serialization.MapCodec;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.entity.GhostBlockEntity;
import fr.kayrouge.popkorn.registry.PKBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GhostBlock extends BlockWithEntity {

	public static final DirectionProperty FACING;
	public static final MapCodec<GhostBlock> CODEC = createCodec(GhostBlock::new);

	public GhostBlock(Settings settings) {
		super(createSettings(settings.noCollision()));
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
	}

	private static Settings createSettings(Settings settings) {
		return settings;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		Direction direction = state.get(FACING);
		BlockPos originalPos = pos.offset(direction);
		Block originalBlock = world.getBlockState(originalPos).getBlock();
		if(originalBlock == Blocks.AIR) {
			originalBlock = Blocks.STONE;
		}
		else if(originalBlock == PKBlocks.GHOST_BLOCK
					&& world.getBlockEntity(originalPos) instanceof GhostBlockEntity originalEntity) {
			originalBlock = originalEntity.getBlockToDisplay();
		}

		PopKorn.LOGGER.info(originalBlock.getTranslationKey());


		if(world.getBlockEntity(pos) instanceof GhostBlockEntity entity) {
			entity.setBlockToDisplay(originalBlock);
			entity.markDirty();
		}
	}

	@Override
	protected MapCodec<? extends GhostBlock> getCodec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GhostBlockEntity(pos, state);
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return PillarBlock.changeRotation(state, rotation);
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getSide().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	static {
		FACING = Properties.FACING;
	}
}

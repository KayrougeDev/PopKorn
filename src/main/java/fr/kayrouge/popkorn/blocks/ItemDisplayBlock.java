package fr.kayrouge.popkorn.blocks;

import com.mojang.serialization.MapCodec;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.entity.ItemDisplayBlockEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayBlock extends BlockWithEntity {

	public static final BooleanProperty DISPLAY_ITEM;

	protected ItemDisplayBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(DISPLAY_ITEM, true));
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(ItemDisplayBlock::new);
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx);
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		if(state.get(DISPLAY_ITEM) && world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity entity && !entity.getInventory().isEmpty()) {
			return 15;
		}
		return 0;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(DISPLAY_ITEM);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ItemDisplayBlockEntity(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity entity, BlockHitResult hitResult) {

		if (!world.isClient) {
			if(world.getBlockEntity(pos) instanceof ExtendedScreenHandlerFactory) {
				NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

				if (screenHandlerFactory != null) {
					entity.openHandledScreen(screenHandlerFactory);
				}
			}

		}
		return ActionResult.SUCCESS;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if(itemStack.getComponents().contains(DataComponentTypes.BLOCK_ENTITY_DATA)) {
			PopKorn.LOGGER.info("CONTAINS");
			state.with(DISPLAY_ITEM, true);
		}
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

		if(world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity entity) {
			ItemStack stack = entity.getInventory().getStack(0);
			if(!stack.isEmpty()) {
				dropStack(world, pos, stack);
			}
		}

		return super.onBreak(world, pos, state, player);
	}

	static {
		DISPLAY_ITEM = BooleanProperty.of("display_item");
	}
}

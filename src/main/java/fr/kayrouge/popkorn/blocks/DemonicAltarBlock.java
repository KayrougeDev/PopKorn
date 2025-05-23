package fr.kayrouge.popkorn.blocks;

import com.mojang.serialization.MapCodec;
import fr.kayrouge.popkorn.blocks.entity.DemonicAltarBlockEntity;
import fr.kayrouge.popkorn.screen.DemonicAltarScreenHandler;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.*;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DemonicAltarBlock extends BlockWithEntity {

	public static final MapCodec<DemonicAltarBlock> CODEC = createCodec(DemonicAltarBlock::new);

	public DemonicAltarBlock(Settings settings) {
		super(settings);
	}

	private static final Text TITLE = Text.translatable("container.demonic_altar");

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity entity, BlockHitResult hitResult) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			entity.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			entity.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
			return ActionResult.CONSUME;
		}
	}

	@Override
	protected @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new DemonicAltarScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DemonicAltarBlockEntity(pos, state);
	}
}

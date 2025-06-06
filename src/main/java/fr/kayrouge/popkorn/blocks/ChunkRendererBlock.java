package fr.kayrouge.popkorn.blocks;

import com.mojang.serialization.MapCodec;
import fr.kayrouge.popkorn.blocks.entity.ChunkRendererBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ChunkRendererBlock extends BlockWithEntity {

	public static final MapCodec<ChunkRendererBlock> CODEC = createCodec(ChunkRendererBlock::new);

	public ChunkRendererBlock(Settings settings) {
		super(settings.noCollision().solid());
	}

	@Override
	protected MapCodec<? extends ChunkRendererBlock> getCodec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ChunkRendererBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}


}

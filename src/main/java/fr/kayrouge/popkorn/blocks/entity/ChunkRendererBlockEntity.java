package fr.kayrouge.popkorn.blocks.entity;

import fr.kayrouge.popkorn.registry.PKBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ChunkRendererBlockEntity extends BlockEntity {


	public ChunkRendererBlockEntity(BlockPos pos, BlockState state) {
		super(PKBlockEntityTypes.CHUNK_RENDERER_BLOCK_ENTITY, pos, state);
	}
}

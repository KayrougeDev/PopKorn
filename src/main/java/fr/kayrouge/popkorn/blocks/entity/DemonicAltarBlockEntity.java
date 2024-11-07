package fr.kayrouge.popkorn.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DemonicAltarBlockEntity extends BlockEntity {

	public DemonicAltarBlockEntity(BlockPos pos, BlockState state) {
		super(PKBlockEntityTypes.DEMONIC_ALTAR_BLOCK_ENTITY_TYPE, pos, state);
	}
}

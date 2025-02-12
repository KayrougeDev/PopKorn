package fr.kayrouge.popkorn.blocks.entity;

import fr.kayrouge.popkorn.registry.PKBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GhostBlockEntity extends BlockEntity {

	private Block blockToDisplay = Blocks.STONE;

	public GhostBlockEntity(BlockPos pos, BlockState state) {
		super(PKBlockEntityTypes.GHOST_BLOCK_ENTITY_TYPE, pos, state);
	}

	@Override
	protected void readNbtImpl(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.readNbtImpl(nbt, lookupProvider);

		if(nbt.contains("namespace") && nbt.contains("namespace")) {
			this.blockToDisplay = Registries.BLOCK.get(Identifier.of(nbt.getString("namespace"), nbt.getString("path")));
			if(this.blockToDisplay == Blocks.AIR) {
				this.blockToDisplay = Blocks.STONE;
			}
		}
		else {
			this.blockToDisplay = Blocks.STONE;
		}

	}

	@Override
	protected void writeNbt(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.writeNbt(nbt, lookupProvider);

		Identifier identifier = Registries.BLOCK.getId(this.blockToDisplay);
		nbt.putString("block_namespace", identifier.getNamespace());
		nbt.putString("block_path", identifier.getPath());
	}

	public void setBlockToDisplay(Block blockToDisplay) {
		this.blockToDisplay = blockToDisplay;
	}

	public Block getBlockToDisplay() {
		return blockToDisplay;
	}
}

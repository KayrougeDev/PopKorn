package fr.kayrouge.popkorn.blocks.entity;

import fr.kayrouge.popkorn.registry.PKBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GhostBlockEntity extends BlockEntity {

	private Block blockToDisplay = Blocks.STONE;

	public GhostBlockEntity(BlockPos pos, BlockState state) {
		super(PKBlockEntityTypes.GHOST_BLOCK_ENTITY_TYPE, pos, state);
	}

	@Override
	protected void readNbtImpl(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.readNbtImpl(nbt, lookupProvider);

		if(nbt.contains("namespace") && nbt.contains("path")) {
			this.blockToDisplay = Registries.BLOCK.get(Identifier.of(nbt.getString("namespace"), nbt.getString("path")));
			if(this.blockToDisplay == Blocks.AIR) {
				this.blockToDisplay = Blocks.COBBLESTONE;
			}
		}
		else {
			this.blockToDisplay = Blocks.BRICKS;
		}
	}

	@Override
	protected void writeNbt(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.writeNbt(nbt, lookupProvider);

		Identifier identifier = Registries.BLOCK.getId(this.blockToDisplay);
		nbt.putString("namespace", identifier.getNamespace());
		nbt.putString("path", identifier.getPath());
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	@Override
	public NbtCompound toSyncedNbt(HolderLookup.Provider lookupProvider) {
		return this.toNbt(lookupProvider);
	}

	public void setBlockToDisplay(Block blockToDisplay) {
		this.blockToDisplay = blockToDisplay;
	}

	public Block getBlockToDisplay() {
		return blockToDisplay;
	}
}

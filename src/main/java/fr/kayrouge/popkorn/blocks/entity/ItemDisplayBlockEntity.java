package fr.kayrouge.popkorn.blocks.entity;

import fr.kayrouge.popkorn.registry.PKBlockEntityTypes;
import fr.kayrouge.popkorn.screen.ItemDisplayScreenHandler;
import fr.kayrouge.popkorn.screen.data.ItemDisplayData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.HolderLookup;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<ItemDisplayData> {

	private final SimpleInventory simpleInventory = new SimpleInventory(1);
	public boolean rotate = false;
	public boolean upAnDown = true;

	public ItemDisplayBlockEntity(BlockPos pos, BlockState state) {
		super(PKBlockEntityTypes.ITEM_DISPLAY_BLOCK_ENTITY_TYPE, pos, state);
	}

	@Override
	protected void readNbtImpl(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.readNbtImpl(nbt, lookupProvider);

		Inventories.readNbt(nbt, this.simpleInventory.stacks, lookupProvider);

		this.rotate = nbt.getBoolean("rotate");
		this.upAnDown = nbt.getBoolean("upAnDown");
	}

	@Override
	protected void writeNbt(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		Inventories.writeNbt(nbt, this.simpleInventory.stacks, lookupProvider);

		nbt.putBoolean("rotate", this.rotate);
		nbt.putBoolean("upAnDown", this.upAnDown);

		super.writeNbt(nbt, lookupProvider);
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	public SimpleInventory getInventory() {
		return simpleInventory;
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	@Override
	public NbtCompound toSyncedNbt(HolderLookup.Provider lookupProvider) {
		return this.toNbt(lookupProvider);
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("container.item_display");
	}

	@Override
	public @Nullable ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new ItemDisplayScreenHandler(i, playerInventory, this.simpleInventory, new ItemDisplayData(pos, rotate, upAnDown), ScreenHandlerContext.create(this.world, this.getPos()));
	}

	@Override
	public ItemDisplayData getScreenOpeningData(ServerPlayerEntity player) {
		return new ItemDisplayData(pos, rotate, upAnDown);
	}
}

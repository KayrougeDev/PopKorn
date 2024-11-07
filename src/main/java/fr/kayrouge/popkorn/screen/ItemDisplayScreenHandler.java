package fr.kayrouge.popkorn.screen;

import fr.kayrouge.popkorn.blocks.ItemDisplayBlock;
import fr.kayrouge.popkorn.blocks.entity.ItemDisplayBlockEntity;
import fr.kayrouge.popkorn.screen.data.ItemDisplayData;
import fr.kayrouge.popkorn.screen.slot.ItemDisplaySlot;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDisplayScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final ItemDisplayData itemDisplayData;
	private final ScreenHandlerContext context;

	public ItemDisplayScreenHandler(int syncId, PlayerInventory playerInventory, ItemDisplayData itemDisplayData) {
		this(syncId, playerInventory, new SimpleInventory(1), itemDisplayData, ScreenHandlerContext.EMPTY);
	}

	public ItemDisplayScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ItemDisplayData itemDisplayData, ScreenHandlerContext context) {
		super(PKHandledScreens.ITEM_DISPLAY_SCREEN_HANDLER, syncId);
		this.inventory = inventory;
		this.addSlot(new ItemDisplaySlot(inventory, 0, 5, 5, this));
		this.itemDisplayData = itemDisplayData;
		this.context = context;

		// The player inventory
		int m;
		int l;
		for (m = 0; m < 3; ++m) {
			for (l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
			}
		}
		// The player Hotbar
		for (m = 0; m < 9; ++m) {
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
		}
	}

	public ItemDisplayData getData() {
		return itemDisplayData;
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);
		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (fromIndex < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		this.context.run(ItemDisplayScreenHandler::update);
	}

	private static void update(World world, BlockPos pos) {
		if(world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity entity) {
			BlockState state = world.getBlockState(pos).with(ItemDisplayBlock.DISPLAY_ITEM, !entity.getInventory().getStack(0).isEmpty());
			world.setBlockState(pos, state);
			world.updateListeners(pos, entity.getCachedState(), state, 0);
		}

	}

	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
	}
}

package fr.kayrouge.popkorn.screen.slot;

import fr.kayrouge.popkorn.screen.ItemDisplayScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ItemDisplaySlot extends Slot {
	public final ItemDisplayScreenHandler handler;

	public ItemDisplaySlot(Inventory inventory, int index, int x, int y, ItemDisplayScreenHandler handler) {
		super(inventory, index, x, y);
		this.handler = handler;
	}

	@Override
	public void setStack(ItemStack stack) {
		super.setStack(stack);
		handler.onContentChanged(inventory);
	}

	@Override
	public int getMaxItemCount() {
		return 1;
	}
}

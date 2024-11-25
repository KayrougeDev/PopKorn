package fr.kayrouge.popkorn.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class DemonicAltarInventory implements Inventory, RecipeInputProvider {

	private final DefaultedList<ItemStack> stacks;
	private final ScreenHandler handler;

	public DemonicAltarInventory(ScreenHandler screenHandler) {
		this(screenHandler, DefaultedList.ofSize(5, ItemStack.EMPTY));
	}

	public DemonicAltarInventory(ScreenHandler screenHandler, DefaultedList<ItemStack> stacks) {
		this.handler = screenHandler;
		this.stacks = stacks;
	}

	@Override
	public int size() {
		return this.stacks.size();
	}

	@Override
	public boolean isEmpty() {
		return this.stacks.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		return slot >= this.size() ? ItemStack.EMPTY : this.stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
		if (!itemStack.isEmpty()) {
			this.handler.onContentChanged(this);
		}

		return itemStack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.stacks, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.stacks.set(slot, stack);
		this.handler.onContentChanged(this);
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void provideRecipeInputs(RecipeMatcher finder) {
		for (ItemStack itemStack : this.stacks) {
			finder.addUnenchantedInput(itemStack);
		}
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}

	public List<ItemStack> getStacks() {
		return List.copyOf(this.stacks);
	}
}

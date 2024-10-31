package fr.kayrouge.popkorn.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingHandler;
import net.minecraft.recipe.RecipeHolder;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class DemonicAltarResultInventory implements Inventory, CraftingHandler {

	private final DefaultedList<ItemStack> stacks;
	@Nullable
	private RecipeHolder<?> lastRecipe;

	public DemonicAltarResultInventory() {
		this.stacks = DefaultedList.ofSize(1, ItemStack.EMPTY);
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return this.stacks.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		return this.stacks.getFirst();
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return Inventories.removeStack(this.stacks, 0);
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.stacks, 0);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.stacks.set(0, stack);
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void onResultUpdate(@Nullable RecipeHolder<?> recipe) {
		this.lastRecipe = recipe;
	}

	@Override
	public @Nullable RecipeHolder<?> getLastRecipe() {
		return this.lastRecipe;
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}
}

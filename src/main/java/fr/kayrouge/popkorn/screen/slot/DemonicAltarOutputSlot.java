package fr.kayrouge.popkorn.screen.slot;

import fr.kayrouge.popkorn.inventory.DemonicAltarInventory;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipeInput;
import fr.kayrouge.popkorn.registry.PKRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class DemonicAltarOutputSlot extends Slot {

	private final PlayerEntity player;
	private int amount;
	private final DemonicAltarInventory input;

	public DemonicAltarOutputSlot(Inventory inventory, int index, int x, int y, PlayerEntity player, DemonicAltarInventory input) {
		super(inventory, index, x, y);
		this.player = player;
		this.input = input;
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack takeStack(int amount) {
		if (this.hasStack()) {
			this.amount += Math.min(amount, this.getStack().getCount());
		}

		return super.takeStack(amount);
	}

	@Override
	protected void onCrafted(ItemStack stack, int amount) {
		this.amount += amount;
		this.onCrafted(stack);
	}

	@Override
	protected void onCrafted(ItemStack stack) {
		if (this.amount > 0) {
			stack.onCraftByPlayer(this.player.getWorld(), this.player, this.amount);
		}

		if (this.inventory instanceof CraftingHandler craftingHandler) {
			craftingHandler.unlockLastRecipe(this.player, this.input.getStacks());
		}

		this.amount = 0;
	}

	@Override
	public void onTakeItem(PlayerEntity player, ItemStack stack) {
		this.onCrafted(stack);

		DemonicAltarRecipeInput recipeInput = new DemonicAltarRecipeInput(this.input.getStack(4), this.input.getStack(1), this.input.getStack(0), this.input.getStack(3), this.input.getStack(2));
		DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainder(PKRecipes.DEMONIC_ALTAR_RECIPE_TYPE, recipeInput, player.getWorld());

		for(int i = 0; i < 5; i++) {
			ItemStack itemStack = this.input.getStack(i);
			ItemStack itemStack2 = defaultedList.get(i);
			if (!itemStack.isEmpty()) {
				this.input.removeStack(i, 1);
				itemStack = this.input.getStack(i);
			}


			if (!itemStack2.isEmpty()) {
				if (itemStack.isEmpty()) {
					this.input.setStack(i, itemStack2);
				} else if (ItemStack.itemsAndComponentsMatch(itemStack, itemStack2)) {
					itemStack2.increment(itemStack.getCount());
					this.input.setStack(i, itemStack2);
				} else if (!this.player.getInventory().insertStack(itemStack2)) {
					this.player.dropItem(itemStack2, false);
				}
			}
		}



		super.onTakeItem(player, stack);
	}

	@Override
	public boolean method_55059() {
		return true;
	}

	@Override
	protected void onTake(int amount) {
		this.amount += amount;
	}
}

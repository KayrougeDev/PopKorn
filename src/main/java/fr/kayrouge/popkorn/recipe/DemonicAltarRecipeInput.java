package fr.kayrouge.popkorn.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInput;

public record DemonicAltarRecipeInput(ItemStack base, ItemStack left, ItemStack top, ItemStack right, ItemStack bottom) implements RecipeInput {

	@Override
	public ItemStack get(int i) {
		ItemStack stack;
		switch (i) {
			case 0 -> stack = base;
			case 1 -> stack = left;
			case 2 -> stack = top;
			case 3 -> stack = right;
			case 4 -> stack = bottom;
			default -> throw new IllegalArgumentException("Recipe does not contain slot " + i);
		}
		return stack;
	}

	@Override
	public int getSize() {
		return 5;
	}

	@Override
	public boolean isEmpty() {
		return base.isEmpty() && left().isEmpty() && top.isEmpty() && right.isEmpty() && bottom.isEmpty();
	}
}

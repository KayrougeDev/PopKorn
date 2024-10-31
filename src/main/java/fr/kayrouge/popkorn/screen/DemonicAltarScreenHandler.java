package fr.kayrouge.popkorn.screen;

import fr.kayrouge.popkorn.blocks.PKBlocks;
import fr.kayrouge.popkorn.client.screen.PKHandledScreens;
import fr.kayrouge.popkorn.inventory.DemonicAltarInventory;
import fr.kayrouge.popkorn.inventory.DemonicAltarResultInventory;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipe;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipeInput;
import fr.kayrouge.popkorn.recipe.PKRecipes;
import fr.kayrouge.popkorn.screen.slot.DemonicAltarOutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DemonicAltarScreenHandler extends AbstractRecipeScreenHandler<DemonicAltarRecipeInput, DemonicAltarRecipe> {

	private final PlayerEntity player;
	private final ScreenHandlerContext context;

	private final DemonicAltarInventory input;
	private final DemonicAltarResultInventory result;

	private boolean field_51625;

	public DemonicAltarScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}


	public DemonicAltarScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(PKHandledScreens.DEMONIC_ALTAR_SCREEN_HANDLER, syncId);
		this.context = context;
		this.input = new DemonicAltarInventory(this);
		this.result = new DemonicAltarResultInventory();
		this.player = playerInventory.player;
		// some inventories do custom logic when a player opens it.
		input.onOpen(playerInventory.player);

		// Result
		this.addSlot(new DemonicAltarOutputSlot(this.result, 0, 134, 35, player, this.input));
		// TOP 0
		this.addSlot(new Slot(this.input, 0, 34, 14));
		// LEFT 1
		this.addSlot(new Slot(this.input, 1, 13, 35));
		// BOTTOM 2
		this.addSlot(new Slot(this.input, 2, 34, 56));
		// RIGHT 3
		this.addSlot(new Slot(this.input, 3, 55, 35));
		// BASE 4
		this.addSlot(new Slot(this.input, 4, 34, 35));


		int m;
		int l;
		// The player inventory
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

	@Override
	public void onContentChanged(Inventory inventory) {
		if (!this.field_51625) {
			this.context.run((world, pos) -> {
				craft(this, world, this.player, this.input, this.result, null);
			});
		}
	}

	@Override
	protected void method_59963() {
		this.field_51625 = true;
	}

	@Override
	protected void method_59964(RecipeHolder<DemonicAltarRecipe> recipeHolder) {
		this.field_51625 = false;
		this.context.run((world, pos) -> {
			craft(this, world, this.player, this.input, this.result, recipeHolder);
		});
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(fromIndex);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (fromIndex == 0) {
				this.context.run((world, pos) -> {
					itemStack2.getItem().onCraftByPlayer(itemStack2, world, player);
				});
				if (!this.insertItem(itemStack2, 6, 42, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickTransfer(itemStack2, itemStack);
			} else if (fromIndex >= 6 && fromIndex < 42) {
				if (!this.insertItem(itemStack2, 1, 6, false)) {
					if (fromIndex < 33) {
						if (!this.insertItem(itemStack2, 33, 42, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.insertItem(itemStack2, 6, 33, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.insertItem(itemStack2, 6, 42, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.method_53512(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, itemStack2);
			if (fromIndex == 0) {
				player.dropItem(itemStack2, false);
			}
		}

		return itemStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(this.context, player, PKBlocks.DEMONIC_ALTAR);
	}

	protected void craft(ScreenHandler screenHandler, World world, PlayerEntity player, DemonicAltarInventory demonicAltarInventory, DemonicAltarResultInventory demonicAltarResultInventory, @Nullable RecipeHolder<DemonicAltarRecipe> value) {
		if (!world.isClient && world.getServer() != null) {
			DemonicAltarRecipeInput recipeInput = new DemonicAltarRecipeInput(demonicAltarInventory.getStack(4), demonicAltarInventory.getStack(1), demonicAltarInventory.getStack(0), demonicAltarInventory.getStack(3), demonicAltarInventory.getStack(2));
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<RecipeHolder<DemonicAltarRecipe>> optional = world.getServer().getRecipeManager().getFirstMatch(PKRecipes.DEMONIC_ALTAR_RECIPE_TYPE, recipeInput, world, value);
			if (optional.isPresent()) {
				RecipeHolder<DemonicAltarRecipe> recipeHolder = optional.get();
				DemonicAltarRecipe craftingRecipe = recipeHolder.value();
				if (demonicAltarResultInventory.canCraft(world, serverPlayerEntity, recipeHolder)) {
					ItemStack itemStack2 = craftingRecipe.craft(recipeInput, world.getRegistryManager());
					if (itemStack2.isEnabled(world.getEnabledFlags())) {
						itemStack = itemStack2;
					}
				}
			}

			demonicAltarResultInventory.setStack(0, itemStack);
			screenHandler.setPreviousTrackedSlot(0, itemStack);
			serverPlayerEntity.networkHandler.send(new ScreenHandlerSlotUpdateS2CPacket(screenHandler.syncId, screenHandler.nextRevision(), 0, itemStack));
		}
	}

	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
		this.input.provideRecipeInputs(finder);
	}

	@Override
	public void clearCraftingSlots() {
		this.input.clear();
		this.result.clear();
	}

	@Override
	public boolean matches(RecipeHolder<DemonicAltarRecipe> recipe) {
		return recipe.value().matches(new DemonicAltarRecipeInput(this.input.getStack(4), this.input.getStack(1), this.input.getStack(0), this.input.getStack(3), this.input.getStack(2)), this.player.getWorld());
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.context.run((world, pos) -> {
			this.dropInventory(player, this.input);
		});
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 0;
	}

	@Override
	public int getCraftingWidth() {
		return 3;
	}

	@Override
	public int getCraftingHeight() {
		return 3;
	}

	@Override
	public int getCraftingSlotCount() {
		return 6;
	}

	@Override
	public RecipeBookCategory getCategory() {
		return RecipeBookCategory.CRAFTING;
	}

	@Override
	public boolean canInsertIntoSlot(int index) {
		return index != this.getCraftingResultSlotIndex();
	}

	@Override
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
	}
}

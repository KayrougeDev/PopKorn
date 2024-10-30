package fr.kayrouge.popkorn.screen;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.screen.PKHandledScreens;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipe;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipeInput;
import fr.kayrouge.popkorn.recipe.PKRecipes;
import fr.kayrouge.popkorn.screen.slot.DemonicAltarOutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.feature_flags.FeatureFlagBitSet;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.inventory.SimpleInventory;
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

	private final Inventory inventory;
	private final PlayerEntity player;
	private boolean field_51625;
	private final ScreenHandlerContext context;

	private static final int TOP_SLOT = 0;
	private static final int LEFT_SLOT = 1;
	private static final int BOTTOM_SLOT = 2;
	private static final int RIGHT_SLOT = 3;
	private static final int BASE_SLOT = 4;
	private static final int RESULT_SLOT = 5;

	public DemonicAltarScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(6), ScreenHandlerContext.EMPTY);
	}


	public DemonicAltarScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context) {
		super(PKHandledScreens.DEMONIC_ALTAR_SCREEN_HANDLER, syncId);
		checkSize(inventory, 6);
		this.context = context;
		this.inventory = inventory;
		this.player = playerInventory.player;
		// some inventories do custom logic when a player opens it.
		inventory.onOpen(playerInventory.player);

		// TOP 0
		this.addSlot(new Slot(inventory, TOP_SLOT, 34, 14));
		// LEFT 1
		this.addSlot(new Slot(inventory, LEFT_SLOT, 13, 35));
		// BOTTOM 2
		this.addSlot(new Slot(inventory, BOTTOM_SLOT, 34, 56));
		// RIGHT 3
		this.addSlot(new Slot(inventory, RIGHT_SLOT, 55, 35));
		// BASE 4
		this.addSlot(new Slot(inventory, BASE_SLOT, 34, 35));
		// RESULT
		this.addSlot(new DemonicAltarOutputSlot(inventory, RESULT_SLOT, 134, 35));

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
	public void method_59963() {
		this.field_51625 = true;
	}

	protected void craft(ScreenHandler screenHandler, World world, PlayerEntity player, Inventory inventory, @Nullable RecipeHolder<DemonicAltarRecipe> value) {
		if (!world.isClient) {
			DemonicAltarRecipeInput recipeInput = new DemonicAltarRecipeInput(this.inventory.getStack(BASE_SLOT), this.inventory.getStack(LEFT_SLOT), this.inventory.getStack(TOP_SLOT), this.inventory.getStack(RIGHT_SLOT), this.inventory.getStack(BOTTOM_SLOT));
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<RecipeHolder<DemonicAltarRecipe>> optional = world.getServer().getRecipeManager().getFirstMatch(PKRecipes.DEMONIC_ALTAR_RECIPE_TYPE, recipeInput, world, value);
			if (optional.isPresent()) {
				RecipeHolder recipeHolder = optional.get();
				DemonicAltarRecipe craftingRecipe = (DemonicAltarRecipe)recipeHolder.value();
				ItemStack itemStack2 = craftingRecipe.craft(recipeInput, world.getRegistryManager());
				if (itemStack2.isEnabled(world.getEnabledFlags())) {
					itemStack = itemStack2;
				}
			}

			PopKorn.LOGGER.info("S43J ctraftedddd!");
			itemStack = this.inventory.getStack(BASE_SLOT);
			inventory.setStack(getCraftingResultSlotIndex(), itemStack);
			screenHandler.setPreviousTrackedSlot(getCraftingResultSlotIndex(), itemStack);
			serverPlayerEntity.networkHandler.send(new ScreenHandlerSlotUpdateS2CPacket(screenHandler.syncId, screenHandler.nextRevision(), 0, itemStack));
		}
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		PopKorn.LOGGER.info("S43J content changed");
		if (!this.field_51625) {
			this.context.run((world, pos) -> {
				craft(this, world, this.player, this.inventory, null);
			});
		}
	}


	@Override
	protected void method_59964(RecipeHolder<DemonicAltarRecipe> recipeHolder) {
		PopKorn.LOGGER.info("S43J something but field is false");
		this.field_51625 = false;
		this.context.run((world, pos) -> {
			craft(this, world, this.player, this.inventory, recipeHolder);
		});
	}

	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
		PopKorn.LOGGER.info("S43J populateRecipeFinder instance of");
		if(this.inventory instanceof RecipeInputProvider) {
			PopKorn.LOGGER.info("S43J populateRecipeFinder instance of");
			((RecipeInputProvider) this.inventory).provideRecipeInputs(finder);
		}
	}

	@Override
	public void clearCraftingSlots() {
		PopKorn.LOGGER.info("S43J clear");
		this.inventory.clear();
	}

	@Override
	public boolean matches(RecipeHolder<DemonicAltarRecipe> recipe) {
		boolean b = recipe.value().matches(new DemonicAltarRecipeInput(this.inventory.getStack(BASE_SLOT), this.inventory.getStack(LEFT_SLOT), this.inventory.getStack(TOP_SLOT), this.inventory.getStack(RIGHT_SLOT), this.inventory.getStack(BOTTOM_SLOT)), this.player.getWorld());
		PopKorn.LOGGER.info("S43J matches screen handler {}", b);
		return b;
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 5;
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
		return 5;
	}

	@Override
	public RecipeBookCategory getCategory() {
		return RecipeBookCategory.CRAFTING;
	}

	@Override
	public boolean canInsertIntoSlot(int index) {
		return index != 5;
	}
}

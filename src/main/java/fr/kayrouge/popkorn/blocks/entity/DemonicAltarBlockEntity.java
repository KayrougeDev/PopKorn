package fr.kayrouge.popkorn.blocks.entity;

import fr.kayrouge.popkorn.screen.DemonicAltarScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.CraftingHandler;
import net.minecraft.recipe.RecipeHolder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.registry.HolderLookup;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DemonicAltarBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, CraftingHandler, RecipeInputProvider {

	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

	public DemonicAltarBlockEntity(BlockPos pos, BlockState state) {
		super(PKBlockEntityTypes.DEMONIC_ALTAR, pos, state);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public @Nullable ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new DemonicAltarScreenHandler(i, playerInventory, this, ScreenHandlerContext.create(world, pos));
	}

	@Override
	protected void readNbtImpl(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.readNbtImpl(nbt, lookupProvider);
		Inventories.readNbt(nbt, this.inventory, lookupProvider);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, HolderLookup.Provider lookupProvider) {
		super.writeNbt(nbt, lookupProvider);
		Inventories.writeNbt(nbt, this.inventory, lookupProvider);
	}

	@Override
	public void onResultUpdate(@Nullable RecipeHolder<?> recipe) {

	}

	@Override
	public @Nullable RecipeHolder<?> getLastRecipe() {
		return null;
	}

	@Override
	public void provideRecipeInputs(RecipeMatcher finder) {
		for (ItemStack stack : this.inventory) {
			finder.addInput(stack);
		}
	}
}

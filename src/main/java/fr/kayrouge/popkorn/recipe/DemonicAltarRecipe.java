package fr.kayrouge.popkorn.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.kayrouge.popkorn.PopKorn;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.HolderLookup;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.stream.Stream;

public class DemonicAltarRecipe implements Recipe<DemonicAltarRecipeInput> {
	final Ingredient base;
	final Ingredient left;
	final Ingredient top;
	final Ingredient right;
	final Ingredient bottom;
	final ItemStack result;

	public DemonicAltarRecipe(Ingredient base, Ingredient left, Ingredient top, Ingredient right, Ingredient bottom, ItemStack stack) {
		this.left = left;
		this.base = base;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.result = stack;
	}

	@Override
	public boolean matches(DemonicAltarRecipeInput input, World world) {
		boolean b =this.base.test(input.base()) && this.left.test(input.left()) && this.top.test(input.top()) && this.right.test(input.right()) && this.bottom.test(input.bottom());
		PopKorn.LOGGER.info("S43J matches recipe {}", b);
		return b;
	}

	@Override
	public ItemStack craft(DemonicAltarRecipeInput input, HolderLookup.Provider provider) {
		PopKorn.LOGGER.info("S43J CRAFT");
		return this.result.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResult(HolderLookup.Provider provider) {
		PopKorn.LOGGER.info("S43J RESULT {}",this.result.getItem().getTranslationKey());
		return this.result;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return PKRecipes.DEMONIC_ALTAR_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return PKRecipes.DEMONIC_ALTAR_RECIPE_TYPE;
	}

	@Override
	public boolean isEmpty() {
		return Stream.of(this.base, this.left, this.top, this.right).anyMatch(Ingredient::isEmpty);
	}

	public static class Serializer implements RecipeSerializer<DemonicAltarRecipe> {
		private static final MapCodec<DemonicAltarRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter((recipe) -> recipe.base), Ingredient.ALLOW_EMPTY_CODEC.fieldOf("left").forGetter((recipe) -> recipe.left), Ingredient.ALLOW_EMPTY_CODEC.fieldOf("top").forGetter((recipe) -> recipe.top), Ingredient.ALLOW_EMPTY_CODEC.fieldOf("right").forGetter((recipe) -> recipe.right), Ingredient.ALLOW_EMPTY_CODEC.fieldOf("bottom").forGetter((recipe) -> recipe.bottom), ItemStack.field_51397.fieldOf("result").forGetter((recipe) -> recipe.result)).apply(instance, DemonicAltarRecipe::new));
		public static final PacketCodec<RegistryByteBuf, DemonicAltarRecipe> PACKET_CODEC = PacketCodec.create(DemonicAltarRecipe.Serializer::write, DemonicAltarRecipe.Serializer::read);

		public Serializer() {
		}

		public MapCodec<DemonicAltarRecipe> getCodec() {
			return CODEC;
		}

		public PacketCodec<RegistryByteBuf, DemonicAltarRecipe> getPacketCodec() {
			return PACKET_CODEC;
		}

		private static DemonicAltarRecipe read(RegistryByteBuf buf) {
			Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
			Ingredient ingredient2 = Ingredient.PACKET_CODEC.decode(buf);
			Ingredient ingredient3 = Ingredient.PACKET_CODEC.decode(buf);
			Ingredient ingredient4 = Ingredient.PACKET_CODEC.decode(buf);
			Ingredient ingredient5 = Ingredient.PACKET_CODEC.decode(buf);

			ItemStack stack = ItemStack.PACKET_CODEC.decode(buf);

			if(Arrays.stream(ingredient.getMatchingStacks()).findFirst().isPresent()) {
				PopKorn.LOGGER.info("S43J READ BASE {}", Arrays.stream(ingredient.getMatchingStacks()).findFirst().get().getItem().getTranslationKey());
			}



			return new DemonicAltarRecipe(ingredient, ingredient2, ingredient3, ingredient4, ingredient5, stack);
		}

		private static void write(RegistryByteBuf buf, DemonicAltarRecipe recipe) {
			Ingredient.PACKET_CODEC.encode(buf, recipe.base);
			Ingredient.PACKET_CODEC.encode(buf, recipe.left);
			Ingredient.PACKET_CODEC.encode(buf, recipe.top);
			Ingredient.PACKET_CODEC.encode(buf, recipe.right);
			Ingredient.PACKET_CODEC.encode(buf, recipe.bottom);

			ItemStack.PACKET_CODEC.encode(buf, recipe.result);
		}
	}
}

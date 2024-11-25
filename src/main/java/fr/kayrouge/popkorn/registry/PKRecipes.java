package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PKRecipes {

	public static final RecipeType<DemonicAltarRecipe> DEMONIC_ALTAR_RECIPE_TYPE;
	public static final RecipeSerializer<DemonicAltarRecipe> DEMONIC_ALTAR_RECIPE_SERIALIZER;

	static {
		DEMONIC_ALTAR_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(PopKorn.MODID, "demonic_altar"), new RecipeType<DemonicAltarRecipe>() {
			@Override
			public String toString() { return "demonic_altar"; }
		});
		DEMONIC_ALTAR_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(PopKorn.MODID, "demonic_altar"), new DemonicAltarRecipe.Serializer());
	}

	public static void initialize() {}
}

package fr.kayrouge.popkorn;

import com.mojang.serialization.JsonOps;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import fr.kayrouge.popkorn.blocks.entity.PKBlockEntityTypes;
import fr.kayrouge.popkorn.client.screen.PKHandledScreens;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import fr.kayrouge.popkorn.items.PKItems;
import fr.kayrouge.popkorn.recipe.DemonicAltarRecipe;
import fr.kayrouge.popkorn.recipe.PKRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopKorn implements ModInitializer {


	public static final String MODID = "popkorn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final Boolean DEBUG = false;

	@Override
	public void onInitialize(ModContainer mod) {
		PKBlocks.initialize();
		PKHandledScreens.initialize();
		PKBlockEntityTypes.initialize();
		PKItems.initialize();
		PKRecipes.initialize();

		DemonicAltarRecipe recipe = new DemonicAltarRecipe(Ingredient.ofItems(Items.BAMBOO), Ingredient.ofItems(Items.LADDER), Ingredient.ofItems(Items.TADPOLE_BUCKET), Ingredient.ofItems(Items.RAIL), Ingredient.ofItems(Items.BREAD), new ItemStack(Items.RABBIT_FOOT));
		PopKorn.LOGGER.info(DemonicAltarRecipe.CODEC.encodeStart(JsonOps.INSTANCE, recipe).getOrThrow().toString());

		PKNetworkingConstants.registerC2S();
		PKNetworkingConstants.registerS2C();
		PKNetworkingConstants.registerC2SGlobalReceiver();
	}
}

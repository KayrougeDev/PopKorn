package fr.kayrouge.popkorn;

import fr.kayrouge.popkorn.registry.PKBlocks;
import fr.kayrouge.popkorn.registry.PKBlockEntityTypes;
import fr.kayrouge.popkorn.items.group.PKItemGroups;
import fr.kayrouge.popkorn.registry.PKHandledScreens;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import fr.kayrouge.popkorn.registry.PKItems;
import fr.kayrouge.popkorn.registry.PKRecipes;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopKorn implements ModInitializer {


	public static final String MODID = "popkorn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final Boolean DEBUG = true;

	@Override
	public void onInitialize(ModContainer mod) {
		PKRecipes.initialize();
		PKHandledScreens.initialize();

		PKBlocks.initialize();
		PKBlockEntityTypes.initialize();
		PKItems.initialize();
		PKItemGroups.initialize();

		PKNetworkingConstants.registerC2S();
		PKNetworkingConstants.registerS2C();
		PKNetworkingConstants.registerC2SGlobalReceiver();
	}
}

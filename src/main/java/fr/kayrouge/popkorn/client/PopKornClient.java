package fr.kayrouge.popkorn.client;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.screen.PKHandledScreens;
import fr.kayrouge.popkorn.client.screen.ingame.DemonicAltarScreen;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopKornClient implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(PopKorn.MODID);

	@Override
	public void onInitializeClient(ModContainer mod) {
		PKNetworkingConstants.registerS2CGlobalReceiver();
		PKKeybindings.registerAction();

		HandledScreens.register(PKHandledScreens.DEMONIC_ALTAR_SCREEN_HANDLER, DemonicAltarScreen::new);
	}
}

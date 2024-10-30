package fr.kayrouge.popkorn.client.screen;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.screen.DemonicAltarScreenHandler;
import net.minecraft.feature_flags.FeatureFlagBitSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class PKHandledScreens {

	public static final ScreenHandlerType<DemonicAltarScreenHandler> DEMONIC_ALTAR_SCREEN_HANDLER;

	static {
		DEMONIC_ALTAR_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER_TYPE, Identifier.of(PopKorn.MODID, "demonic_altar"), new ScreenHandlerType<>(DemonicAltarScreenHandler::new, FeatureFlagBitSet.empty()));
	}


	public static void initialize() {
	}
}

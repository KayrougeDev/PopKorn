package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.screen.DemonicAltarScreenHandler;
import fr.kayrouge.popkorn.screen.ItemDisplayScreenHandler;
import fr.kayrouge.popkorn.screen.data.ItemDisplayData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.feature_flags.FeatureFlagBitSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class PKHandledScreens {

	public static final ScreenHandlerType<DemonicAltarScreenHandler> DEMONIC_ALTAR_SCREEN_HANDLER;
	public static final ExtendedScreenHandlerType<ItemDisplayScreenHandler, ItemDisplayData> ITEM_DISPLAY_SCREEN_HANDLER;


	static {
		DEMONIC_ALTAR_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER_TYPE,
			Identifier.of(PopKorn.MODID, "demonic_altar"),
			new ScreenHandlerType<>(DemonicAltarScreenHandler::new, FeatureFlagBitSet.empty()));

		ITEM_DISPLAY_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER_TYPE,
			Identifier.of(PopKorn.MODID, "item_display"),
			new ExtendedScreenHandlerType<>(ItemDisplayScreenHandler::new, ItemDisplayData.PACKET_CODEC));

	}


	public static void initialize() {
	}
}

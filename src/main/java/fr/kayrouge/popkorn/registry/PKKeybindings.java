package fr.kayrouge.popkorn.registry;

import com.mojang.blaze3d.platform.InputUtil;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.screen.ConfigScreen;
import fr.kayrouge.popkorn.network.packet.c2s.AbilitiesUseC2SPayload;
import fr.kayrouge.popkorn.util.PlayerUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBind;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.QuiltLoader;

public class PKKeybindings {

	public static final KeyBind DASH;
	public static final KeyBind CHAINSAW;

	public static final KeyBind TEST;

	@Nullable
	public static final KeyBind OPEN_CONFIG;

	static {
		DASH = KeyBindingHelper.registerKeyBinding(new KeyBind("key.popkorn.dash", InputUtil.Type.MOUSE , GLFW.GLFW_MOUSE_BUTTON_4, KeyBind.MOVEMENT_CATEGORY));
		CHAINSAW = KeyBindingHelper.registerKeyBinding(new KeyBind("key.popkorn.chainsaw", InputUtil.Type.MOUSE , GLFW.GLFW_MOUSE_BUTTON_5, KeyBind.GAMEPLAY_CATEGORY));

		if(PopKorn.DEBUG) {
			TEST = KeyBindingHelper.registerKeyBinding(new KeyBind("key.popkorn.test", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, KeyBind.MISC_CATEGORY));
		} else {
			TEST = null;
		}

		if(!QuiltLoader.isModLoaded("modmenu")) {
			OPEN_CONFIG = KeyBindingHelper.registerKeyBinding(new KeyBind("key.popkorn.open_config", InputUtil.Type.KEYSYM , GLFW.GLFW_KEY_RIGHT_ALT, KeyBind.MISC_CATEGORY));
		}
		else {
			OPEN_CONFIG = null;
		}
	}

	public static void registerAction() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(DASH.wasPressed()) {
				ClientPlayNetworking.send(new AbilitiesUseC2SPayload("dash"));
			}

			while(CHAINSAW.wasPressed()) {
				ClientPlayNetworking.send(new AbilitiesUseC2SPayload("chainsaw"));
			}
		});

		if(OPEN_CONFIG != null) {
			ClientTickEvents.END_CLIENT_TICK.register(client -> {
				while (OPEN_CONFIG.wasPressed()) {
					client.setScreen(new ConfigScreen(null));
				}
			});
		}

		if(TEST != null) {
			ClientTickEvents.END_CLIENT_TICK.register(client -> {
				while (TEST.wasPressed()) {
					PlayerUtil.startDisplayInfo();
				}
			});
		}
	}

}

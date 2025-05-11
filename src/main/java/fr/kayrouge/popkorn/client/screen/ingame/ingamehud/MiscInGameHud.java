package fr.kayrouge.popkorn.client.screen.ingame.ingamehud;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import fr.kayrouge.popkorn.registry.PKComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Objects;

public class MiscInGameHud {

	private static final Identifier BROKEN_HEART_ICON = Identifier.of(PopKorn.MODID, "hud/soulmate/broken_heart");

	public static void render(GuiGraphics graphics, MinecraftClient client) {
		if(Objects.isNull(client.player)) return;
		TextRenderer textRend = client.textRenderer;

		if(PKComponents.PLAYER_DATA.get(client.player).isHeartbroken()) {
			renderHeartBroken(graphics, client);
		}
	}

	public static void renderHeartBroken(GuiGraphics graphics, MinecraftClient client) {
		graphics.drawGuiTexture(BROKEN_HEART_ICON, 5, graphics.getScaledWindowHeight()-21, 16, 16);
	}

}

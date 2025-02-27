package fr.kayrouge.popkorn.client.screen.ingame.ingamehud;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.abilities.Ability;
import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import fr.kayrouge.popkorn.util.configs.PopKornClientConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class AbilitiesInGameHui {


	private static final Identifier ICON_BASE = Identifier.of(PopKorn.MODID, "hud/abilities/icon_base");
	private static final Identifier ICON_NOT_AVAILABLE = Identifier.of(PopKorn.MODID, "hud/abilities/not_available");


	public static void renderAbilitiesIcon(GuiGraphics graphics) {
		final int iconSize = 22;
		final int iconHeight = graphics.getScaledWindowHeight()-iconSize;
		// graphics.getScaledWindowWidth()/2: middle of the screen, 91: half of the hotbar's size
		float iconPos = (float)graphics.getScaledWindowWidth() / 2 - 91;
		//iconPos -= iconSize*2;
		float startPos = (float)graphics.getScaledWindowWidth()/2+91 + (iconPos / 3);
		MatrixStack matrices = graphics.getMatrices();

		RenderSystem.enableBlend();
		matrices.push();
		matrices.translate(startPos, iconHeight, -91f);

		switch (ClientPlayerManager.getInstance().getAbilitiesState()) {

			case NORMAL:
				renderIcon(graphics, -iconSize+(iconSize/4), 0, iconSize, 1.5f, 0,2.5f,-0.25F, "dash", ClientPlayerManager.getInstance().getAbility("dash"));
				renderIcon(graphics, iconSize+(iconSize/2), 0, iconSize, 0, 0, 0,0, "icon_edge",  ClientPlayerManager.getInstance().getAbility("chainsaw"));
				break;

			case CANTBELOADED:
				matrices.scale(0.75F, 0.75F, 1F);
				renderAbilitiesUnavailable(graphics, (iconSize/2));
				break;

			case DISABLED:
				matrices.scale(0.75F, 0.75F, 1F);
				renderAbilitiesDisabled(graphics, (iconSize/2));
				break;

		}

		matrices.pop();
		RenderSystem.disableBlend();
	}

	private static void renderAbilitiesUnavailable(GuiGraphics graphics, int y) {
		if(PopKornClientConfig.INSTANCE.drawUnableToRecoverAbilitiesDataString.value()) {
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			Text text = Text.translatable("abilities.hud.cantrecoverdata");
			graphics.drawText(textRenderer, text, -(textRenderer.getWidth(text)/3), y, Color.ORANGE.getRGB(), false);
		}
	}

	private static void renderAbilitiesDisabled(GuiGraphics graphics, int y) {
		if(PopKornClientConfig.INSTANCE.drawAbilitiesDisabledString.value()) {
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			Text text = Text.translatable("abilities.hud.disabled");
			graphics.drawText(textRenderer, text, -(textRenderer.getWidth(text)/3), y, Color.ORANGE.getRGB(), false);
		}
	}

	private static void renderIcon(GuiGraphics graphics, int x, int y, int size, float offsetX, int offsetY, float correctionOffsetX, float correctionOffsetY, String name, Ability ability) {
		//TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		//graphics.drawCenteredShadowedText(textRenderer, Text.keyBind(key.getTranslationKey()), x+(size/2), y+(size/2)-(textRenderer.fontHeight/2), Color.WHITE.getRGB());
		graphics.drawGuiTexture(ICON_BASE, x, y, size, size);


		graphics.getMatrices().push();

		boolean b1 = ability.getRemainingCharges() < ability.getMaxCharges() && ability.isAvailable();

		if(ability.getMaxCharges() == 1 || b1 || ability.getMaxCharges() == 0) {
			graphics.getMatrices().translate(offsetX, offsetY, 0f);
			graphics.drawGuiTexture(Identifier.of(PopKorn.MODID, "hud/abilities/"+name), x, y, size, size);
		}
		else {
			graphics.getMatrices().translate(correctionOffsetX, correctionOffsetY, 0f);
			graphics.drawGuiTexture(Identifier.of(PopKorn.MODID, "hud/abilities/"+name), (x-size/6), y, size, size);
			graphics.drawGuiTexture(Identifier.of(PopKorn.MODID, "hud/abilities/"+name), (x+size/6), y, size, size);
		}
		graphics.getMatrices().pop();

		if(!ability.isAvailable()) {
			graphics.drawGuiTexture(ICON_NOT_AVAILABLE, x, y, size, size);
		}
	}

}

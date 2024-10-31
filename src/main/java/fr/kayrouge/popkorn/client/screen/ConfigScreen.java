package fr.kayrouge.popkorn.client.screen;

import fr.kayrouge.popkorn.util.configs.PopKornClientConfig;
import fr.kayrouge.popkorn.util.configs.PopKornServerConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.text.Text;

import java.awt.*;

public class ConfigScreen extends Screen {

	private final Screen parent;

	public ConfigScreen(Screen parent) {
		super(Text.translatable("title.popkorn.config"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		this.addDrawableSelectableElement(ButtonWidget
			.builder(Text.translatable(
					"title.popkorn.config.server"),
				buttonWidget -> client.setScreen(PopKornServerConfig.INSTANCE.init(this))
			).position(this.width/2-ButtonWidget.DEFAULT_WIDTH/2, this.height/2-10-ButtonWidget.DEFAULT_HEIGHT)
			.build());

		this.addDrawableSelectableElement(ButtonWidget
			.builder(Text.translatable(
				"title.popkorn.config.client"),
				buttonWidget -> client.setScreen(PopKornClientConfig.INSTANCE.init(this))
			).position(this.width/2-ButtonWidget.DEFAULT_WIDTH/2, this.height/2+10)
			.build());
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredShadowedText(textRenderer, getTitle(), graphics.getScaledWindowWidth()/2, 20, Color.WHITE.getRGB());
	}

	@Override
	public void closeScreen() {
		client.setScreen(this.parent);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}

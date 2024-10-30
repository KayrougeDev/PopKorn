package fr.kayrouge.popkorn.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.OutlinedMultilineTextWidget;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Objects;


public class PreTitleScreen extends Screen {

	private OutlinedMultilineTextWidget textWidget;
	private ButtonWidget closeButton;

	public PreTitleScreen() {
		super(Text.translatable("debug.popkorn.screen.debug").setColor(new Color(0xFF5B5B).getRGB()));
	}

	@Override
	protected void init() {
		this.textWidget = this.addDrawableSelectableElement(new OutlinedMultilineTextWidget(this.width, this.title, this.textRenderer, 12));
		this.closeButton = this.addDrawableSelectableElement(ButtonWidget
			.builder(Text.translatable("mco.selectServer.close"), buttonWidget -> gotoTitleScreen())
			.build());
		this.repositionElements();
	}

	@Override
	protected void repositionElements() {
		if (this.textWidget != null) {
			this.textWidget.updateMaxWidth(this.width);
			OutlinedMultilineTextWidget var10000 = this.textWidget;
			int var10001 = this.width / 2 - this.textWidget.getWidth() / 2;
			int var10002 = this.height / 2;
			Objects.requireNonNull(this.textRenderer);
			var10000.setPosition(var10001, var10002 - 9 / 2);
		}
		if(this.closeButton != null) {
			this.closeButton.setPosition(this.width/2 - (150/2), this.height / 2 + this.textWidget.getHeight());
		}
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderPanorama(graphics, delta);
		this.renderBlur(delta);
		this.renderMenuBackground(graphics);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	@Override
	public void closeScreen() {
		gotoTitleScreen();
	}

	private void gotoTitleScreen() {
		MinecraftClient.getInstance().setScreen(new TitleScreen());
	}
}

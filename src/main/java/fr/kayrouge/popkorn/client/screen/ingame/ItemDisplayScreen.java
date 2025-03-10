package fr.kayrouge.popkorn.client.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.network.packet.c2s.UpdateItemDisplayC2SPayload;
import fr.kayrouge.popkorn.screen.ItemDisplayScreenHandler;
import fr.kayrouge.popkorn.util.ScreenUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.awt.*;

@ClientOnly
public class ItemDisplayScreen extends HandledScreen<ItemDisplayScreenHandler> {

	private static final Identifier TEXTURE = Identifier.of(PopKorn.MODID, "textures/gui/container/item_display.png");

	public ItemDisplayScreen(ItemDisplayScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		this.renderBackground(graphics, mouseX, mouseY, delta);
		super.render(graphics, mouseX, mouseY, delta);
		drawMouseoverTooltip(graphics, mouseX, mouseY);

		//graphics.drawShadowedText(textRenderer, Text.literal(handler.getData().getPos().toString()), 0, 70, Color.white.getRGB());
	}

	@Override
	protected void drawBackground(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		graphics.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
	}

	@Override
	protected void init() {
		super.init();
		final int startGUIX = (width-backgroundWidth)/2;
		final int startGUIY = (height-backgroundHeight)/2;
		this.addDrawableSelectableElement(ButtonWidget.builder(generateMessage("rotate", handler.getData().isRotate()), buttonWidget -> {
			if(client == null) return;
			if(client.player != null && ScreenUtil.canPlayerUseGuiButton(client.player, handler.getData().getPos(), 1d)) {
				handler.getData().setRotate(!handler.getData().isRotate());
				update();
				buttonWidget.setMessage(generateMessage("rotate", handler.getData().isRotate()));
			}
		}).position(this.width/2+5, startGUIY+31).build());

		this.addDrawableSelectableElement(ButtonWidget.builder(generateMessage("upAndDown", handler.getData().isUpAnDown()), buttonWidget -> {
			if(client == null) return;
			if(client.player != null && ScreenUtil.canPlayerUseGuiButton(client.player, handler.getData().getPos(), 1d)) {
				handler.getData().setUpAnDown(!handler.getData().isUpAnDown());
				update();
				buttonWidget.setMessage(generateMessage("upAndDown", handler.getData().isUpAnDown()));
			}
		}).position(this.width/2+5, startGUIY+47).build());

	}

	public void update() {
		ClientPlayNetworking.send(new UpdateItemDisplayC2SPayload(
			handler.getData().getPos(),
			handler.getData().isRotate(),
			handler.getData().isUpAnDown()));
	}


	private Text generateMessage(String message, boolean value) {
		return Text.literal(message).append(" ").append(String.valueOf(value));
	}
}

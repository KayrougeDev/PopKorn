package fr.kayrouge.popkorn.client.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.screen.DemonicAltarScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.CraftingRecipeInput;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.SmithingRecipeInput;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DemonicAltarScreen extends HandledScreen<DemonicAltarScreenHandler> {

	private static final Identifier TEXTURE = Identifier.of(PopKorn.MODID, "textures/gui/container/demonic_altar.png");

	public DemonicAltarScreen(DemonicAltarScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
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
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		renderBackground(graphics, mouseX, mouseY, delta);
		super.render(graphics, mouseX, mouseY, delta);
		drawMouseoverTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void init() {
		super.init();
		this.titleY = 4;
		this.titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
		this.playerInventoryTitleY = this.backgroundHeight - 92;
	}
}

package fr.kayrouge.popkorn.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.kayrouge.popkorn.client.screen.ingame.ingamehud.AbilitiesInGameHui;
import fr.kayrouge.popkorn.client.screen.ingame.ingamehud.MiscInGameHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.in_game.InGameHud;
import net.minecraft.client.render.DeltaTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "render", at = @At("TAIL"))
	public void render(GuiGraphics graphics, DeltaTracker tracker, CallbackInfo ci) {
		RenderSystem.enableDepthTest();

		AbilitiesInGameHui.renderAbilitiesIcon(graphics);
		MiscInGameHud.render(graphics, client);

		RenderSystem.disableDepthTest();
	}
}

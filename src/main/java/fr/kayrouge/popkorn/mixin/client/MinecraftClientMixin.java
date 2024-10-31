package fr.kayrouge.popkorn.mixin.client;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import fr.kayrouge.popkorn.client.screen.PreTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

/* Tested something
	@Unique
	private boolean firstTime = true;

	@Shadow
	@Nullable
	public Screen currentScreen;

	@Shadow
	public abstract void updateWindowTitle();

	@Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
	public void onSetScreen(Screen screen, CallbackInfo ci) {
		if(screen instanceof TitleScreen) {
			if(firstTime && PopKorn.DEBUG) {
				currentScreen = new PreTitleScreen();
				updateWindowTitle();
				firstTime = false;
				ci.cancel();
			}
		}
	}*/


	// Methode called when the player quit a server / world
	@Inject(method = "method_18096", at = @At("HEAD"))
	public void onDisconnected(Screen screen, boolean bl, CallbackInfo ci) {
		/*
		* Add this because without it, if you join a server that doesn't PopKorn installed, it
		* will still show the abilities' icon
		* */
		ClientPlayerManager.clearAbilities();
	}
}

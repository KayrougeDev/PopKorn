package fr.kayrouge.popkorn.mixin.client;

import com.mojang.blaze3d.glfw.Window;
import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.*;
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
	}
	*/

	@Shadow
	protected abstract String getWindowTitle();

	@Shadow
	@Final
	private Window window;

	@Shadow
	public abstract Session getSession();

	// Methode called when the player quit a server / world
	@Inject(method = "method_18096", at = @At("HEAD"))
	public void onDisconnected(Screen screen, boolean bl, CallbackInfo ci) {

	}

	/**
	 * @author KayrougeDev
	 * @reason Add player name in window title
	 */
	@Overwrite
	public void updateWindowTitle() {
		this.window.setTitle(getWindowTitle()+" | "+getSession().getUsername());
	}

	// Called when join / quit world
	@Inject(method = "method_18096", at = @At("HEAD"))
	public void joinWorld(Screen screen, boolean bl, CallbackInfo ci) {
		ClientPlayerManager.reset();
	}
}

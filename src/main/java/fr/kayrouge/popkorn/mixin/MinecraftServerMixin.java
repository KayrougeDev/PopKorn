package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.server.manager.PlayerManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Shadow
	private net.minecraft.server.PlayerManager playerManager;

	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		for (ServerPlayerEntity player : this.playerManager.getPlayerList()) {
			PlayerManager.tick(player);
		}
	}

}

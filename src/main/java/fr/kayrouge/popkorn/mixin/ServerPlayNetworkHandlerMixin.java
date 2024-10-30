package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.server.manager.PlayerManager;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.ConnectedClientData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	public void onPlayerJoin(MinecraftServer server, ClientConnection connection, ServerPlayerEntity player, ConnectedClientData connectedClient, CallbackInfo ci) {
		PlayerManager.initPlayerAbilities(player);
		PlayerManager.updateClient(player);
	}

}

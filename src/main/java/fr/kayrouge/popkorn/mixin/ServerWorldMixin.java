package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.network.packet.c2s.SoulmateConnectionC2SPayload;
import fr.kayrouge.popkorn.registry.PKComponents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

	@Inject(method = "addPlayer", at =  @At("HEAD"))
	public void addPlayer(ServerPlayerEntity player, CallbackInfo ci) {
		boolean isHeartBroken = PKComponents.PLAYER_DATA.get(player).isHeartbroken();
		if(isHeartBroken) {
			SoulmateConnectionC2SPayload.giveHeartBrokenModifier(player);
		}
	}
}

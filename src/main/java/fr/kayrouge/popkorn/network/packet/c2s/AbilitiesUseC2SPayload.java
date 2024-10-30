package fr.kayrouge.popkorn.network.packet.c2s;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import fr.kayrouge.popkorn.network.packet.s2c.AbilitiesUseConfirmationS2CPayload;
import fr.kayrouge.popkorn.network.packet.s2c.PlayerAbilitiesUpdateS2CPayload;
import fr.kayrouge.popkorn.server.manager.PlayerManager;
import fr.kayrouge.popkorn.abilities.Ability;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record AbilitiesUseC2SPayload(String ability) implements CustomPayload {

	public static final CustomPayload.Id<AbilitiesUseC2SPayload> ID = new CustomPayload.Id<>(PKNetworkingConstants.ABILITIES_USE_PACKET_ID);

	public static final PacketCodec<RegistryByteBuf, AbilitiesUseC2SPayload> CODEC = PacketCodec.tuple(
		PacketCodecs.STRING, AbilitiesUseC2SPayload::ability,
		AbilitiesUseC2SPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public void receive(ServerPlayNetworking.Context context) {
		context.server().execute(() -> {
			ServerPlayerEntity player = context.player();
			if(!PlayerManager.getAbilities(player).containsKey(ability)) {
				PopKorn.LOGGER.warn("Receive a packet use for ability '{}' which don't exist !", ability);
				return;
			}
			Ability receiveAbility = PlayerManager.getAbilities(player).get(ability);
			if(receiveAbility.isAvailable()) {
				if(receiveAbility.use(player)) {
					context.responseSender().sendPacket(new PlayerAbilitiesUpdateS2CPayload(PlayerManager.getAbilities(player)));
					if(receiveAbility.needConfirmation()) {
						context.responseSender().sendPacket(new AbilitiesUseConfirmationS2CPayload(ability));
					}
				}
			}
			else {
				receiveAbility.cantUse(player);
			}

			if(PopKorn.DEBUG) {
				PopKorn.LOGGER.info("Class of used ability is {}", receiveAbility.getClass().toGenericString());
			}
		});
	}
}

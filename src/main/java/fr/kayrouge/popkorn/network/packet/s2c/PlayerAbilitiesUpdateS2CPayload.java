package fr.kayrouge.popkorn.network.packet.s2c;

import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import fr.kayrouge.popkorn.network.packet.PKPacketCodecs;
import fr.kayrouge.popkorn.abilities.Ability;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.payload.CustomPayload;

import java.util.Map;

public record PlayerAbilitiesUpdateS2CPayload(Map<String, Ability> abilities) implements CustomPayload {

	public static final CustomPayload.Id<PlayerAbilitiesUpdateS2CPayload> ID = new CustomPayload.Id<>(PKNetworkingConstants.ABILITIES_UPDATE_PACKET_ID);

	public static final PacketCodec<RegistryByteBuf, PlayerAbilitiesUpdateS2CPayload> CODEC = PacketCodec.tuple(
		PKPacketCodecs.ABILITY_MAP, PlayerAbilitiesUpdateS2CPayload::abilities,
		PlayerAbilitiesUpdateS2CPayload::new);


	public void receive(ClientPlayNetworking.Context context) {
		context.client().execute(() -> {
			ClientPlayerManager.getInstance().setAbilities(abilities);
//			for(Map.Entry<String, Ability> entry : abilities.entrySet()) {
//				context.player().sendMessage(Text.literal(entry.getKey() + " " + entry.getValue().getRemainingCharges()), false);
//			}
		});
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}



}

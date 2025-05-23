package fr.kayrouge.popkorn.network.packet.s2c;

import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;

public record AbilitiesUseS2CPayload(String ability, boolean used) implements CustomPayload {

	public static final CustomPayload.Id<AbilitiesUseS2CPayload> ID = new CustomPayload.Id<>(PKNetworkingConstants.ABILITIES_USE_CONFIRMATION_PACKET_ID);

	public static final PacketCodec<RegistryByteBuf, AbilitiesUseS2CPayload> CODEC = PacketCodec.tuple(
		PacketCodecs.STRING, AbilitiesUseS2CPayload::ability,
		PacketCodecs.BOOL, AbilitiesUseS2CPayload::used,
		AbilitiesUseS2CPayload::new);

	public void receive(ClientPlayNetworking.Context context) {
		context.client().execute(() -> {
			ClientPlayerManager.getInstance().getAbility(ability).setUsed(used);
		});
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

package fr.kayrouge.popkorn.network.packet.s2c;

import fr.kayrouge.popkorn.client.renderer.PKRenderers;
import fr.kayrouge.popkorn.client.renderer.WorldCustomRenderer;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import org.joml.Vector3f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.awt.*;

public record RayLauncherUseS2CPayload(Vector3f startPos, Vector3f endPos) implements CustomPayload {

	public static final CustomPayload.Id<RayLauncherUseS2CPayload> ID = new CustomPayload.Id<>(PKNetworkingConstants.RAY_LAUNCHER_USE_PACKET_ID);

	public static final PacketCodec<RegistryByteBuf, RayLauncherUseS2CPayload> CODEC = PacketCodec.tuple(
		PacketCodecs.VECTOR3F, RayLauncherUseS2CPayload::startPos,
		PacketCodecs.VECTOR3F, RayLauncherUseS2CPayload::endPos,
		RayLauncherUseS2CPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	@ClientOnly
	public void receive(ClientPlayNetworking.Context context) {
		PKRenderers.INSTANCE.addRenderTask((renderContext, endTimeMillis) -> {

			WorldCustomRenderer.renderLineInWorld(startPos, endPos,
				Color.RED, Color.ORANGE,
				5d,
				renderContext.matrices(), renderContext.vertexConsumers());

			return () -> {};
		},1300L);
	}
}

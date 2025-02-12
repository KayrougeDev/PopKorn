package fr.kayrouge.popkorn.network.packet.s2c;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.kayrouge.popkorn.client.renderer.PKRenderers;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import org.joml.Vector3f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

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
		PKRenderers.INSTANCE.addRenderTask((matrices, provider, endTimeMillis) -> {
			Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

			float cameraX = (float) camera.getPos().x;
			float cameraY = (float) camera.getPos().y;
			float cameraZ = (float) camera.getPos().z;

			float startX = startPos().x;
			float startY = startPos().y;
			float startZ = startPos().z;
			float adjustedStartX = startX - cameraX;
			float adjustedStartY = startY - cameraY;
			float adjustedStartZ = startZ - cameraZ;

			float endX = endPos().x;
			float endY = endPos().y;
			float endZ = endPos().z;
			float adjustedEndX = endX - cameraX;
			float adjustedEndY = endY - cameraY;
			float adjustedEndZ = endZ - cameraZ;

			float q = adjustedEndX-adjustedStartX;
			float r = adjustedEndY-adjustedStartY;
			float s = adjustedEndZ-adjustedStartZ;
			float t = (float) Math.sqrt(q * q + r * r + s * s);
			q /= t;
			r /= t;
			s /= t;

			matrices.push();
			//matrices.translate(adjustedX, adjustedY, adjustedZ);

			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.enableDepthTest();
			RenderSystem.disableBlend();
			RenderSystem.disableCull();
			RenderSystem.enablePolygonOffset();
			RenderSystem.lineWidth(2.0F);

			MatrixStack.Entry entry = matrices.peek();
			VertexConsumer consumer = provider.getBuffer(RenderLayer.LINES);

			consumer.xyz(entry, adjustedStartX, adjustedStartY-0.5F, adjustedStartZ)
				.color(1.F, 0f, 0f, 1f)
				.normal(entry, q, r, s);

			consumer.xyz(entry, adjustedEndX, adjustedEndY, adjustedEndZ)
				.color(1.F, 0f, 0f, 1f)
				.normal(entry, q, r, s);

			RenderSystem.disableBlend();
			RenderSystem.enableCull();
			matrices.pop();
		},625L);
	}
}

package fr.kayrouge.popkorn.abilities;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.network.packet.s2c.AbilitiesUseS2CPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class TPBackAbility extends TwoUseAbility {

	private BlockPos tpPos = null;

	public TPBackAbility(int maxCharges, int cooldownTime) {
		super(maxCharges, cooldownTime, true);
	}

	@Override
	public void firstUse(ServerPlayerEntity player) {
		super.firstUse(player);
		tpPos = player.getBlockPos();
		setUsed(true);
	}

	@Override
	public void secondUse(ServerPlayerEntity player) {
		super.secondUse(player);
		double x = tpPos.getX();
		double z = tpPos.getZ();
		x += x < 0 ? -.5d : .5d;
		z += z < 0 ? -.5d : .5d;
		player.teleport(x, tpPos.getY(), z, true);
		setUsed(false);
		tpPos = null;
	}

	@Override
	public boolean isFirstUse() {
		return tpPos == null;
	}

	@ClientOnly
	public static void renderTpPos(BlockPos pos, MatrixStack matrices, VertexConsumerProvider provider) {
		Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
		Vec3d cameraPos = camera.getPos();
		matrices.push();

		double posX = pos.getX();
		double posY = pos.getY();
		double posZ = pos.getZ();

		posX += posX < 0 ? -.5d : .5d;
		posY += 0.0001d;
		posZ += posZ < 0 ? -.5d : .5d;

		double x = posX - cameraPos.x;
		double y = posY - cameraPos.y;
		double z = posZ - cameraPos.z;

		Identifier identifier = Identifier.of(PopKorn.MODID, "textures/effects/demonic_altar_base.png");

		RenderSystem.setShaderTexture(0, identifier);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableDepthTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		Matrix4f matrix4f = matrices.peek().getModel();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

		float size = .5f;

		buffer.xyz(matrix4f, (float) x-size, (float) y, (float) z+size)
			.uv0(0, 0)
			.normal(matrices.peek(), 0f, 1f, 0f);

		buffer.xyz(matrix4f, (float) x + size, (float) y, (float) z+size)
			.uv0(1, 0)
			.normal(matrices.peek(), 0f, 1f, 0f);

		buffer.xyz(matrix4f, (float) x + size, (float) y, (float) z - size)
			.uv0(1, 1)
			.normal(matrices.peek(), 0f, 1f, 0f);

		buffer.xyz(matrix4f, (float) x-size, (float) y, (float) z-size)
			.uv0(0, 1)
			.normal(matrices.peek(), 0f, 1f, 0f);

		BufferRenderer.drawWithShader(buffer.endOrThrow());

		RenderSystem.disableBlend();
		matrices.pop();
	}
}

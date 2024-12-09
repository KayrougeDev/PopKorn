package fr.kayrouge.popkorn.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.kayrouge.popkorn.blocks.ChunkRendererBlock;
import fr.kayrouge.popkorn.registry.PKItems;
import fr.kayrouge.popkorn.util.RenderUtil;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.Iterator;
import java.util.Map;

public class WorldCustomRenderer {

	public static void render() {

		WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> !(blockOutlineContext.blockState().getBlock() instanceof ChunkRendererBlock));
		WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {

			if(blockOutlineContext.entity() instanceof PlayerEntity player) {
				if(player.getInventory().getArmorStack(3).getItem() == PKItems.LIGHT_GLASSES) {
					BlockHitResult result = (BlockHitResult) player.raycast(player.getBlockInteractionRange(), worldRenderContext.tickCounter().getTickDelta(true), false);
					BlockPos pos = blockOutlineContext.blockPos();
					pos = pos.offset(result.getSide());

					renderLightIndicator(result.getSide(), worldRenderContext.world().getLightLevel(pos), pos, worldRenderContext.matrixStack(), worldRenderContext.consumers());
					return true;
				}
			}

			return true;
		});
		WorldRenderEvents.LAST.register((worldRenderContext) -> {
			MinecraftClient client = worldRenderContext.gameRenderer().getClient();
			client.execute(() -> {
				Iterator<Map.Entry<PKRenderers.IPKRender, Long>> iterator = PKRenderers.INSTANCE.getRenderTasks().entrySet().iterator();

				while (iterator.hasNext()) {
					Map.Entry<PKRenderers.IPKRender, Long> task = iterator.next();

					task.getKey().render(worldRenderContext.matrixStack(), worldRenderContext.consumers(), task.getValue());

					if (task.getValue() < System.currentTimeMillis()) iterator.remove();
				}
			});
		});
	}

	public static void renderLightIndicator(Direction direction, int lightLevel, BlockPos pos, MatrixStack matrices, VertexConsumerProvider provider) {
		RenderUtil.renderTextOnBlockFace(direction, Text.literal(String.valueOf(lightLevel)), pos, matrices, provider, true);

		Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
		Vec3d cameraPos = camera.getPos();
		matrices.push();

		double x = pos.getX() - cameraPos.x;
		double y = pos.getY() - cameraPos.y;
		double z = pos.getZ() - cameraPos.z;

		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableDepthTest();
		RenderSystem.enableCull();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Matrix4f matrix4f = matrices.peek().getModel();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

		float r = 0.6F, g = 0.0F, b = 0.75F, a = .35F;
		matrices.translate(x, y, z);

		float displacementX = 0f;
		float displacementY = 0f;
		float displacementZ = 0f;

		switch (direction) {
			case NORTH -> {
				matrices.rotate(Axis.Z_POSITIVE.rotationDegrees(90f));
				matrices.rotate(Axis.X_NEGATIVE.rotationDegrees(90f));
				displacementY = -1f;
				displacementZ = -1f;
			}
			case SOUTH -> {
				matrices.rotate(Axis.X_POSITIVE.rotationDegrees(90f));
				displacementZ = -1f;
			}
			case WEST -> {
				displacementY = -1f;
				matrices.rotate(Axis.Z_POSITIVE.rotationDegrees(90f));
			}
			case EAST -> {
				displacementX = -1f;
				matrices.rotate(Axis.Z_NEGATIVE.rotationDegrees(90f));
			}
			case DOWN -> {
				displacementZ = -1f;
				displacementY = -1f;
				matrices.rotate(Axis.X_POSITIVE.rotationDegrees(180f));
			}
		}

		buffer.xyz(matrix4f, 0.5f+displacementX, 0.0001f+displacementY, 0f+displacementZ).color(r, g, b, a);
		buffer.xyz(matrix4f,0f+displacementX, 0.0001f+displacementY, 0.5f+displacementZ).color(r, g, b, a);
		buffer.xyz(matrix4f,0.5f+displacementX, 0.0001f+displacementY, 1f+displacementZ).color(r, g, b, a);
		buffer.xyz(matrix4f,1f+displacementX, 0.0001f+displacementY, 0.5f+displacementZ).color(r, g, b, a);

		BufferRenderer.drawWithShader(buffer.endOrThrow());

		RenderSystem.disableBlend();
		matrices.pop();
	}

}

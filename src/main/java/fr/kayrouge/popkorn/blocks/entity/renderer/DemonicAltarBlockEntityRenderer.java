package fr.kayrouge.popkorn.blocks.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.kayrouge.popkorn.blocks.entity.DemonicAltarBlockEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Objects;

@ClientOnly
public class DemonicAltarBlockEntityRenderer implements BlockEntityRenderer<DemonicAltarBlockEntity> {

	public DemonicAltarBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}

	@Override
	public void render(DemonicAltarBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();

		double x = 0.5d;
		double y = 0.001d;
		double z = 0.5d;

		Identifier identifier = Identifier.of("popkorn", "textures/effects/demonic_altar.png");

		RenderSystem.setShaderTexture(0, identifier);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableDepthTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		Matrix4f matrix4f = matrices.peek().getModel();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);


		/*
		*
		* 2.5f = 2 block
		* 1.5f = 1 block
		* because of the origin is a corner of the block, so to center the image I have to translate the image 0.5f on x and z
		*
		* */
		float size = 2.5f;

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

		BufferRenderer.drawWithShader(Objects.requireNonNull(buffer.endOrThrow()));

		RenderSystem.disableBlend();
		matrices.pop();
	}

	@Override
	public int getRenderDistance() {
		return 45;
	}
}

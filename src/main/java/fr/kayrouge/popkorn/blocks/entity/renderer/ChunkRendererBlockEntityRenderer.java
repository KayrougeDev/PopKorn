package fr.kayrouge.popkorn.blocks.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.kayrouge.popkorn.blocks.entity.ChunkRendererBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ChunkRendererBlockEntityRenderer implements BlockEntityRenderer<ChunkRendererBlockEntity> {

	private final BlockEntityRendererFactory.Context context;

	public ChunkRendererBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.context = context;
	}

	@Override
	public void render(ChunkRendererBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		renderWireframe(matrices, vertexConsumers);
	}

	public void renderWireframe(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
		VertexConsumer vertex = vertexConsumers.getBuffer(RenderLayer.LINES);

		// Configurer le mode de rendu
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.lineWidth(2.0F); // Épaisseur des lignes

		// Position des sommets d'un cube (exemple simplifié)
		float[][] vertices = {
			{0, 0, 0}, {1, 0, 0}, {1, 1, 0}, {0, 1, 0}, // Face arrière
			{0, 0, 1}, {1, 0, 1}, {1, 1, 1}, {0, 1, 1}  // Face avant
		};

		// Index des arêtes du cube (par paires de sommets)
		int[][] edges = {
			{0, 1}, {1, 2}, {2, 3}, {3, 0}, // Face arrière
			{4, 5}, {5, 6}, {6, 7}, {7, 4}, // Face avant
			{0, 4}, {1, 5}, {2, 6}, {3, 7}  // Entre faces avant et arrière
		};

		// Couleur de la ligne (rouge avec transparence)
		float r = 1.0F, g = 0.0F, b = 0.0F, a = 1.0F;

		Matrix4f matrix = matrices.peek().getModel();

		for (int[] edge : edges) {
			float[] v1 = vertices[edge[0]];
			float[] v2 = vertices[edge[1]];

			float n1 = v2[0] - v1[0];
			float n2 = v2[1] - v1[1];
			float n3 = v2[2] - v1[2];

			float t = MathHelper.sqrt(n1 * n1 + n2 * n2 + n3 * n3);
			n1 /= t;
			n2 /= t;
			n3 /= t;

			vertex.xyz(matrix, v1[0], v1[1], v1[2]).color(r, g, b, a).normal(matrices.peek(), n1, n2, n3);
			vertex.xyz(matrix, v2[0], v2[1], v2[2]).color(r, g, b, a).normal(matrices.peek(), n1, n2, n3);
		}



		RenderSystem.disableBlend();
	}
}

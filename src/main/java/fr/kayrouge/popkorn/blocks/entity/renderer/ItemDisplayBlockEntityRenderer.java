package fr.kayrouge.popkorn.blocks.entity.renderer;

import fr.kayrouge.popkorn.blocks.ItemDisplayBlock;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import fr.kayrouge.popkorn.blocks.entity.ItemDisplayBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Axis;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.awt.*;

@ClientOnly
public class ItemDisplayBlockEntityRenderer implements BlockEntityRenderer<ItemDisplayBlockEntity> {

	private final BlockEntityRendererFactory.Context context;

	public ItemDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.context = context;
	}

	@Override
	public void render(ItemDisplayBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if(blockEntity.getWorld().getBlockState(blockEntity.getPos()) == null || blockEntity.getWorld().getBlockState(blockEntity.getPos()).getBlock() != PKBlocks.ITEM_DISPLAY) return;
		if(blockEntity.getInventory().isEmpty() || !blockEntity.getWorld().getBlockState(blockEntity.getPos()).get(ItemDisplayBlock.DISPLAY_ITEM)) return;
		matrices.push();

		double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 6.0;
		// Move the item
		matrices.translate(0.5, 1.25f + (blockEntity.upAnDown ? offset : 0f), 0.5);

		if(blockEntity.rotate) {
			matrices.rotate(Axis.Y_POSITIVE.rotationDegrees((blockEntity.getWorld().getTime() + tickDelta) * 4));
		}

		int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
		context.getItemRenderer().renderItem(blockEntity.getInventory().getStack(0), ModelTransformationMode.GROUND, lightAbove, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);


		matrices.pop();

		matrices.push();
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		matrices.translate(1f, 0.6, -0.001d);

		matrices.rotate(Axis.Z_POSITIVE.rotationDegrees(180f));

		Text itemName = blockEntity.getInventory().getStack(0).getName();

		int maxTextWidth = 16;
		int textWidth = textRenderer.getWidth(itemName);
		float scale = textWidth > maxTextWidth ? (float) maxTextWidth / textWidth : 1.0f;
		float adjustedScale = 0.0625f * scale;
		matrices.scale(adjustedScale, adjustedScale, adjustedScale);

		textRenderer.draw(itemName, 0, 0, Color.WHITE.getRGB(), false, matrices.peek().getModel(), vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 250);

		matrices.pop();
	}
}

package fr.kayrouge.popkorn.blocks.entity.renderer;

import fr.kayrouge.popkorn.blocks.ItemDisplayBlock;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import fr.kayrouge.popkorn.blocks.entity.ItemDisplayBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.Direction;
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


		float rotation = 0f;

		BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());

		if(state.contains(Properties.FACING)) {
			if(state.get(Properties.FACING) == Direction.WEST || state.get(Properties.FACING) == Direction.EAST) {
				rotation = state.get(Properties.FACING).asRotation() + 180f;
			}
			else {
				rotation = state.get(Properties.FACING).asRotation();
			}
		}

		matrices.push();

		double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 6.0;

		matrices.translate(0.5, 1.25f + (blockEntity.upAnDown ? offset : 0f), 0.5);

		matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(rotation));

		if(blockEntity.rotate) {
			matrices.rotate(Axis.Y_POSITIVE.rotationDegrees((blockEntity.getWorld().getTime() + tickDelta) * 4));
		}

		int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
		context.getItemRenderer().renderItem(blockEntity.getInventory().getStack(0), ModelTransformationMode.GROUND, lightAbove, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);


		matrices.pop();

		matrices.push();
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

		rotation = 0f;
		double offsetX = 0f;
		double offsetZ = 0f;
		final double defaultOffset = 0.001d;

		if(state.contains(Properties.FACING)) {

			rotation = state.get(Properties.FACING).asRotation();
			offsetX = state.get(Properties.FACING).getOffsetX();
			offsetZ = state.get(Properties.FACING).getOffsetZ();

			if(state.get(Properties.FACING) == Direction.EAST) {
				offsetZ += 1;
				offsetX += defaultOffset;
			}
			else if(state.get(Properties.FACING) == Direction.WEST) {
				offsetX += 1-defaultOffset;
			}
			else {
				rotation = state.get(Properties.FACING).asRotation()+180f;
				if(state.get(Properties.FACING) == Direction.SOUTH) {
					offsetZ += defaultOffset;
				}
				else {
					offsetZ += 1 - defaultOffset;
					offsetX += 1;
				}
			}
		}

		matrices.translate(offsetX, 0.6, offsetZ);

		matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(rotation));
		matrices.rotate(Axis.Z_POSITIVE.rotationDegrees(180F));

		Text itemName = blockEntity.getInventory().getStack(0).getName();

		int maxTextWidth = 16;
		int textWidth = textRenderer.getWidth(itemName);
		float scale = (float) maxTextWidth / textWidth;
		float adjustedScale = 0.0625f * scale;
		matrices.scale(adjustedScale, adjustedScale, adjustedScale);

		textRenderer.draw(itemName, 0, 0, Color.WHITE.getRGB(), false, matrices.peek().getModel(), vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 250);

		matrices.pop();
	}

	@Override
	public int getRenderDistance() {
		return 45;
	}
}

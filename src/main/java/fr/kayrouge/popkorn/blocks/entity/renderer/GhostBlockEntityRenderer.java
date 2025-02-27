package fr.kayrouge.popkorn.blocks.entity.renderer;

import fr.kayrouge.popkorn.blocks.entity.GhostBlockEntity;
import fr.kayrouge.popkorn.registry.PKBlocks;
import fr.kayrouge.popkorn.registry.PKItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.biome.BiomeColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.light.LightingProvider;
import org.jetbrains.annotations.Nullable;

public class GhostBlockEntityRenderer implements BlockEntityRenderer<GhostBlockEntity> {


	private final BlockEntityRendererFactory.Context context;

	public GhostBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.context = ctx;
	}

	@Override
	public void render(GhostBlockEntity ghostBlockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		GhostBlockRenderView blockRenderView = new GhostBlockRenderView(ghostBlockEntity.getWorld(), light);

		Block blockToDisplay = ghostBlockEntity.getBlockToDisplay();

		BlockState blockStateToDisplay;
		assert MinecraftClient.getInstance().player != null;
		if(MinecraftClient.getInstance().player.getInventory().getArmorStack(3).getItem() == PKItems.LIGHT_GLASSES
			|| blockToDisplay.getDefaultState().getRenderType() == BlockRenderType.INVISIBLE) {
			blockToDisplay = PKBlocks.GHOST_BLOCK;
		}

		blockStateToDisplay = blockToDisplay.getDefaultState();

		if(blockStateToDisplay.getProperties().contains(Properties.FACING)) {
			blockStateToDisplay = blockStateToDisplay.with(Properties.FACING, ghostBlockEntity.getCachedState().get(Properties.FACING));
		}

		if(blockStateToDisplay.getProperties().contains(Properties.AXIS)) {
			blockStateToDisplay = blockStateToDisplay.with(Properties.AXIS, ghostBlockEntity.getCachedState().get(Properties.FACING).getAxis());
		}

		if (blockStateToDisplay.getRenderType() != BlockRenderType.INVISIBLE) {
			this.context.getRenderManager().renderBlock(blockStateToDisplay, ghostBlockEntity.getPos(),
				blockRenderView, matrices, vertexConsumers.getBuffer(RenderLayer.getBlockLayers().getLast()), true, RandomGenerator.createLegacy());
		}

		if(blockToDisplay instanceof BlockWithEntity blockWithEntity) {
			BlockEntity blockEntityToDisplay = blockWithEntity.createBlockEntity(ghostBlockEntity.getPos(), blockStateToDisplay);
			blockEntityToDisplay.setWorld(ghostBlockEntity.getWorld());
			this.context.getRenderDispatcher().get(blockEntityToDisplay).render(blockEntityToDisplay, tickDelta, matrices, vertexConsumers, light, overlay);
		}
	}

	private record GhostBlockRenderView(World world, float brightness) implements BlockRenderView {

		@Override
		public float getBrightness(Direction direction, boolean shaded) {
			return world().getBrightness(direction, shaded);
		}

		@Override
		public LightingProvider getLightingProvider() {
			return world().getLightingProvider();
		}

		@Override
		public int getColor(BlockPos pos, BiomeColorProvider biomeColorProvider) {
			return world().getColor(pos, biomeColorProvider);
		}

		@Override
		public @Nullable BlockEntity getBlockEntity(BlockPos pos) {
			return world().getBlockEntity(pos);
		}

		@Override
		public BlockState getBlockState(BlockPos pos) {
			return world().getBlockState(pos);
		}

		@Override
		public FluidState getFluidState(BlockPos pos) {
			return world.getFluidState(pos);
		}

		@Override
		public int getHeight() {
			return 0;
		}

		@Override
		public int getBottomY() {
			return 0;
		}
	}
}

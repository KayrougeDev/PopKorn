package fr.kayrouge.popkorn.blocks.entity.renderer;

import fr.kayrouge.popkorn.blocks.entity.GhostBlockEntity;
import fr.kayrouge.popkorn.registry.PKBlocks;
import fr.kayrouge.popkorn.registry.PKItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
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
	public void render(GhostBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		GhostBlockRenderView blockRenderView = new GhostBlockRenderView(blockEntity.getWorld(), light);

		Block block = blockEntity.getBlockToDisplay();
		BlockState blockState;
		assert MinecraftClient.getInstance().player != null;
		if(MinecraftClient.getInstance().player.getInventory().getArmorStack(3).getItem() == PKItems.LIGHT_GLASSES
			|| block.getDefaultState().getRenderType() == BlockRenderType.INVISIBLE) {
			blockState = PKBlocks.GHOST_BLOCK.getDefaultState();
		}
		else {
			blockState = block.getDefaultState();
			if(blockState.getProperties().contains(Properties.AXIS)) {
				blockState = blockState.with(Properties.AXIS, blockEntity.getCachedState().get(Properties.FACING).getAxis());
			}

			if(blockState.getProperties().contains(Properties.FACING)) {
				blockState = blockState.with(Properties.FACING, blockEntity.getCachedState().get(Properties.FACING));
			}
		}

		MinecraftClient.getInstance().getBlockRenderManager().renderBlock(blockState, blockEntity.getPos(),
			blockRenderView, matrices, vertexConsumers.getBuffer(RenderLayer.getBlockLayers().getLast()), true, RandomGenerator.createLegacy());
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

package fr.kayrouge.popkorn.mixin.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@Shadow
	@Final
	private BufferBuilderStorage bufferBuilders;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	@Nullable
	private ClientWorld world;

	@Inject(method = "method_3251", at = @At(
		value = "HEAD"
	), cancellable = true)
	public void render(RenderLayer renderLayer, double d, double e, double f, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {

		BlockPos pos = new BlockPos((int) d, (int) e, (int) f);

		BlockState blockState = this.world.getBlockState(pos);

		if (!blockState.isAir() && this.world.getWorldBorder().contains(pos)) {
			VertexConsumerProvider.Immediate immediate = this.bufferBuilders.getEntityVertexConsumers();

			VertexConsumer consumer = immediate.getBuffer(RenderLayer.getLines());

			VoxelShape voxelShape = blockState.getOutlineShape(world, pos);

			float red = 0.0F;
			float green = 0.0F;
			float blue = 0.0F;
			float alpha = 0.8F;

			voxelShape.forEachEdge((k, l, m, n, o, p) -> {
				float q = (float)(n - k);
				float r = (float)(o - l);
				float s = (float)(p - m);
				float t = MathHelper.sqrt(q * q + r * r + s * s);
				q /= t;
				r /= t;
				s /= t;
				consumer.xyz(matrix4f, (float)(k + d), (float)(l + e), (float)(m + f)).color(red, green, blue, alpha);
				consumer.xyz(matrix4f, (float)(n + d), (float)(o + e), (float)(p + f)).color(red, green, blue, alpha);
			});

			immediate.draw(RenderLayer.getLines());
			immediate.draw();
		}



		ci.cancel();
	}
}

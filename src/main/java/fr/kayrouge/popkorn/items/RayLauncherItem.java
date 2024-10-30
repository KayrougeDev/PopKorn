package fr.kayrouge.popkorn.items;

import fr.kayrouge.popkorn.debug.DebugItem;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class RayLauncherItem extends DebugItem {

	public RayLauncherItem() {
		super(new Settings().maxCount(1).fireproof());
	}

	@Override
	protected TypedActionResult<ItemStack> useImpl(World world, PlayerEntity user, Hand hand) {

		if (!world.isClient) {
			double range = 50.0D;

			Vec3d startPos = user.getEyePos();

			Vec3d lookVector = user.getRotationVector().multiply(range);

			Vec3d endPos = startPos.add(lookVector);


			BlockHitResult hitResult = world.raycast(new RaycastContext(
				startPos,
				endPos,
				RaycastContext.ShapeType.COLLIDER,
				RaycastContext.FluidHandling.NONE,
				ShapeContext.of(user)
			));

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				drawParticleLine(MinecraftClient.getInstance().world, startPos, hitResult.getPos());
				world.setBlockState(hitResult.getBlockPos(), Blocks.COBBLESTONE.getDefaultState());

				return TypedActionResult.success(user.getStackInHand(hand), true);
			}
		}

		return TypedActionResult.fail(user.getStackInHand(hand));
	}

	private void drawParticleLine(ClientWorld world, Vec3d start, Vec3d end) {
		Vec3d direction = end.subtract(start);
		double distance = direction.length();
		Vec3d normalizedDirection = direction.normalize();

		for (double d = 0; d < distance; d += 0.1) {
			Vec3d particlePos = start.add(normalizedDirection.multiply(d));
			world.addParticle(ParticleTypes.END_ROD,
				particlePos.x, particlePos.y, particlePos.z,
				0, 0, 0);
		}
	}
}

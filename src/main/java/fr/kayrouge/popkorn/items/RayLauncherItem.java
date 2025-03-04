package fr.kayrouge.popkorn.items;

import fr.kayrouge.popkorn.network.packet.s2c.RayLauncherUseS2CPayload;
import fr.kayrouge.popkorn.util.PlayerUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class RayLauncherItem extends Item {

	public RayLauncherItem() {
		super(new Settings().maxDamage(27));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
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
				//drawParticleLine(MinecraftClient.getInstance().world, startPos, hitResult.getPos());


				PlayerUtil.getPlayerInRange(world, user, 20f).forEach(player -> {
					if(player instanceof  ServerPlayerEntity) {
						ServerPlayNetworking.send((ServerPlayerEntity)player, new RayLauncherUseS2CPayload(startPos.toVector3f(), hitResult.getPos().toVector3f()));
					}
				});

				world.setBlockState(hitResult.getBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
				user.getStackInHand(hand).damageEquipment(1, user, LivingEntity.getHand(hand));

				return TypedActionResult.success(user.getStackInHand(hand), true);
			}
		}

		return TypedActionResult.fail(user.getStackInHand(hand));
	}
}

package fr.kayrouge.popkorn.abilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ChainsawAbility extends Ability{
	public ChainsawAbility(int maxCharges, int cooldownTime, boolean needConfirmation) {
		super(maxCharges, cooldownTime, needConfirmation);
	}

	@Override
	public boolean use(ServerPlayerEntity player) {
		EntityHitResult hitResult = entityRayCast(player.getCameraEntity(), 11D, player.getRotationVec(1.F), 1.F);
		if(hitResult == null || !(hitResult.getEntity() instanceof LivingEntity target)) {
			player.sendMessage(Text.translatable("abilities.chainsaw.notarget"), true);
		}
		else {
			if(target.getWorld() != player.getWorld()) {
				player.sendMessage(Text.translatable("abilities.chainsaw.interdimensionalkill"), true);
				return false;
			}
			if(target.getMaxHealth() > 25 || target instanceof PlayerEntity) {
				player.sendMessage(Text.translatable("abilities.chainsaw.targetcantbekill"), true);
			}
			else {
				double x = calculatePos(player.getX(), target.getX());
				double z = calculatePos(player.getZ(), target.getZ());


				player.teleport(player.getServerWorld(), x, target.getY(), z, player.getYaw(), player.getPitch());
				target.kill();
				return super.use(player);
			}
		}
		return false;
	}

	private double calculatePos(double playerPos, double targetPos) {
		if(targetPos > playerPos) {
			return targetPos-0.75d;
		}
		else {
			return targetPos+0.75d;
		}
	}

	public static EntityHitResult entityRayCast(Entity entity, double maxDistance, Vec3d rotationVec, float tickDelta) {
		if(entity == null) return null;
		Vec3d cameraPos = entity.getLerpedEyePos(tickDelta);
		Vec3d vec3d3 = cameraPos.add(rotationVec.multiply(maxDistance));
		double expansionFactor = 1.0D;
		Box box = entity
			.getBounds()
			.stretch(entity.getRotationVec(1.0F).multiply(maxDistance))
			.expand(expansionFactor, expansionFactor, expansionFactor);
		return ProjectileUtil.raycast(
			entity,
			cameraPos,
			vec3d3,
			box,
			(entityx) -> !entityx.isSpectator() && entityx.collides(),
			maxDistance * maxDistance
		);
	}

}

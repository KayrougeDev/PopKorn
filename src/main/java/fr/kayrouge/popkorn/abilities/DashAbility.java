package fr.kayrouge.popkorn.abilities;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class DashAbility extends Ability{

	public DashAbility(int maxCharges, int cooldownTime) {
		super(maxCharges, cooldownTime);
	}

	@Override
	public boolean use(ServerPlayerEntity player) {
		dash(player);
		return super.use(player);
	}

	public static void dash(ServerPlayerEntity player) {
		player.velocityModified = false;
		Vec3d movementVec = player.getMovement();

		boolean isMoving = movementVec.horizontalLengthSquared() > 0.001;

		Vec3d dashDirection;

		if (isMoving) {
			dashDirection = movementVec.normalize();
		} else {
			dashDirection = player.getRotationVec(1.0F);
		}

		double dashSpeed = 2.0D;

		Vec3d newVelocity = new Vec3d(
			dashDirection.x * dashSpeed,
			movementVec.y,
			dashDirection.z * dashSpeed
		);

		player.setVelocity(newVelocity);

		player.velocityModified = true;
	}
}

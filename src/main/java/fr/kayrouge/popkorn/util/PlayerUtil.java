package fr.kayrouge.popkorn.util;

import fr.kayrouge.popkorn.PopKorn;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.List;

public class PlayerUtil {


	public static List<? extends PlayerEntity> getPlayerInRange(World world, PlayerEntity source) {
		double maxDistance = 5f;
		return world.getPlayers().stream().filter(
			player -> player.getBlockPos().getSquaredDistance(
				source.getX(),
				source.getY(),
				source.getZ()) < (maxDistance * maxDistance)).toList();
	}

	// TODO Hide armor and held item
	public static void totallyDisappear(LivingEntity entity, boolean b) {
		entity.setInvisible(b);
	}

	private static boolean shouldDisplayInfo = true;

	public static void displayInfo(String info) {
		displayInfo(info, new Object());
	}

	public static void displayInfo(String info, Object... argument) {
		if(shouldDisplayInfo) {
			PopKorn.LOGGER.info(info, argument);
		}
	}

	public static void startDisplayInfo() {
		shouldDisplayInfo = true;
	}

	public static void endDisplayInfo() {
		shouldDisplayInfo = false;
	}
}

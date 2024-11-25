package fr.kayrouge.popkorn.util;

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
}

package fr.kayrouge.popkorn.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ScreenUtil {

	public static boolean canPlayerUseButton(PlayerEntity player, BlockPos pos, double additionalRange) {
		return !player.isSpectator() && player.canInteractWithBlockAt(pos, additionalRange);
	}

}

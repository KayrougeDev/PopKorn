package fr.kayrouge.popkorn.util;

import fr.kayrouge.popkorn.PopKorn;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class PlayerUtil {

	public static final UUID DEFAULT_UUID = new UUID(0, 0);


	public static List<? extends PlayerEntity> getPlayerInRange(World world, PlayerEntity source, double maxDistance) {
		return world.getPlayers().stream().filter(
			player -> player.getBlockPos().getSquaredDistance(
				source.getX(),
				source.getY(),
				source.getZ()) < (maxDistance * maxDistance)).toList();
	}

	public static void setMaxHealth(PlayerEntity player, double value) {
		EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (healthAttribute != null) {
			Identifier modifierID = Identifier.of(PopKorn.MODID, "custom_max_health");
			healthAttribute.removeModifier(modifierID);

			healthAttribute.addPersistentModifier(new EntityAttributeModifier(
				modifierID, value - healthAttribute.getBaseValue(), EntityAttributeModifier.Operation.ADD_VALUE
			));

			if (player.getHealth() > value) {
				player.setHealth((float) value);
			}
		}
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

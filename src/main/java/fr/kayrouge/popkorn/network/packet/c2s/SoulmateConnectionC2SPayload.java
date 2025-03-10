package fr.kayrouge.popkorn.network.packet.c2s;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.component.PlayerDataComponent;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import fr.kayrouge.popkorn.registry.PKComponents;
import fr.kayrouge.popkorn.util.PlayerUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public record SoulmateConnectionC2SPayload() implements CustomPayload {

	public static final Id<SoulmateConnectionC2SPayload> ID = new Id<>(PKNetworkingConstants.SOULMATE_CONNECTION_PACKET_ID);

	public static final PacketCodec<RegistryByteBuf, SoulmateConnectionC2SPayload> CODEC = PacketCodec.unit(new SoulmateConnectionC2SPayload());

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public void receive(ServerPlayNetworking.Context context) {
		context.server().execute(() -> {
			ServerPlayerEntity player = context.player();
			PlayerDataComponent playerData = PKComponents.PLAYER_DATA.get(player);
			ServerWorld world = player.getServerWorld();
			if(playerData.getSoulmateId().equals(PlayerUtil.DEFAULT_UUID)) {
				PopKorn.LOGGER.warn("TRY CONNECT WITH DEFAULT UUID");
				return;
			}
			if(playerData.getSoulmateTime() == 0L) {
				PopKorn.LOGGER.warn("TRY CONNECT WITH 0 TICK TIME");
				return;
			}
			if(PopKorn.DEBUG) {
				PopKorn.LOGGER.info("COMP {}", PKComponents.PLAYER_DATA.get(player).getSoulmateTime());
				PopKorn.LOGGER.info("WORLD {}", world.getTime());
			}
			final long maxDiff = -100;
			long timeDiff = playerData.getSoulmateTime() - world.getTime();
			// difference between the mate on server and the moment the packet was received
			// not > 5s (3s anim + 2s in case of lag)
			if(timeDiff > 0 || timeDiff < maxDiff) {
				PopKorn.LOGGER.warn("TIME DIFF TOO BIG FOR {}", player.getProfileName());
				return;
			}

			// check soulmate exists
			if(world.getEntity(playerData.getSoulmateId()) instanceof LivingEntity entity) {

				giveSoulMateModifier(player, entity);

				entity.setCustomName(Text.translatable("soulmate.entity.name", player.getProfileName())
					.setColor(Color.ORANGE.getRGB()));
				entity.setCustomNameVisible(true);

				PKComponents.ENTITY_DATA.get(entity).setPlayerMate(player.getUuid());

				playerData.setSoulmateId(playerData.getSoulmateId(), 0L);
				PKComponents.PLAYER_DATA.sync(player);
			}

		});
	}

	public static void removeSoulMateModifier(ServerPlayerEntity player) {
		EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (healthAttribute != null) {
			healthAttribute.removeModifier(Identifier.of(PopKorn.MODID, "soulmate_health"));
		}
		EntityAttributeInstance sneakSpeedAttribute = player.getAttributeInstance(EntityAttributes.PLAYER_SNEAKING_SPEED);
		if (sneakSpeedAttribute != null) {
			sneakSpeedAttribute.removeModifier(Identifier.of(PopKorn.MODID, "soulmate_sneak_speed"));
		}
	}

	public static void setPlayerHeartBroken(ServerPlayerEntity player) {
		removeSoulMateModifier(player);
		PKComponents.PLAYER_DATA.get(player).setSoulmateId(PlayerUtil.DEFAULT_UUID, 0);
		PKComponents.PLAYER_DATA.get(player).setHeartbroken(true);
		PKComponents.PLAYER_DATA.sync(player);
		giveHeartBrokenModifier(player);
	}

	public static void giveHeartBrokenModifier(ServerPlayerEntity player) {
		EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if(healthAttribute != null) {
			Identifier modifierID = Identifier.of(PopKorn.MODID, "heartbroken_health");
			healthAttribute.removeModifier(modifierID);
			healthAttribute.addPersistentModifier(new EntityAttributeModifier(
				modifierID, -3d, EntityAttributeModifier.Operation.ADD_VALUE
			));
		}
		EntityAttributeInstance scaleAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_SCALE);
		if(scaleAttribute != null) {
			Identifier modifierID = Identifier.of(PopKorn.MODID, "heartbroken_scale");
			scaleAttribute.removeModifier(modifierID);
			scaleAttribute.addPersistentModifier(new EntityAttributeModifier(
				modifierID, -0.1f, EntityAttributeModifier.Operation.ADD_VALUE
			));
		}
	}

	public static void giveSoulMateModifier(ServerPlayerEntity player, LivingEntity entity) {
		double mateMaxHealth = entity.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH);
		EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (healthAttribute != null) {
			Identifier modifierID = Identifier.of(PopKorn.MODID, "soulmate_health");
			healthAttribute.removeModifier(modifierID);

			healthAttribute.addPersistentModifier(new EntityAttributeModifier(
				modifierID, (mateMaxHealth/4)*3, EntityAttributeModifier.Operation.ADD_VALUE
			));
		}
		EntityAttributeInstance sneakSpeedAttribute = player.getAttributeInstance(EntityAttributes.PLAYER_SNEAKING_SPEED);
		if (sneakSpeedAttribute != null) {
			Identifier modifierID = Identifier.of(PopKorn.MODID, "soulmate_sneak_speed");
			sneakSpeedAttribute.removeModifier(modifierID);

			sneakSpeedAttribute.addPersistentModifier(new EntityAttributeModifier(
				modifierID, 0.3f, EntityAttributeModifier.Operation.ADD_VALUE
			));
		}
	}
}

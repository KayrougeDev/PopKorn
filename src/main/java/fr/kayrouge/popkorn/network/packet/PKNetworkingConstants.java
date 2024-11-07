package fr.kayrouge.popkorn.network.packet;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.network.packet.c2s.AbilitiesUseC2SPayload;
import fr.kayrouge.popkorn.network.packet.c2s.UpdateItemDisplayC2SPayload;
import fr.kayrouge.popkorn.network.packet.s2c.AbilitiesUseConfirmationS2CPayload;
import fr.kayrouge.popkorn.network.packet.s2c.PlayerAbilitiesUpdateS2CPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class PKNetworkingConstants {

	public static final Identifier ABILITIES_USE_PACKET_ID = Identifier.of(PopKorn.MODID, "abilities_use");
	public static final Identifier UPDATE_DISPLAY_ITEM_PACKET_ID = Identifier.of(PopKorn.MODID, "update_display_item");

	public static final Identifier ABILITIES_UPDATE_PACKET_ID = Identifier.of(PopKorn.MODID, "abilities_update");
	public static final Identifier ABILITIES_USE_CONFIRMATION_PACKET_ID = Identifier.of(PopKorn.MODID, "abilities_update_confirmation");

	public static void registerC2S() {
		PayloadTypeRegistry.playC2S().register(AbilitiesUseC2SPayload.ID, AbilitiesUseC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(UpdateItemDisplayC2SPayload.ID, UpdateItemDisplayC2SPayload.CODEC);
	}

	public static void registerS2C() {
		PayloadTypeRegistry.playS2C().register(PlayerAbilitiesUpdateS2CPayload.ID, PlayerAbilitiesUpdateS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AbilitiesUseConfirmationS2CPayload.ID, AbilitiesUseConfirmationS2CPayload.CODEC);
	}


	public static void registerC2SGlobalReceiver() {
		ServerPlayNetworking.registerGlobalReceiver(AbilitiesUseC2SPayload.ID, AbilitiesUseC2SPayload::receive);
		ServerPlayNetworking.registerGlobalReceiver(UpdateItemDisplayC2SPayload.ID, UpdateItemDisplayC2SPayload::receive);
	}

	public static void registerS2CGlobalReceiver() {
		ClientPlayNetworking.registerGlobalReceiver(PlayerAbilitiesUpdateS2CPayload.ID, PlayerAbilitiesUpdateS2CPayload::receive);
		ClientPlayNetworking.registerGlobalReceiver(AbilitiesUseConfirmationS2CPayload.ID, AbilitiesUseConfirmationS2CPayload::receive);
	}


}

package fr.kayrouge.popkorn.network.packet;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.network.packet.c2s.AbilitiesUseC2SPayload;
import fr.kayrouge.popkorn.network.packet.c2s.SoulmateConnectionC2SPayload;
import fr.kayrouge.popkorn.network.packet.c2s.UpdateItemDisplayC2SPayload;
import fr.kayrouge.popkorn.network.packet.s2c.AbilitiesUseS2CPayload;
import fr.kayrouge.popkorn.network.packet.s2c.PlayerAbilitiesUpdateS2CPayload;
import fr.kayrouge.popkorn.network.packet.s2c.RayLauncherUseS2CPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class PKNetworkingConstants {

	//C2S
	public static final Identifier ABILITIES_USE_PACKET_ID = Identifier.of(PopKorn.MODID, "abilities_use");
	public static final Identifier SOULMATE_CONNECTION_PACKET_ID = Identifier.of(PopKorn.MODID, "soulmate_connection");
	public static final Identifier UPDATE_DISPLAY_ITEM_PACKET_ID = Identifier.of(PopKorn.MODID, "update_display_item");

	//S2C
	public static final Identifier ABILITIES_UPDATE_PACKET_ID = Identifier.of(PopKorn.MODID, "abilities_update");
	public static final Identifier ABILITIES_USE_CONFIRMATION_PACKET_ID = Identifier.of(PopKorn.MODID, "abilities_update_confirmation");
	public static final Identifier RAY_LAUNCHER_USE_PACKET_ID = Identifier.of(PopKorn.MODID, "ray_launcher_use");

	public static void registerC2S() {
		PayloadTypeRegistry.playC2S().register(AbilitiesUseC2SPayload.ID, AbilitiesUseC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(SoulmateConnectionC2SPayload.ID, SoulmateConnectionC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(UpdateItemDisplayC2SPayload.ID, UpdateItemDisplayC2SPayload.CODEC);
	}

	public static void registerS2C() {
		PayloadTypeRegistry.playS2C().register(PlayerAbilitiesUpdateS2CPayload.ID, PlayerAbilitiesUpdateS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(AbilitiesUseS2CPayload.ID, AbilitiesUseS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(RayLauncherUseS2CPayload.ID, RayLauncherUseS2CPayload.CODEC);
	}


	public static void registerC2SGlobalReceiver() {
		ServerPlayNetworking.registerGlobalReceiver(AbilitiesUseC2SPayload.ID, AbilitiesUseC2SPayload::receive);
		ServerPlayNetworking.registerGlobalReceiver(SoulmateConnectionC2SPayload.ID, SoulmateConnectionC2SPayload::receive);
		ServerPlayNetworking.registerGlobalReceiver(UpdateItemDisplayC2SPayload.ID, UpdateItemDisplayC2SPayload::receive);
	}

	public static void registerS2CGlobalReceiver() {
		ClientPlayNetworking.registerGlobalReceiver(PlayerAbilitiesUpdateS2CPayload.ID, PlayerAbilitiesUpdateS2CPayload::receive);
		ClientPlayNetworking.registerGlobalReceiver(AbilitiesUseS2CPayload.ID, AbilitiesUseS2CPayload::receive);
		ClientPlayNetworking.registerGlobalReceiver(RayLauncherUseS2CPayload.ID, RayLauncherUseS2CPayload::receive);
	}


}

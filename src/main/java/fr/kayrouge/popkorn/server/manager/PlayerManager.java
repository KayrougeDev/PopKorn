package fr.kayrouge.popkorn.server.manager;

import fr.kayrouge.popkorn.network.packet.s2c.PlayerAbilitiesUpdateS2CPayload;
import fr.kayrouge.popkorn.abilities.Ability;
import fr.kayrouge.popkorn.abilities.ChainsawAbility;
import fr.kayrouge.popkorn.abilities.DashAbility;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

	private static final Map<ServerPlayerEntity,
		Map<String, Ability>> playerAbilities  = new HashMap<>();

	public static void initPlayerAbilities(ServerPlayerEntity player) {
		Map<String, Ability> abilities = new HashMap<>();

		abilities.put("dash", new DashAbility(2, 100, false));
		abilities.put("chainsaw", new ChainsawAbility(1, 200, true));
		abilities.put("test", new Ability(2, 60, false));

		playerAbilities.put(player, abilities);
	}

	public static void tick(ServerPlayerEntity player) {
		Map<String, Ability> abilities = playerAbilities.get(player);
		if (abilities != null) {
			for (Ability ability : abilities.values()) {
				ability.tick(player);
			}
		}
	}

	public static Map<String, Ability> getAbilities(ServerPlayerEntity player) {
		if(!playerAbilities.containsKey(player)) initPlayerAbilities(player);
		return playerAbilities.get(player);
	}

	public static void updateClient(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new PlayerAbilitiesUpdateS2CPayload(getAbilities(player)));
	}
}

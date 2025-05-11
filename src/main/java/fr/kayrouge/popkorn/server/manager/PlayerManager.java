package fr.kayrouge.popkorn.server.manager;

import fr.kayrouge.popkorn.abilities.ChainsawAbility;
import fr.kayrouge.popkorn.abilities.DashAbility;
import fr.kayrouge.popkorn.abilities.TPBackAbility;
import fr.kayrouge.popkorn.network.packet.s2c.PlayerAbilitiesUpdateS2CPayload;
import fr.kayrouge.popkorn.abilities.Ability;
import fr.kayrouge.popkorn.util.TimeUtil;
import fr.kayrouge.popkorn.util.configs.PopKornServerConfig;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

	private static final Map<ServerPlayerEntity,
		Map<String, Ability>> playerAbilities  = new HashMap<>();

	public static void initPlayerAbilities(ServerPlayerEntity player) {
		Map<String, Ability> abilities = new HashMap<>();
		if(PopKornServerConfig.INSTANCE.disableAbilities.value()) {
			abilities.put("disabled", Ability.USELESS);
		} else {
			abilities.put("dash", new DashAbility(2, TimeUtil.secondToTick(5)));
			abilities.put("chainsaw", new ChainsawAbility(1, TimeUtil.secondToTick(10)));
			abilities.put("test", new Ability(2, TimeUtil.secondToTick(3)));
			abilities.put("tpback", new TPBackAbility(1, TimeUtil.minuteToTick(1, 15)));
		}

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

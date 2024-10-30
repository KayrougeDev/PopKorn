package fr.kayrouge.popkorn.client.manager;

import fr.kayrouge.popkorn.client.PopKornClient;
import fr.kayrouge.popkorn.abilities.Ability;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public class ClientPlayerManager {

	private static final Map<String, Ability> ABILITY_MAP  = new HashMap<>();


	public static void setAbilities(Map<String, Ability> abilities) {
		ABILITY_MAP.clear();
		ABILITY_MAP.putAll(abilities);
	}

	public static Ability getAbility(String name) {
		if(ABILITY_MAP.containsKey(name)) {
			return ABILITY_MAP.get(name);
		}
		else {
			if(!ABILITY_MAP.isEmpty()) {
				PopKornClient.LOGGER.warn("UNKNOW abilities '{}' used ! Return a placeholder one", name);
			}
			return new Ability(0, 0, false);
		}
	}

	public static boolean areAbilitiesAvailable() {
		return !ABILITY_MAP.isEmpty();
	}

	public static void clearAbilities() {
		ABILITY_MAP.clear();
	}
}

package fr.kayrouge.popkorn.client.manager;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.PopKornClient;
import fr.kayrouge.popkorn.abilities.Ability;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public class ClientPlayerManager {

	private static ClientPlayerManager INSTANCE = new ClientPlayerManager();

	private final Map<String, Ability> ABILITY_MAP  = new HashMap<>();

	public void setAbilities(Map<String, Ability> abilities) {
		ABILITY_MAP.clear();
		ABILITY_MAP.putAll(abilities);
	}

	public Ability getAbility(String name) {
		if(ABILITY_MAP.containsKey(name)) {
			return ABILITY_MAP.get(name);
		}
		else {
			if(!ABILITY_MAP.isEmpty() && PopKorn.DEBUG) {
				PopKornClient.LOGGER.warn("UNKNOW abilities '{}' used ! Return a placeholder one", name);
			}
			return Ability.USELESS;
		}
	}

	public boolean areAbilitiesAvailable() {
		return getAbilitiesState().isAvailable;
	}

	public AbilitiesState getAbilitiesState() {
		if(ABILITY_MAP.isEmpty()) {
			return AbilitiesState.CANTBELOADED;
		} else if (ABILITY_MAP.size() == 1 && ABILITY_MAP.containsKey("disabled")) {
			return AbilitiesState.DISABLED;
		} else {
			return AbilitiesState.NORMAL;
		}
	}

	public static ClientPlayerManager getInstance() {
		return INSTANCE;
	}

	public static ClientPlayerManager reset() {
		INSTANCE = new ClientPlayerManager();
		return INSTANCE;
	}

	public enum AbilitiesState {

		NORMAL(true),
		DISABLED(false),
		CANTBELOADED(false);

		boolean isAvailable;

		AbilitiesState(boolean available) {
			this.isAvailable = available;
		}

		public boolean isAvailable() {
			return isAvailable;
		}
	}
}

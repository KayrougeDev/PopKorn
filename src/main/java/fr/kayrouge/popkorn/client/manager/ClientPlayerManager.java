package fr.kayrouge.popkorn.client.manager;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.client.PopKornClient;
import fr.kayrouge.popkorn.abilities.Ability;
import fr.kayrouge.popkorn.network.packet.c2s.AbilitiesUseC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public class ClientPlayerManager {

	private static ClientPlayerManager INSTANCE = new ClientPlayerManager();
	private final Map<String, Ability> ABILITY_MAP  = new HashMap<>();

	private BlockPos tpBackPos = new BlockPos(0, 0, 0);

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

	public static void useAbility(String ability) {
		ClientPlayNetworking.send(new AbilitiesUseC2SPayload(ability));
	}

	public BlockPos getTpBackPos() {
		return tpBackPos;
	}

	public void useTpBackAbility(BlockPos pos) {
		useAbility("tpback");
		tpBackPos = pos;
	}

	public enum AbilitiesState {

		NORMAL(true),
		DISABLED(false),
		CANTBELOADED(false);

		final boolean isAvailable;

		AbilitiesState(boolean available) {
			this.isAvailable = available;
		}

		public boolean isAvailable() {
			return isAvailable;
		}
	}
}

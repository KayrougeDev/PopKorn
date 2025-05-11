package fr.kayrouge.popkorn.abilities;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class TwoUseAbility extends Ability {

	public TwoUseAbility(int maxCharges, int cooldownTime) {
		super(maxCharges, cooldownTime);
	}

	public TwoUseAbility(int maxCharges, int cooldownTime, boolean syncIsUsed) {
		super(maxCharges, cooldownTime, syncIsUsed);
	}

	@Override
	public boolean use(ServerPlayerEntity player) {
		if(isFirstUse()) {
			firstUse(player);
			return true;
		}
		secondUse(player);
		return super.use(player);
	}

	public void firstUse(ServerPlayerEntity player) {
	}

	public void secondUse(ServerPlayerEntity player) {
	}

	public abstract boolean isFirstUse();
}

package fr.kayrouge.popkorn.abilities;

import fr.kayrouge.popkorn.server.manager.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Ability {

	private final int maxCharges;
	private int charges;
	private int cooldown;
	private final int cooldownTime;
	private boolean isUsed = false;
	private final boolean needConfirmation;

	public static final Ability USELESS = new Ability(0, 0, false);

	public Ability(int maxCharges, int cooldownTime, boolean needConfirmation) {
		this.maxCharges = maxCharges;
		this.charges = maxCharges;
		this.cooldownTime = cooldownTime;
		this.cooldown = 0;
		this.needConfirmation = needConfirmation;
	}

	public boolean isAvailable() {
		return charges > 0;
	}

	public boolean use(ServerPlayerEntity player) {
		if (charges > 0) {
			charges--;
			cooldown = cooldownTime;
		}
		return true;
	}

	public void cantUse(ServerPlayerEntity player) {
		player.sendMessage(Text.translatable("abilities.cantuse"), true);
	}

	public void tick(ServerPlayerEntity player) {
		if (cooldown > 0) {
			cooldown--;
			if (cooldown == 0) {
				charges = maxCharges;
				PlayerManager.updateClient(player);
			}
		}
	}

	public void setCharges(int charges) {
		this.charges = Math.min(charges, maxCharges);
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public int getMaxCharges() {
		return maxCharges;
	}

	public int getCooldownTime() {
		return cooldownTime;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getRemainingCharges() {
		return charges;
	}

	public void setUsed(boolean used) {
		this.isUsed = used;
	}

	public boolean isUsed() {
		return this.isUsed;
	}

	public boolean needConfirmation() {
		return this.needConfirmation;
	}
}

package fr.kayrouge.popkorn.component;

import fr.kayrouge.popkorn.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.HolderLookup;
import org.ladysnake.cca.api.v3.component.Component;

import java.util.UUID;

public class EntityDataComponent implements Component {

	private UUID playerMate = PlayerUtil.DEFAULT_UUID;
	private final Entity entity;

	public EntityDataComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag, HolderLookup.Provider registryLookup) {
		if (tag.contains("playerMate")) {
			this.playerMate = tag.getUuid("playerMate");
		} else {
			this.playerMate = PlayerUtil.DEFAULT_UUID;
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag, HolderLookup.Provider registryLookup) {
		if (!playerMate.equals(PlayerUtil.DEFAULT_UUID)) {
			tag.putUuid("playerMate", this.playerMate);
		}
	}

	public UUID getPlayerMate() {
		return playerMate;
	}

	public void setPlayerMate(UUID playerMate) {
		this.playerMate = playerMate;
	}
}

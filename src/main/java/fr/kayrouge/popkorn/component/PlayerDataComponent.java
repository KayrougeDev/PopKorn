package fr.kayrouge.popkorn.component;

import fr.kayrouge.popkorn.client.renderer.PKRenderers;
import fr.kayrouge.popkorn.client.renderer.WorldCustomRenderer;
import fr.kayrouge.popkorn.items.SoulRayItem;
import fr.kayrouge.popkorn.network.packet.c2s.SoulmateConnectionC2SPayload;
import fr.kayrouge.popkorn.registry.PKItems;
import fr.kayrouge.popkorn.util.PlayerUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.HolderLookup;
import net.minecraft.server.network.ServerPlayerEntity;
import org.joml.Vector3f;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.util.CheckEnvironment;

import java.awt.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerDataComponent implements AutoSyncedComponent {

	private UUID soulmateId = PlayerUtil.DEFAULT_UUID;
	private long soulmateTime = 0L;
	private boolean heartbroken = false;

	private final PlayerEntity player;


	public PlayerDataComponent(PlayerEntity player) {
		this.player = player;
	}

	public UUID getSoulmateId() {
		return soulmateId;
	}



	public void setSoulmateId(UUID soulmateId, long soulmateTime) {
		this.soulmateId = soulmateId;
		this.soulmateTime = soulmateTime;
	}

	@Override
	public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient) {
		buf.writeUuid(this.soulmateId);
		buf.writeBoolean(this.heartbroken);
		buf.writeLong(this.soulmateTime);
	}

	@CheckEnvironment(EnvType.CLIENT)
	@Override
	public void applySyncPacket(RegistryByteBuf buf) {
		this.soulmateId = buf.readUuid();
		this.heartbroken = buf.readBoolean();
		this.soulmateTime = buf.readLong();

		renderSoulmateConnection();
	}

	private void renderSoulmateConnection() {
		if(this.isHeartbroken() || this.soulmateTime == 0L || this.soulmateId.equals(PlayerUtil.DEFAULT_UUID)) return;

		AtomicReference<LivingEntity> targetEntity = new AtomicReference<>();
		if(this.player.getWorld() instanceof ClientWorld world) {
			for(Entity entity : world.getEntities()) {
				if(entity.getUuid().equals(this.soulmateId) && entity instanceof LivingEntity le) {
					targetEntity.set(le);
					break;
				}
			}
		}

		if(targetEntity.get() == null) return;

		PKRenderers.INSTANCE.addRenderTask((renderContext, endTimeMillis) -> {
			int remainingTime = (int)(endTimeMillis - System.currentTimeMillis());
			float progress = 1.0f - ((float) remainingTime / SoulRayItem.ANIMATION_TIME);
			Vector3f targetPos = targetEntity.get().getPos()
				.toVector3f().add(0f, targetEntity.get().getHeight() / 1.6f, 0f);
			Vector3f endPos = this.player.getPos().toVector3f().add(0, 1.3f, 0);
			endPos.lerp(targetPos, progress);

			WorldCustomRenderer.renderLineInWorld(this.player.getPos().toVector3f().add(0, 1.3f, 0), endPos,
				Color.RED, Color.RED,
				3.65d,
				renderContext.matrices(), renderContext.vertexConsumers());

			return () -> ClientPlayNetworking.send(new SoulmateConnectionC2SPayload());
		}, SoulRayItem.ANIMATION_TIME);
	}

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return player == this.player || player.getInventory().getArmorStack(3).getItem() == PKItems.LIGHT_GLASSES;
	}

	@Override
	public void readFromNbt(NbtCompound tag, HolderLookup.Provider registryLookup) {
		if (tag.contains("soulmate")) {
			this.soulmateId = tag.getUuid("soulmate");
			this.soulmateTime = tag.getLong("soulmateTime");
		} else {
			this.soulmateId = PlayerUtil.DEFAULT_UUID;
			this.soulmateTime = 0L;
		}
		this.heartbroken = tag.getBoolean("heartbroken");
	}

	@Override
	public void writeToNbt(NbtCompound tag, HolderLookup.Provider registryLookup) {
		if (!soulmateId.equals(PlayerUtil.DEFAULT_UUID)) {
			tag.putUuid("soulmate", this.soulmateId);
			tag.putLong("soulmateTime", this.soulmateTime);
		}
		tag.putBoolean("heartbroken", this.heartbroken);
	}

	public boolean isHeartbroken() {
		return heartbroken;
	}

	public void setHeartbroken(boolean heartbroken) {
		this.heartbroken = heartbroken;
	}

	public long getSoulmateTime() {
		return soulmateTime;
	}
}

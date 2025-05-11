package fr.kayrouge.popkorn.network.packet;

import fr.kayrouge.popkorn.abilities.Ability;
import fr.kayrouge.popkorn.abilities.DashAbility;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.encoding.StringEncoding;

import java.util.HashMap;
import java.util.Map;

public interface PKPacketCodecs {

	PacketCodec<ByteBuf, Map<String, Ability>> ABILITY_MAP = new PacketCodec<>() {
		public Map<String, Ability> decode(ByteBuf byteBuf) {
			Map<String, Ability> abilities = new HashMap<>();
			int size = byteBuf.readInt();
			for (int i = 0; i < size; i++) {
				String key = StringEncoding.read(byteBuf, 16);
				final int remainingCharges = PacketCodecs.INT.decode(byteBuf);
				final int maxCharges = PacketCodecs.INT.decode(byteBuf);

				final int cooldown = PacketCodecs.INT.decode(byteBuf);
				final int cooldownTime = PacketCodecs.INT.decode(byteBuf);

				final boolean shouldSync = PacketCodecs.BOOL.decode(byteBuf);
				boolean isUsed = false;
				if(shouldSync) {
					isUsed = PacketCodecs.BOOL.decode(byteBuf);
				}

				Ability ability = new Ability(maxCharges, cooldownTime);
				ability.setCharges(remainingCharges);
				ability.setCooldown(cooldown);

				if(shouldSync) {
					ability.setUsed(isUsed);
				}

				abilities.put(key, ability);
			}
			return abilities;
		}
		public void encode(ByteBuf byteBuf, Map<String, Ability> abilities) {
			byteBuf.writeInt(abilities.size());

			for (Map.Entry<String, Ability> entry : abilities.entrySet()) {

				StringEncoding.write(byteBuf, entry.getKey(), 16);

				PacketCodecs.INT.encode(byteBuf, entry.getValue().getRemainingCharges());
				PacketCodecs.INT.encode(byteBuf, entry.getValue().getMaxCharges());

				PacketCodecs.INT.encode(byteBuf, entry.getValue().getCooldown());
				PacketCodecs.INT.encode(byteBuf, entry.getValue().getCooldownTime());

				boolean shouldSync = entry.getValue().shouldSyncIsUsed();
				PacketCodecs.BOOL.encode(byteBuf, shouldSync);
				if(shouldSync) {
					PacketCodecs.BOOL.encode(byteBuf, entry.getValue().isUsed());
				}
			}
		}
	};
}

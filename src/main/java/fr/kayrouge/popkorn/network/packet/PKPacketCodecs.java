package fr.kayrouge.popkorn.network.packet;

import fr.kayrouge.popkorn.abilities.Ability;
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
				int remainingCharges = PacketCodecs.INT.decode(byteBuf);
				int maxCharges = PacketCodecs.INT.decode(byteBuf);

				int cooldown = PacketCodecs.INT.decode(byteBuf);
				int cooldownTime = PacketCodecs.INT.decode(byteBuf);

				Ability ability = new Ability(maxCharges, cooldownTime, false);
				ability.setCharges(remainingCharges);
				ability.setCooldown(cooldown);

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
			}
		}
	};
}

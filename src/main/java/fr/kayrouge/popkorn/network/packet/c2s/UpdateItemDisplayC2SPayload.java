package fr.kayrouge.popkorn.network.packet.c2s;

import fr.kayrouge.popkorn.blocks.entity.ItemDisplayBlockEntity;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import fr.kayrouge.popkorn.util.ScreenUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public record UpdateItemDisplayC2SPayload(BlockPos pos, boolean rotate, boolean upAndDown) implements CustomPayload {

	public static final CustomPayload.Id<UpdateItemDisplayC2SPayload> ID = new CustomPayload.Id<>(PKNetworkingConstants.UPDATE_DISPLAY_ITEM_PACKET_ID);

	public static final PacketCodec<RegistryByteBuf, UpdateItemDisplayC2SPayload> CODEC = PacketCodec.tuple(
		BlockPos.PACKET_CODEC, UpdateItemDisplayC2SPayload::pos,
		PacketCodecs.BOOL, UpdateItemDisplayC2SPayload::rotate,
		PacketCodecs.BOOL, UpdateItemDisplayC2SPayload::upAndDown,
		UpdateItemDisplayC2SPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public void receive(ServerPlayNetworking.Context context) {
		context.server().execute(() -> {
			if(ScreenUtil.canPlayerUseGuiButton(context.player(), pos, 1d)) {
				ServerWorld world = context.player().getServerWorld();

				if(world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity entity) {
					entity.rotate = rotate;
					entity.upAnDown = upAndDown;
					entity.markDirty();
					world.updateListeners(pos, entity.getCachedState(), entity.getCachedState(), 0);
				}
			}
			else {
				context.player().sendMessage(Text.translatable(
					"block.popkorn.item_display.notinrange"),
					true
				);
			}

		});
	}
}

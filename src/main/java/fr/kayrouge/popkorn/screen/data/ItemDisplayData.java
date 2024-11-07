package fr.kayrouge.popkorn.screen.data;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

public class ItemDisplayData {

	public BlockPos pos;
	public boolean rotate;
	public boolean upAnDown;

	public static final PacketCodec<RegistryByteBuf, ItemDisplayData> PACKET_CODEC = PacketCodec.tuple(
		BlockPos.PACKET_CODEC,
		ItemDisplayData::getPos,
		PacketCodecs.BOOL,
		ItemDisplayData::isRotate,
		PacketCodecs.BOOL,
		ItemDisplayData::isUpAnDown,
		ItemDisplayData::new
		);

	public ItemDisplayData(BlockPos pos, boolean rotate, boolean upAnDown) {
		this.pos = pos;
		this.rotate = rotate;
		this.upAnDown = upAnDown;
	}

	public BlockPos getPos() {
		return pos;
	}

	public boolean isRotate() {
		return rotate;
	}

	public boolean isUpAnDown() {
		return upAnDown;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}

	public void setUpAnDown(boolean upAnDown) {
		this.upAnDown = upAnDown;
	}
}

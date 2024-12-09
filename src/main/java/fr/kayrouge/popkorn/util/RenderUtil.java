package fr.kayrouge.popkorn.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class RenderUtil {

	public static void renderFloatingText(Text text, double x, double y, double z, MatrixStack matrices, VertexConsumerProvider provider, TextRotationType rotationType) {

		Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
		Vec3d cameraPos = camera.getPos();

		matrices.push();
		matrices.translate(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z);

		if(rotationType == TextRotationType.FOLLOW_PLAYER) {
			matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(-camera.getYaw()));
			matrices.rotate(Axis.X_POSITIVE.rotationDegrees(camera.getPitch()));
		} else {
			if (rotationType == TextRotationType.FACE_PLAYER_HORIZONTALLY || rotationType == TextRotationType.FACE_PLAYER_TOTALLY) {
				matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(Math.round(-camera.getYaw() / 90.0f)*90));
			}
			if(rotationType == TextRotationType.FACE_PLAYER_VERTICALLY || rotationType == TextRotationType.FACE_PLAYER_TOTALLY) {
				matrices.rotate(Axis.X_POSITIVE.rotationDegrees(Math.round(camera.getPitch() / 90.0f)*90));
			}
		}

		float scale = 0.025f;
		matrices.scale(-scale, -scale, scale);

		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		int textWidth = textRenderer.getWidth(text);

		textRenderer.draw(
			text,
			-textWidth / 2.0f, // Centrer le texte
			0,
			0xFFFFFF,
			false,
			matrices.peek().getModel(),
			provider,
			TextRenderer.TextLayerType.NORMAL,
			0,
			250
		);
		matrices.pop();
	}

	public static void renderTextOnBlockFace(Direction direction, Text text, BlockPos pos, MatrixStack matrices, VertexConsumerProvider provider, boolean upAndDownRotation) {
		Vec3d centralPos = Vec3d.ofCenter(pos);
		Vec3d textPos = centralPos.add(direction.getOffsetX() * -0.5, direction.getOffsetY() * -0.5, direction.getOffsetZ() * -0.5);
		double x = textPos.x+((double) direction.getOffsetX()/1000);
		double y = textPos.y+((double) direction.getOffsetY()/1000);
		double z = textPos.z+((double) direction.getOffsetZ()/1000);

		Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
		Vec3d cameraPos = camera.getPos();
		matrices.push();
		matrices.translate(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z);
		rotateWithDirection(matrices, direction);
		if(upAndDownRotation && (direction == Direction.DOWN || direction == Direction.UP)) {
			if(camera.getPitch() > 0) {
				matrices.rotate(Axis.Z_POSITIVE.rotationDegrees(camera.getYaw()));
			}
			else {
				matrices.rotate(Axis.Z_POSITIVE.rotationDegrees(-camera.getYaw()));
			}
		}
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		int textWidth = textRenderer.getWidth(text);
		float scale = 0.025f;
		matrices.scale(-scale, -scale, scale);
		textRenderer.draw(
			text,
			-textWidth / 2.0f,
			-textRenderer.fontHeight / 2.0f,
			0xFFFFFF,
			false,
			matrices.peek().getModel(),
			provider,
			TextRenderer.TextLayerType.NORMAL,
			0,
			250
		);
		matrices.pop();
	}

	public static void rotateWithDirection(MatrixStack matrices, Direction direction) {
		switch (direction) {
			case UP -> matrices.rotate(Axis.X_POSITIVE.rotationDegrees(90f));
			case DOWN -> matrices.rotate(Axis.X_POSITIVE.rotationDegrees(-90f));
			case SOUTH -> matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(180f));
			case WEST -> matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(90f));
			case EAST -> matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(-90f));
		}
	}

	public enum TextRotationType {
		FIXED,
		FOLLOW_PLAYER,
		FACE_PLAYER_TOTALLY,
		FACE_PLAYER_HORIZONTALLY,
		FACE_PLAYER_VERTICALLY
	}
}

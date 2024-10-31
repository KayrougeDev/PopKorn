package fr.kayrouge.popkorn.debug;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Axis;

public class AnimationTest {

	public static void test(MatrixStack matrices) {
		matrices.translate(0.1f,0.05f, 0f);
		matrices.rotate(Axis.X_POSITIVE.rotationDegrees(35));
		matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(10));
		matrices.rotate(Axis.Z_NEGATIVE.rotationDegrees(35));
	}
}

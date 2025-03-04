package fr.kayrouge.popkorn.client.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;

import java.util.OptionalDouble;

public class PKRenderLayer {

	public static final RenderLayer.MultiPhase XRAY_LINES;

	public static RenderLayer.MultiPhase getLineRenderLayer(double width) {
		return RenderLayer.of(
			"lines",
			VertexFormats.LINES,
			VertexFormat.DrawMode.LINES,
			1536,
			RenderLayer.MultiPhaseParameters.builder().shader(RenderPhase.LINES_SHADER)
				.lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(width)))
				.layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
				.transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
				.target(RenderPhase.ITEM_TARGET)
				.writeMaskState(RenderPhase.ALL_MASK).cull(RenderPhase.DISABLE_CULLING).build(false));
	}

	static {
		XRAY_LINES =  RenderLayer.of("xray_lines",
			VertexFormats.LINES, VertexFormat.DrawMode.LINES, 1536,
			RenderLayer.MultiPhaseParameters.builder().shader(RenderPhase.LINES_SHADER).lineWidth(
					new RenderPhase.LineWidth(OptionalDouble.of(5d)))
				.layering(RenderPhase.NO_LAYERING).
				transparency(RenderPhase.NO_TRANSPARENCY).
				target(RenderPhase.MAIN_TARGET).writeMaskState(RenderPhase.ALL_MASK)
				.cull(RenderPhase.DISABLE_CULLING)
				.depthTest(RenderPhase.GREATER_THAN_DEPTH_TEST)
				.build(false));
	}
}

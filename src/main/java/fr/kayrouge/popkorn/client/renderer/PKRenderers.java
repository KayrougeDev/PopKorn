package fr.kayrouge.popkorn.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.Map;

public class PKRenderers {

	public static final PKRenderers INSTANCE = new PKRenderers();

	private final Map<IPKRender, Long> renderTasks = new HashMap<>();

	public synchronized void addRenderTask(IPKRender render, long time) {
		renderTasks.put(render, System.currentTimeMillis()+time);
	}

	public synchronized Map<IPKRender, Long> getRenderTasks() {
		return renderTasks;
	}

	@FunctionalInterface
	public interface IPKRender {
		Runnable render(RenderContext renderContext, long endTimeMillis);
	}

	public record RenderContext(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {}

}

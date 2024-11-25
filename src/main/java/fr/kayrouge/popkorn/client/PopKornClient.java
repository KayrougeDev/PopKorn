package fr.kayrouge.popkorn.client;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.ChunkRendererBlock;
import fr.kayrouge.popkorn.client.renderer.PKRenderers;
import fr.kayrouge.popkorn.network.packet.s2c.RayLauncherUseS2CPayload;
import fr.kayrouge.popkorn.registry.PKClientRegistry;
import fr.kayrouge.popkorn.registry.PKKeybindings;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.border.WorldBorder;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class PopKornClient implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(PopKorn.MODID);
	@Override
	public void onInitializeClient(ModContainer mod) {
		PKNetworkingConstants.registerS2CGlobalReceiver();
		PKKeybindings.registerAction();

		PKClientRegistry.initialize();
		WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> !(blockOutlineContext.blockState().getBlock() instanceof ChunkRendererBlock));
		WorldRenderEvents.LAST.register((worldRenderContext) -> {
			MinecraftClient client = worldRenderContext.gameRenderer().getClient();
			client.execute(() -> {
				Iterator<Map.Entry<PKRenderers.IPKRender, Long>> iterator = PKRenderers.INSTANCE.getRenderTasks().entrySet().iterator();

				while (iterator.hasNext()) {
					Map.Entry<PKRenderers.IPKRender, Long> task = iterator.next();

					task.getKey().render(worldRenderContext.matrixStack(), worldRenderContext.consumers(), task.getValue());

					if (task.getValue() < System.currentTimeMillis()) iterator.remove();
				}
			});
		});
	}
}

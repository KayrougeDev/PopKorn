package fr.kayrouge.popkorn.client;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.ChunkRendererBlock;
import fr.kayrouge.popkorn.client.renderer.PKRenderers;
import fr.kayrouge.popkorn.client.renderer.WorldCustomRenderer;
import fr.kayrouge.popkorn.registry.PKClientRegistry;
import fr.kayrouge.popkorn.registry.PKItems;
import fr.kayrouge.popkorn.registry.PKKeybindings;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
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
		WorldCustomRenderer.render();
	}
}

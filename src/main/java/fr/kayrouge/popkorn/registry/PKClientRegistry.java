package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.blocks.entity.renderer.ChunkRendererBlockEntityRenderer;
import fr.kayrouge.popkorn.blocks.entity.renderer.DemonicAltarBlockEntityRenderer;
import fr.kayrouge.popkorn.blocks.entity.renderer.GhostBlockEntityRenderer;
import fr.kayrouge.popkorn.blocks.entity.renderer.ItemDisplayBlockEntityRenderer;
import fr.kayrouge.popkorn.client.screen.ingame.DemonicAltarScreen;
import fr.kayrouge.popkorn.client.screen.ingame.ItemDisplayScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class PKClientRegistry {

	public static void initialize() {
		HandledScreens.register(PKHandledScreens.DEMONIC_ALTAR_SCREEN_HANDLER, DemonicAltarScreen::new);
		HandledScreens.register(PKHandledScreens.ITEM_DISPLAY_SCREEN_HANDLER, ItemDisplayScreen::new);

		BlockEntityRendererFactories.register(PKBlockEntityTypes.DEMONIC_ALTAR_BLOCK_ENTITY_TYPE, DemonicAltarBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(PKBlockEntityTypes.ITEM_DISPLAY_BLOCK_ENTITY_TYPE, ItemDisplayBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(PKBlockEntityTypes.CHUNK_RENDERER_BLOCK_ENTITY, ChunkRendererBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(PKBlockEntityTypes.GHOST_BLOCK_ENTITY_TYPE, GhostBlockEntityRenderer::new);
	}

}

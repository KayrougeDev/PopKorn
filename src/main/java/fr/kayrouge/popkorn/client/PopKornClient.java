package fr.kayrouge.popkorn.client;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.entity.PKBlockEntityTypes;
import fr.kayrouge.popkorn.blocks.entity.renderer.DemonicAltarBlockEntityRenderer;
import fr.kayrouge.popkorn.blocks.entity.renderer.ItemDisplayBlockEntityRenderer;
import fr.kayrouge.popkorn.client.screen.ingame.ItemDisplayScreen;
import fr.kayrouge.popkorn.screen.PKHandledScreens;
import fr.kayrouge.popkorn.client.screen.ingame.DemonicAltarScreen;
import fr.kayrouge.popkorn.network.packet.PKNetworkingConstants;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PopKornClient implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(PopKorn.MODID);
	@Override
	public void onInitializeClient(ModContainer mod) {
		PKNetworkingConstants.registerS2CGlobalReceiver();
		PKKeybindings.registerAction();

		HandledScreens.register(PKHandledScreens.DEMONIC_ALTAR_SCREEN_HANDLER, DemonicAltarScreen::new);
		HandledScreens.register(PKHandledScreens.ITEM_DISPLAY_SCREEN_HANDLER, ItemDisplayScreen::new);

		BlockEntityRendererFactories.register(PKBlockEntityTypes.DEMONIC_ALTAR_BLOCK_ENTITY_TYPE, DemonicAltarBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(PKBlockEntityTypes.ITEM_DISPLAY_BLOCK_ENTITY_TYPE, ItemDisplayBlockEntityRenderer::new);

//		WorldRenderEvents.LAST.register(context -> {
//			renderTopScare(context.matrixStack(), new BlockPos(0, -60, -3));
//		});
//		WorldRenderEvents.BLOCK_OUTLINE.register((worldRenderContext, blockOutlineContext) -> {
//			renderTopScare(Objects.requireNonNull(worldRenderContext.matrixStack()), blockOutlineContext.blockPos());
//			return true;
//		});
	}


}

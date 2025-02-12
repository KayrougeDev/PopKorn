package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PKBlocks {

	public static final Block ELEVATOR;
	public static final Block TECHNOLOGY_CORE;
	public static final Block DEMONIC_ALTAR;
	public static final Block ITEM_DISPLAY;
	public static final Block CHUNK_RENDERER;
	public static final Block GHOST_BLOCK;

	static {
		ELEVATOR = register("elevator", new ElevatorBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));
		TECHNOLOGY_CORE = register("technology_core", new TechnologyCoreBlock(AbstractBlock.Settings.copy(Blocks.HEAVY_CORE)));
		DEMONIC_ALTAR = register("demonic_altar", new DemonicAltarBlock(AbstractBlock.Settings.create()));
		ITEM_DISPLAY = register("item_display", new ItemDisplayBlock(AbstractBlock.Settings.create()));
		CHUNK_RENDERER = register("chunk_renderer", new ChunkRendererBlock(AbstractBlock.Settings.create()));
		GHOST_BLOCK = register("ghost_block", new GhostBlock(AbstractBlock.Settings.create()));
	}


	public static void initialize() {
	}

	public static Block register(String name, Block block) {
		return Registry.register(Registries.BLOCK, Identifier.of(PopKorn.MODID, name), block);
	}
}

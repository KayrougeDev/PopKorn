package fr.kayrouge.popkorn.blocks;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.entity.DemonicAltarBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PKBlocks {

	public static final ElevatorBlock ELEVATOR;
	public static final TechnologyCoreBlock TECHNOLOGY_CORE;
	public static final DemonicAltarBlock DEMONIC_ALTAR;

	static {
		ELEVATOR = register(new ElevatorBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)), "elevator");
		TECHNOLOGY_CORE = register(new TechnologyCoreBlock(AbstractBlock.Settings.copy(Blocks.HEAVY_CORE)), "technology_core");
		DEMONIC_ALTAR = register(new DemonicAltarBlock(AbstractBlock.Settings.create()), "demonic_altar");
	}


	public static void initialize() {
	}

	public static <T extends Block> T register(T block, String name) {
		Registry.register(Registries.BLOCK, Identifier.of(PopKorn.MODID, name), block);
		return block;
	}
}

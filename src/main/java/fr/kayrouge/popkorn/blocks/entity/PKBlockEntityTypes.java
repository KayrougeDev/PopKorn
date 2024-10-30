package fr.kayrouge.popkorn.blocks.entity;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PKBlockEntityTypes {

	public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PopKorn.MODID, path), blockEntityType);
	}

	public static final BlockEntityType<DemonicAltarBlockEntity> DEMONIC_ALTAR;

	static {
		DEMONIC_ALTAR = register("demonic_altar",BlockEntityType.Builder.create(DemonicAltarBlockEntity::new, PKBlocks.DEMONIC_ALTAR).build());
	}

	public static void initialize() {
	}

}

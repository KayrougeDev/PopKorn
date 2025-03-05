package fr.kayrouge.popkorn.registry;

import com.mojang.datafixers.types.Type;
import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.entity.ChunkRendererBlockEntity;
import fr.kayrouge.popkorn.blocks.entity.DemonicAltarBlockEntity;
import fr.kayrouge.popkorn.blocks.entity.GhostBlockEntity;
import fr.kayrouge.popkorn.blocks.entity.ItemDisplayBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class PKBlockEntityTypes {

	public static final BlockEntityType<DemonicAltarBlockEntity> DEMONIC_ALTAR_BLOCK_ENTITY_TYPE;
	public static final BlockEntityType<ItemDisplayBlockEntity> ITEM_DISPLAY_BLOCK_ENTITY_TYPE;
	public static final BlockEntityType<ChunkRendererBlockEntity> CHUNK_RENDERER_BLOCK_ENTITY;
	public static final BlockEntityType<GhostBlockEntity> GHOST_BLOCK_ENTITY_TYPE;

	static {
		DEMONIC_ALTAR_BLOCK_ENTITY_TYPE = create("demonic_altar", BlockEntityType.Builder.create(DemonicAltarBlockEntity::new, PKBlocks.DEMONIC_ALTAR));
		ITEM_DISPLAY_BLOCK_ENTITY_TYPE = create("item_display", BlockEntityType.Builder.create(ItemDisplayBlockEntity::new, PKBlocks.ITEM_DISPLAY));
		CHUNK_RENDERER_BLOCK_ENTITY = create("chunk_renderer", BlockEntityType.Builder.create(ChunkRendererBlockEntity::new, PKBlocks.CHUNK_RENDERER));
		GHOST_BLOCK_ENTITY_TYPE = create("ghost_block", BlockEntityType.Builder.create(GhostBlockEntity::new, PKBlocks.GHOST_BLOCK));
	}

	public static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
		Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PopKorn.MODID, id), builder.build(type));
	}

	public static void initialize() {
	}

}

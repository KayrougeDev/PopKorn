package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.items.EquippableItem;
import fr.kayrouge.popkorn.items.HeartBrokenKnifeItem;
import fr.kayrouge.popkorn.items.RayLauncherItem;
import fr.kayrouge.popkorn.items.SoulRayItem;
import fr.kayrouge.popkorn.items.armor.PKArmorMaterials;
import fr.kayrouge.popkorn.items.group.PKItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PKItems {

	public static final Item RAY_LAUNCHER_ITEM;
	public static final Item SOUL_RAY_ITEM;
	public static final Item BROKEN_HEART_KNIFE;

	public static final Item CHAINSAW;
	public static final Item SOLIDIFIED_DEMONIC_ENERGY;
	public static final Item RAW_DEMONIC_ENERGY;
	public static final Item LIGHT_GLASSES;

	public static final Item DEMONIC_HELMET;
	public static final Item DEMONIC_CHESTPLATE;
	public static final Item DEMONIC_LEGGINGS;
	public static final Item DEMONIC_BOOTS;

	public static final Item ELEVATOR;
	public static final Item TECHNOLOGY_CORE;
	public static final Item DEMONIC_ALTAR;
	public static final Item ITEM_DISPLAY;
	public static final Item CHUNK_RENDERER;
	public static final Item GHOST_BLOCK;


	static {
		RAY_LAUNCHER_ITEM = register("ray_launcher", new RayLauncherItem(), ItemGroups.TOOLS_AND_UTILITIES);
		SOUL_RAY_ITEM = register("soul_ray_item", new SoulRayItem(new Item.Settings()), PKItemGroups.SOULMATE);
		BROKEN_HEART_KNIFE = register("broken_hearts_knife", new HeartBrokenKnifeItem(new Item.Settings().maxCount(1)), PKItemGroups.SOULMATE);

		CHAINSAW = register("chainsaw", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)), ItemGroups.TOOLS_AND_UTILITIES);

		SOLIDIFIED_DEMONIC_ENERGY = register("solidified_demonic_energy", new Item(new Item.Settings().rarity(Rarity.RARE)), PKItemGroups.DEMONIC);
		RAW_DEMONIC_ENERGY = register("raw_demonic_energy", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)), PKItemGroups.DEMONIC);

		LIGHT_GLASSES = register("light_glasses", new EquippableItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1), EquipmentSlot.HEAD), ItemGroups.TOOLS_AND_UTILITIES);

		DEMONIC_HELMET = register("demonic_helmet", new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.HELMET.getBaseDurability(40))), PKItemGroups.DEMONIC);
		DEMONIC_CHESTPLATE = register("demonic_chestplate", new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.CHESTPLATE.getBaseDurability(40))), PKItemGroups.DEMONIC);
		DEMONIC_LEGGINGS = register("demonic_leggings", new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.LEGGINGS.getBaseDurability(40))), PKItemGroups.DEMONIC);
		DEMONIC_BOOTS = register("demonic_boots", new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.BOOTS.getBaseDurability(40))), PKItemGroups.DEMONIC);

		ELEVATOR = register("elevator", new BlockItem(PKBlocks.ELEVATOR,  new Item.Settings().rarity(Rarity.RARE)), ItemGroups.REDSTONE_BLOCKS);
		TECHNOLOGY_CORE = register("technology_core", new BlockItem(PKBlocks.TECHNOLOGY_CORE, new Item.Settings().rarity(Rarity.EPIC).maxCount(32)), ItemGroups.REDSTONE_BLOCKS);
		DEMONIC_ALTAR = register("demonic_altar", new BlockItem(PKBlocks.DEMONIC_ALTAR, new Item.Settings().rarity(Rarity.EPIC).fireproof()), ItemGroups.FUNCTIONAL_BLOCKS);
		ITEM_DISPLAY = register("item_display", new BlockItem(PKBlocks.ITEM_DISPLAY, new Item.Settings()), ItemGroups.FUNCTIONAL_BLOCKS);
		CHUNK_RENDERER = register("chunk_renderer", new BlockItem(PKBlocks.CHUNK_RENDERER, new Item.Settings()));
		GHOST_BLOCK = register("ghost_block", new BlockItem(PKBlocks.GHOST_BLOCK, new Item.Settings()));
	}

	public static Item register(Block block) {
		return Items.register(block);
	}

	public static Item register(String id, Item item) {
		return Items.register(Identifier.of(PopKorn.MODID, id), item);
	}

	public static Item register(String name, Item item, RegistryKey<ItemGroup> group) {
		item = register(name, item);
		registerInItemGroup(group, item);
		return item;
	}

	public static void registerInItemGroup(RegistryKey<ItemGroup> group, Item item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addItem(item));
	}

	public static void initialize() {
	}
}

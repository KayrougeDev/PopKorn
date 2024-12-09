package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.items.EquippableItem;
import fr.kayrouge.popkorn.items.RayLauncherItem;
import fr.kayrouge.popkorn.items.armor.PKArmorMaterials;
import fr.kayrouge.popkorn.items.group.PKItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PKItems {

	public static final RayLauncherItem RAY_LAUNCHER_ITEM;

	public static final Item CHAINSAW;
	public static final Item SOLIDIFIED_DEMONIC_ENERGY;
	public static final Item RAW_DEMONIC_ENERGY;
	public static final Item LIGHT_GLASSES;

	public static final ArmorItem DEMONIC_HELMET;
	public static final ArmorItem DEMONIC_CHESTPLATE;
	public static final ArmorItem DEMONIC_LEGGINGS;
	public static final ArmorItem DEMONIC_BOOTS;

	public static final BlockItem ELEVATOR;
	public static final BlockItem TECHNOLOGY_CORE;
	public static final BlockItem DEMONIC_ALTAR;
	public static final BlockItem ITEM_DISPLAY;
	public static final BlockItem CHUNK_RENDERER;


	static {
		RAY_LAUNCHER_ITEM = register("ray_launcher", new RayLauncherItem(), ItemGroups.TOOLS_AND_UTILITIES);

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
	}

	public static <T extends Item> T register(String name, T item) {
		Registry.register(Registries.ITEM, Identifier.of(PopKorn.MODID, name), item);
		return item;
	}

	public static <T extends Item> T register(String name, T item, RegistryKey<ItemGroup> group) {
		register(name, item);
		registerInItemGroup(group, item);
		return item;
	}

	public static void registerInItemGroup(RegistryKey<ItemGroup> group, Item item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addItem(item));
	}

	public static void initialize() {
	}
}

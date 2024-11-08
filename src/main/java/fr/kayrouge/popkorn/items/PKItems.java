package fr.kayrouge.popkorn.items;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import fr.kayrouge.popkorn.debug.DebugItem;
import fr.kayrouge.popkorn.items.armor.PKArmorMaterials;
import fr.kayrouge.popkorn.items.group.PKItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class PKItems {

	/**
	 * This constant is used for debugging purposes.
	 */
	@Deprecated
	public static final RayLauncherItem RAY_LAUNCHER_ITEM = register(new RayLauncherItem(), "ray_launcher");

	public static final Item CHAINSAW;
	public static final Item SOLIDIFIED_DEMONIC_ENERGY;
	public static final Item RAW_DEMONIC_ENERGY;

	public static final ArmorItem DEMONIC_HELMET;
	public static final ArmorItem DEMONIC_CHESTPLATE;
	public static final ArmorItem DEMONIC_LEGGINGS;
	public static final ArmorItem DEMONIC_BOOTS;

	public static final BlockItem ELEVATOR;
	public static final BlockItem TECHNOLOGY_CORE;
	public static final BlockItem DEMONIC_ALTAR;
	public static final BlockItem ITEM_DISPLAY;


	static {
		CHAINSAW = register(new Item(new Item.Settings().rarity(Rarity.UNCOMMON)), "chainsaw", ItemGroups.TOOLS_AND_UTILITIES);

		SOLIDIFIED_DEMONIC_ENERGY = register(new Item(new Item.Settings().rarity(Rarity.RARE)), "solidified_demonic_energy", PKItemGroups.DEMONIC);
		RAW_DEMONIC_ENERGY = register(new Item(new Item.Settings().rarity(Rarity.UNCOMMON)), "raw_demonic_energy", PKItemGroups.DEMONIC);

		DEMONIC_HELMET = register(new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.HELMET, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.HELMET.getBaseDurability(40))), "demonic_helmet", PKItemGroups.DEMONIC);
		DEMONIC_CHESTPLATE = register(new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.CHESTPLATE, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.CHESTPLATE.getBaseDurability(40))), "demonic_chestplate", PKItemGroups.DEMONIC);
		DEMONIC_LEGGINGS = register(new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.LEGGINGS, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.LEGGINGS.getBaseDurability(40))), "demonic_leggings", PKItemGroups.DEMONIC);
		DEMONIC_BOOTS = register(new ArmorItem(PKArmorMaterials.DEMONIC, ArmorItem.ArmorSlot.BOOTS, new Item.Settings().fireproof().maxDamage(ArmorItem.ArmorSlot.BOOTS.getBaseDurability(40))), "demonic_boots", PKItemGroups.DEMONIC);

		ELEVATOR = register(new BlockItem(PKBlocks.ELEVATOR,  new Item.Settings().rarity(Rarity.RARE)), "elevator",ItemGroups.REDSTONE_BLOCKS);
		TECHNOLOGY_CORE = register(new BlockItem(PKBlocks.TECHNOLOGY_CORE, new Item.Settings().rarity(Rarity.EPIC).maxCount(32)), "technology_core", ItemGroups.REDSTONE_BLOCKS);
		DEMONIC_ALTAR = register(new BlockItem(PKBlocks.DEMONIC_ALTAR, new Item.Settings().rarity(Rarity.EPIC).fireproof()), "demonic_altar", ItemGroups.FUNCTIONAL_BLOCKS);
		ITEM_DISPLAY = register(new BlockItem(PKBlocks.ITEM_DISPLAY, new Item.Settings()), "item_display", ItemGroups.FUNCTIONAL_BLOCKS);
	}

	public static <T extends Item> T register(T item, String name) {
		Registry.register(Registries.ITEM, Identifier.of(PopKorn.MODID, name), item);
		return item;
	}

	public static <T extends Item> T register(T item, String name, RegistryKey<ItemGroup> group) {
		Registry.register(Registries.ITEM, Identifier.of(PopKorn.MODID, name), item);
		registerInItemGroup(group, item);
		return item;
	}

	public static void registerInItemGroup(RegistryKey<ItemGroup> group, Item item) {
		if(item instanceof DebugItem && !PopKorn.DEBUG) return;
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addItem(item));
	}

	public static void initialize() {
	}
}

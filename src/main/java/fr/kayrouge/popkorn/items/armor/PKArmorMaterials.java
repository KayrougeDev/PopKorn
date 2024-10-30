package fr.kayrouge.popkorn.items.armor;

import fr.kayrouge.popkorn.items.PKItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class PKArmorMaterials {

	public static final Holder<ArmorMaterial> DEMONIC;


	static {
		DEMONIC = register("demonic", Util.make(new EnumMap<>(ArmorItem.ArmorSlot.class), (map) -> {
			map.put(ArmorItem.ArmorSlot.BOOTS, 4);
			map.put(ArmorItem.ArmorSlot.LEGGINGS, 6);
			map.put(ArmorItem.ArmorSlot.CHESTPLATE, 9);
			map.put(ArmorItem.ArmorSlot.HELMET, 4);
			map.put(ArmorItem.ArmorSlot.BODY, 12);
		}), 16, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 3.5F, 0.2F, () -> Ingredient.ofItems(PKItems.SOLIDIFIED_DEMONIC_ENERGY));
	}


	private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.ArmorSlot, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Identifier.ofDefault(name)));
		return register(name, defense, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, list);
	}

	private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.ArmorSlot, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
		EnumMap<ArmorItem.ArmorSlot, Integer> enumMap = new EnumMap<>(ArmorItem.ArmorSlot.class);
		ArmorItem.ArmorSlot[] var9 = ArmorItem.ArmorSlot.values();
		int var10 = var9.length;

		for (ArmorItem.ArmorSlot armorSlot : var9) {
			enumMap.put(armorSlot, defense.get(armorSlot));
		}

		return Registry.registerHolder(Registries.ARMOR_MATERIAL, Identifier.ofDefault(name), new ArmorMaterial(enumMap, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockbackResistance));
	}

}

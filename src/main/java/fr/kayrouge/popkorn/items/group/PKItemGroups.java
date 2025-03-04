package fr.kayrouge.popkorn.items.group;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.registry.PKItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PKItemGroups {

	public static final RegistryKey<ItemGroup> DEMONIC;
	public static final RegistryKey<ItemGroup> SOULMATE;

	private static RegistryKey<ItemGroup> createRegistryKey(String name, ItemGroup group) {
		Registry.register(Registries.ITEM_GROUP, Identifier.of(PopKorn.MODID, name), group);
		return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(PopKorn.MODID, name));
	}

	static {
		DEMONIC = createRegistryKey("demonic", new ItemGroup.Builder(ItemGroup.VerticalPosition.TOP, 0)
			.icon(() -> new ItemStack(PKItems.RAW_DEMONIC_ENERGY))
			.name(Text.translatable("itemGroup.demonic"))
			.build());

		SOULMATE = createRegistryKey("soulmate", new ItemGroup.Builder(ItemGroup.VerticalPosition.TOP, 1)
			.icon(() -> new ItemStack(PKItems.SOUL_RAY_ITEM))
			.name(Text.translatable("itemGroup.soulmate"))
			.build());
	}

	public static void initialize() {}

}

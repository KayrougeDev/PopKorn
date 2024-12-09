package fr.kayrouge.popkorn.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equippable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EquippableItem extends Item implements Equippable {

	private final EquipmentSlot slot;

	public EquippableItem(Settings settings, EquipmentSlot slot) {
		super(settings);
		this.slot = slot;
	}

	@Override
	public EquipmentSlot getPreferredSlot() {
		return this.slot;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return use(this, world, user, hand);
	}
}

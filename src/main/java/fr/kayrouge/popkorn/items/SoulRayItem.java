package fr.kayrouge.popkorn.items;

import fr.kayrouge.popkorn.registry.PKComponents;
import fr.kayrouge.popkorn.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class SoulRayItem extends Item {

	public static final long ANIMATION_TIME = 3000L;

	public SoulRayItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if(!user.getWorld().isClient && !PKComponents.PLAYER_DATA.get(user).isHeartbroken() && PKComponents.ENTITY_DATA.get(entity).getPlayerMate() == PlayerUtil.DEFAULT_UUID) {
			PKComponents.PLAYER_DATA.get(user).setSoulmateId(entity.getUuid(), user.getWorld().getTime());
			PKComponents.PLAYER_DATA.sync(user);
		}

		return ActionResult.PASS;
	}
}

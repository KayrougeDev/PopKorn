package fr.kayrouge.popkorn.items;

import fr.kayrouge.popkorn.registry.PKComponents;
import net.minecraft.client.item.TooltipConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class HeartBrokenKnifeItem extends Item {

	public HeartBrokenKnifeItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipConfig config) {
		tooltip.add(Text.translatable("soulmate.heartbroken.nofallback").setColor(Color.RED.getRGB()));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		if(!world.isClient) {
			boolean heartbroken = PKComponents.PLAYER_DATA.get(user).isHeartbroken();
			if(heartbroken) {
				PKComponents.PLAYER_DATA.get(user).setHeartbroken(false);
				PKComponents.PLAYER_DATA.sync(user);
				user.getStackInHand(hand).decrement(1);
				user.kill();
			}
			else {
				user.sendMessage(Text.translatable("soulmate.heartbroken.not").setColor(Color.PINK.getRGB()),true);
				return TypedActionResult.fail(user.getStackInHand(hand));
			}
		}

		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}

package fr.kayrouge.popkorn.debug;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.registry.PKItems;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class DebugBlockItem extends BlockItem {

	public DebugBlockItem(Block block, Settings settings) {
		super(block, settings);
		PKItems.registerInItemGroup(ItemGroups.OPERATOR_UTILITIES, this);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipConfig config) {
		tooltip.add(Text.translatable("debug.popkorn.item.tooltip").setColor(Color.RED.getRGB()));
		if(!PopKorn.DEBUG) {
			tooltip.add(Text.translatable("debug.popkorn.disabled").setColor(new Color(0x6B0AB6).getRGB()));
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if(PopKorn.DEBUG) {
			return super.use(world, user, hand);
		}
		else {
			return TypedActionResult.fail(user.getStackInHand(hand));
		}
	}
}

package fr.kayrouge.popkorn.debug;

import fr.kayrouge.popkorn.PopKorn;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public abstract class DebugBlock extends Block {


	public DebugBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if(PopKorn.DEBUG) {
			placed(world, pos, state, placer, itemStack);
			super.onPlaced(world, pos, state, placer, itemStack);
		}
		else {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			if(placer instanceof PlayerEntity player) {
				player.sendMessage(Text.translatable("debug.popkorn.item.disabled").setColor(new Color(0xF30E68).getRGB()), true);
			}
		}
	}

	protected abstract void placed(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack);
}

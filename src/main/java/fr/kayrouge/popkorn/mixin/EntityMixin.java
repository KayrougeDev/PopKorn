package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.blocks.ElevatorBlock;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
	public void startSneak(boolean sneaking, CallbackInfo ci) {
		if((Object) this instanceof PlayerEntity player) {
			if(player.getWorld().isClient) return;
			if(sneaking) {
				World world = player.getWorld();
				BlockPos blockPos = player.getBlockPos().down();
				if(world.getBlockState(blockPos).getBlock() == PKBlocks.ELEVATOR) {
					for(int i = blockPos.getY()-1; i > world.getBottomY()-1; i--) {
						BlockPos nextPos = blockPos.withY(i);
						if(world.getBlockState(nextPos).getBlock() == PKBlocks.ELEVATOR) {
							world.setBlockState(blockPos, PKBlocks.ELEVATOR.getDefaultState().with(Properties.POWERED, true).with(Properties.VERTICAL_DIRECTION, Direction.DOWN));
							((ElevatorBlock)world.getBlockState(blockPos).getBlock()).scheduleTick(world, blockPos);
							player.teleport(nextPos.getX()+0.5d, nextPos.getY()+1d, nextPos.getZ()+0.5d, false);
							ci.cancel();
							return;
						}
					}
				}
			}
		}
	}
}

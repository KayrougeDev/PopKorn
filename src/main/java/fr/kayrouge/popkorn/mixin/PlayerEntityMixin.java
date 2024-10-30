package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.blocks.ElevatorBlock;
import fr.kayrouge.popkorn.blocks.PKBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

	@Inject(method = "jump", at = @At("HEAD"), cancellable = true)
	public void jump(CallbackInfo ci) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if(player.getWorld().isClient) return;
		BlockPos blockPos = player.getBlockPos().down();
		World world = player.getWorld();
		if(world.getBlockState(blockPos).getBlock() == PKBlocks.ELEVATOR) {
			for(int i = blockPos.getY()+1; i < world.getHeight(); i++) {
				BlockPos nextPos = blockPos.withY(i);
				if(world.getBlockState(nextPos).getBlock() == PKBlocks.ELEVATOR) {
					if(player.teleport(nextPos.getX()+0.5d, nextPos.getY()+1d, nextPos.getZ()+0.5d, false)) {
						world.setBlockState(blockPos, PKBlocks.ELEVATOR.getDefaultState().with(Properties.POWERED, true).with(Properties.VERTICAL_DIRECTION, Direction.UP));
						((ElevatorBlock)world.getBlockState(blockPos).getBlock()).scheduleTick(world, blockPos);
						ci.cancel();
						return;
					}
				}
			}
		}
	}
}

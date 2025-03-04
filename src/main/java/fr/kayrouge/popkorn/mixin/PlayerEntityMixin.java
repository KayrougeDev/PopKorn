package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.blocks.ElevatorBlock;
import fr.kayrouge.popkorn.registry.PKBlocks;
import fr.kayrouge.popkorn.util.configs.PopKornServerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
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

			int distance = world.getHeight();

			boolean notInfinite = PopKornServerConfig.INSTANCE.elevatorMaxDistance.value() != 0;

			if(notInfinite) {
				distance = blockPos.getY()+PopKornServerConfig.INSTANCE.elevatorMaxDistance.value();
			}

			for(int i = blockPos.getY()+1; i < Math.min(world.getHeight(), distance); i++) {
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
			if(notInfinite) {
				player.sendMessage(Text.translatable("block.popkorn.elevator.noblockinrange", PopKornServerConfig.INSTANCE.elevatorMaxDistance.value()), true);
			}
		}
	}
}

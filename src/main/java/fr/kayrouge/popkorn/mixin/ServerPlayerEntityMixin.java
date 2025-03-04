package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.registry.PKComponents;
import fr.kayrouge.popkorn.registry.PKDamageType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends Entity {

	@Shadow
	public abstract ServerWorld getServerWorld();

	public ServerPlayerEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "onDeath", at = @At("TAIL"))
	public void onDeath(DamageSource source, CallbackInfo ci) {
		if(this.getWorld().isClient) return;
		Entity entity = getServerWorld().getEntity(PKComponents.PLAYER_DATA.get(this).getSoulmateId());
		if(entity == null) return;
		entity.damage(PKDamageType.getDamageSource(this.getWorld(), PKDamageType.HEART_BROKEN_DAMAGE), Float.MAX_VALUE);
	}

}

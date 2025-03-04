package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.network.packet.c2s.SoulmateConnectionC2SPayload;
import fr.kayrouge.popkorn.registry.PKComponents;
import fr.kayrouge.popkorn.registry.PKDamageType;
import fr.kayrouge.popkorn.registry.potion.PKEffects;
import fr.kayrouge.popkorn.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Redirect(method = "updatePotionVisibility()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V", ordinal = 1))
	public void visibility(LivingEntity instance, boolean b) {
		if(instance.hasStatusEffect(StatusEffects.INVISIBILITY)) {
			instance.setInvisible(true);
			return;
		}

		if(!instance.hasStatusEffect(PKEffects.SHADOW_WALKER)) {
			instance.setInvisible(false);
		}
	}


	@Inject(method = "onDeath", at = @At(value = "HEAD"))
	public void onDeath(DamageSource source, CallbackInfo ci) {
		if(getWorld().isClient) return;
		if(PKComponents.ENTITY_DATA.get(this).getPlayerMate().equals(PlayerUtil.DEFAULT_UUID)) return;
		if(getWorld().getPlayerByUuid(PKComponents.ENTITY_DATA.get(this).getPlayerMate()) instanceof ServerPlayerEntity player) {
			if(source.getType() == PKDamageType.getDamageType(PKDamageType.HEART_BROKEN_DAMAGE, getWorld())) {
				PKComponents.PLAYER_DATA.get(player).setSoulmateId(PlayerUtil.DEFAULT_UUID, 0);
				PKComponents.PLAYER_DATA.sync(player);
				return;
			}
			SoulmateConnectionC2SPayload.setPlayerHeartBroken(player);
		}
	}

}

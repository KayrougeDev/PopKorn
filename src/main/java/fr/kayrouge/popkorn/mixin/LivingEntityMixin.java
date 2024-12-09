package fr.kayrouge.popkorn.mixin;

import fr.kayrouge.popkorn.registry.potion.PKEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

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

}

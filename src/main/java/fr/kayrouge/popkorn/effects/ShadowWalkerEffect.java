package fr.kayrouge.popkorn.effects;

import fr.kayrouge.popkorn.util.PlayerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectType;

public class ShadowWalkerEffect extends PKStatusEffect{

	int tickCounter = 0;

	public ShadowWalkerEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public void onApplied(AttributeContainer attributes, int amplifier) {
		super.onApplied(attributes, amplifier);
		tickCounter = 10;
	}

	@Override
	public boolean shouldApplyUpdateEffect(int tick, int amplifier) {
		return true;
	}

	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		tickCounter++;

		if (tickCounter >= 5) {
			int light = entity.getWorld().getLightLevel(entity.getBlockPos());

			switch (amplifier) {
				case 0:
					PlayerUtil.totallyDisappear(entity, light < 8);
					break;

				case 1:
					PlayerUtil.totallyDisappear(entity, light < 12);
					break;

				default:
					PlayerUtil.totallyDisappear(entity, true);
					break;
			}
			tickCounter = 0;
		}

		return true;
	}
}

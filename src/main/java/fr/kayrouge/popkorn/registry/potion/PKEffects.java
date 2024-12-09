package fr.kayrouge.popkorn.registry.potion;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.effects.ShadowWalkerEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PKEffects {

	public static final Holder<StatusEffect> SHADOW_WALKER;

	private static Holder<StatusEffect> register(String id, StatusEffect entry) {
		return Registry.registerHolder(Registries.STATUS_EFFECT, Identifier.of(PopKorn.MODID, id), entry);
	}

	static {
		SHADOW_WALKER = register("shadow_walker", new ShadowWalkerEffect(StatusEffectType.NEUTRAL, 5242987));
	}
}

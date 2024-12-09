package fr.kayrouge.popkorn.registry.potion;

import fr.kayrouge.popkorn.PopKorn;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PKPotions {

	public static final Holder<Potion> SHADOW_WALKER;
	public static final Holder<Potion> STRONG_SHADOW_WALKER;
	public static final Holder<Potion> LIGHT_WALKER;


	private static Holder<Potion> registerPotion(String path, Potion entry) {
		return Registry.registerHolder(Registries.POTION, Identifier.of(PopKorn.MODID, path), entry);
	}

	static {
		SHADOW_WALKER = registerPotion("shadow_walker", new Potion("shadow_walker", new StatusEffectInstance(PKEffects.SHADOW_WALKER, 9600, 0)));
		STRONG_SHADOW_WALKER = registerPotion("strong_shadow_walker", new Potion("shadow_walker", new StatusEffectInstance(PKEffects.SHADOW_WALKER, 3600, 1)));
		LIGHT_WALKER = registerPotion("light_walker", new Potion("light_walker", new StatusEffectInstance(PKEffects.SHADOW_WALKER, 3600, 2)));
	}

	public static void initialize() {
	}
}

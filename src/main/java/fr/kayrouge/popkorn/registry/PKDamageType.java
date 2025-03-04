package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PKDamageType {

	public static final RegistryKey<DamageType> HEART_BROKEN_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(PopKorn.MODID, "heart_broken_damage"));


	public static DamageType getDamageType(RegistryKey<DamageType> type, World world) {
		return world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).get(type);
	}

	public static DamageSource getDamageSource(World world, RegistryKey<DamageType> type) {
		Registry<DamageType> damageTypesRegistry = world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE);
		return new DamageSource(damageTypesRegistry.wrapAsHolder(damageTypesRegistry.get(type)));
	}

	public static DamageSource getDamageSource(World world, Entity entity, RegistryKey<DamageType> type) {
		Registry<DamageType> damageTypesRegistry = world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE);
		return new DamageSource(damageTypesRegistry.wrapAsHolder(damageTypesRegistry.get(type)), entity);
	}
}

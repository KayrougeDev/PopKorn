package fr.kayrouge.popkorn.registry;

import fr.kayrouge.popkorn.PopKorn;
import fr.kayrouge.popkorn.component.EntityDataComponent;
import fr.kayrouge.popkorn.component.PlayerDataComponent;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public final class PKComponents implements EntityComponentInitializer {

	public static final ComponentKey<PlayerDataComponent> PLAYER_DATA =
		ComponentRegistry.getOrCreate(Identifier.of(PopKorn.MODID, "player_data"), PlayerDataComponent.class);

	public static final ComponentKey<EntityDataComponent> ENTITY_DATA =
		ComponentRegistry.getOrCreate(Identifier.of(PopKorn.MODID, "entity_data"), EntityDataComponent.class);


	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(PLAYER_DATA, PlayerDataComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerFor(Entity.class, ENTITY_DATA, EntityDataComponent::new);
	}
}

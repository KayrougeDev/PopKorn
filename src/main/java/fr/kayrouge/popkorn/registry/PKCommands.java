package fr.kayrouge.popkorn.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import fr.kayrouge.popkorn.server.manager.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PKCommands {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("upabilities")
			.executes(context -> {
				context.getSource().getWorld().getPlayers().forEach(player -> {
					if(player instanceof ServerPlayerEntity) {
						PlayerManager.initPlayerAbilities(player);
						PlayerManager.updateClient(player);
					}
				});
				context.getSource().sendFeedback(() -> Text.literal("Updated !"), false);
				return Command.SINGLE_SUCCESS;
			}));
	}

}

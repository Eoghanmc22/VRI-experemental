package net.minestom.vanilla.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.vanilla.entity.VanillaEntities;

/**
 * Command that displays a player action
 */
public class MeCommand extends Command {

	public MeCommand() {
		super("me");

		setDefaultExecutor(this::usage);

		Argument message = ArgumentType.StringArray("message");

		addSyntax(this::execute, message);
	}

	private void usage(CommandSender player, Arguments arguments) {
		player.sendMessage("Usage: /me <message>");
	}

	private void execute(CommandSender player, Arguments arguments) {
			VanillaEntities.PIG.getEntityFunction().apply(player.asPlayer().getPosition().clone(),player.asPlayer().getInstance());
		String[] messageParts = arguments.getStringArray("message");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < messageParts.length; i++) {
			if (i != 0) {
				builder.append(" ");
			}
			builder.append(messageParts[i]);
		}
		String message = builder.toString();
		MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(p -> {
			p.sendMessage(" * " + ((Player) player).getUsername() + " " + message);
		});
	}

	private boolean isAllowed(CommandSender player) {
		return player.isPlayer(); // TODO: permissions
	}

}


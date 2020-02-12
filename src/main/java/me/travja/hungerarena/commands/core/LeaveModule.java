package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.travja.hungerarena.managers.MessageManager.sendMessage;

public class LeaveModule extends CommandModule {

	public LeaveModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
		super(parent, name, permission, permissionMessage, usage, alias);
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if(GameManager.isPlaying(p)) {
			Game game = GameManager.getGame(p);
			game.removePlayer(p);
			sendMessage(p, "&aYou have left &3"+game.getName());
		} else
			sendMessage(p, "&cYou aren't playing in a game!");
		return true;
	}

}
package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.resources.Game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LeaveModule extends CommandModule {

	public LeaveModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
		super(parent, name, permission, permissionMessage, usage, alias);
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cYou're silly! Thinking that the console can leave a game...");
			return true;
		}
		Player p = (Player) sender;
		if(GameManager.isPlaying(p)) {
			Game game = GameManager.getGame(p);
			game.removePlayer(p);
			p.sendMessage(Main.tag+"§aYou have left §3"+game.getName());
		} else
			p.sendMessage("§cYou aren't playing in a game!");
		return true;
	}

}
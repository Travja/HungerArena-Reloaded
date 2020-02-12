package me.travja.hungerarena.commands.modules;

import java.util.ArrayList;
import java.util.Arrays;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.resources.Game;
import me.travja.hungerarena.commands.CommandModule;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TPCommand extends CommandModule {

	public TPCommand(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
		super(parent, name, permission, permissionMessage, usage, alias);
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cThis command can only be run in game!");
			return true;
		}
		if(args.length==1)
			return false;
		else {
			Player p = (Player) sender;
			Player target = Bukkit.getPlayer(args[1]);
			if(target== null) {
				p.sendMessage("§cThat player isn't online!");
				return true;
			}
			if(GameManager.isPlaying(p)) {
				p.sendMessage("§cYou can't spectate another game while you're playing!");
				return true;
			}
			if(GameManager.isPlaying(target)) {
				Game game = GameManager.getGame(target);
				if(GameManager.getGame(p)!= null)
					GameManager.getGame(p).removeSpectator(p);
				game.addSpectator(p);
				p.sendMessage(Main.tag+"§7Teleporting...");
				p.teleport(target);
			} else {
				p.sendMessage("§cThat player isn't in a game!");
			}
		}
		return true;
	}

}
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
import org.bukkit.entity.Player;

public class KickModule extends CommandModule {

	public KickModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
		super(parent, name, permission, permissionMessage, usage, alias);
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length==1)
			return false;
		else {
			Player target = Bukkit.getPlayer(args[1]);
			if(target!= null && GameManager.isPlaying(target)) {
				Game game = GameManager.getGame(target);
				game.removePlayer(target);
				target.sendMessage(Main.tag+"§cYou have been kicked from the game!");
				sender.sendMessage("§a"+target.getName()+" kicked from the game!");
			}
		}
		return true;
	}

}
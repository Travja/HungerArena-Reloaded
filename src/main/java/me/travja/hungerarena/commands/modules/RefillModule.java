package me.travja.hungerarena.commands.modules;

import java.util.ArrayList;
import java.util.Arrays;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.resources.Game;
import me.travja.hungerarena.commands.CommandModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RefillCommand extends CommandModule {

	public RefillCommand(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
		super(parent, name, permission, permissionMessage, usage, alias);
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args.length==1) {
			for(Game game: GameManager.getGames()) {
				game.refillChests();
			}
			sender.sendMessage(Main.tag+"§aAll games' chests have been refilled!");
		} else {
			String name = args[1];
			if(GameManager.isGame(name)) {
				Game game = GameManager.getGame(name);
				game.refillChests();
				sender.sendMessage(Main.tag+"§aChests refilled for §3"+game.getName());
			}
		}
		return true;
	}

}
package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cThis can only be run in game!");
			return true;
		}
		Player p = (Player) sender;
		if(args.length>= 2) {
			String name = args[1];
			if(GameManager.isGame(name)) {
				Game game = GameManager.getGame(name);
				game.setSpawn(p.getLocation());
				p.sendMessage(Main.tag+"§aSpawn set for §3"+game.getName());
			} else
				p.sendMessage("§cA game with that name doesn't exist!");
		}
		return true;
	}

	@Override
	public String getName() {
		return "setspawn";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("ss"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.setspawn";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha setspawn [name]";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}
	
	@Override
	public boolean isIndependent() {
		return false;
	}

}
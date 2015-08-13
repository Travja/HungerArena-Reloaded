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

public class DisableCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cYou're silly! Thinking that the console can join a game...");
			return true;
		}
		if(args.length>= 2) {
			Player p = (Player) sender;
			String name = args[1];
			if(GameManager.isGame(name)) {
				Game game = GameManager.getGame(name);
				game.disable();
				p.sendMessage(Main.tag+"§aYou have disabled §3"+game.getName());
			} else
				p.sendMessage("§cA game with that name doesn't exist!");
			return true;
		} else if(args.length== 1) {
			for(Game game: GameManager.getGames()) {
				game.disable();
			}
			sender.sendMessage(Main.tag+"§aAll games have been disabled!");
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "disable";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("d"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.manage";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha disable <name>";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}

}
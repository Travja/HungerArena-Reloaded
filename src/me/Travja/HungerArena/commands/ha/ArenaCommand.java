package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class ArenaCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cThis can only be run in game!");
			return true;
		}
		if(args.length== 1)
			return false;
		if(args.length>= 3) {
			Player p = (Player) sender;
			String action = args[1];
			String name = args[2];

			if(action.equalsIgnoreCase("create")) {
				if(GameManager.isGame(name)) {
					p.sendMessage("§cA game with that name already exists!");
					return true;
				}

				WorldEditPlugin we = Main.we;
				Selection selection = we.getSelection(p);

				if(selection!= null) {
					Location min = selection.getMinimumPoint();
					Location max = selection.getMaximumPoint();
					Game game = new Game(name, min, max);
					GameManager.addGame(game);
					p.sendMessage(Main.tag+"§aNew game called §3"+name+"§a created!");
				} else
					p.sendMessage("§cPlease make a selection first!");
			} else if(action.equalsIgnoreCase("delete")) {
				if(GameManager.isGame(name)) {
					Game game = GameManager.getGame(name);
					game.delete();
					GameManager.removeGame(game);
					p.sendMessage(Main.tag+name+"§a deleted!");
				} else {
					p.sendMessage("§cA game with that name could not be found!");
				}
			}



		}
		return true;
	}

	@Override
	public String getName() {
		return "arena";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("a"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.arena";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha arena [create|delete] [Name]";
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
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

public class LeaveCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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

	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("l"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.join";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha leave";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}

}
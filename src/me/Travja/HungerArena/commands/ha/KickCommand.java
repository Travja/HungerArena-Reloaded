package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements SubcommandInterface {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("k"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.kick";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha kick [player]";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}

}
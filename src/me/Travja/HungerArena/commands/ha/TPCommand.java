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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements SubcommandInterface {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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

	@Override
	public String getName() {
		return "tp";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("teleport"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.tp";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha tp [player]";
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
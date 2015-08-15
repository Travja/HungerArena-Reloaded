package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.Resources.Game.State;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cYou're silly! Thinking that the console can join a game...");
			return true;
		}
		Player p = (Player) sender;
		if(args.length>= 2) {
			String name = args[1];
			if(GameManager.isGame(name)) {
				if(!GameManager.isPlaying(p)) {
					Game game = GameManager.getGame(name);
					if((game.getState()== State.WAITING || game.getState()== State.STARTING) && game.getPlayers().size()< game.getMaxPlayers()) {
						game.addPlayer(p);
						p.sendMessage(Main.tag+"§aYou have joined §3"+game.getName());
					} else
						p.sendMessage("§cYou can't join that game right now!");
				} else
					p.sendMessage("§cYou are already playing in a game!");
			} else
				p.sendMessage("§cA game with that name doesn't exist!");
		} else {
			p.sendMessage("§aRight click a game here to join!");
			p.performCommand("ha list");
		}
		return true;
	}

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("j"));
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
		return "/ha join [name]";
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
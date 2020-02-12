package me.travja.hungerarena.commands.modules;

import java.util.ArrayList;
import java.util.Arrays;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.resources.Game;
import me.travja.hungerarena.resources.Game.State;
import me.travja.hungerarena.commands.CommandModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandModule {

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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
	public CommandModule getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}
	
	@Override
	public boolean isIndependent() {
		return false;
	}

}
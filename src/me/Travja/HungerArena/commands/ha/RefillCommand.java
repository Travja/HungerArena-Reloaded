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

public class RefillCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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

	@Override
	public String getName() {
		return "refill";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("r"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.refill";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha refill <name>";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}

}
package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		ChatColor c = ChatColor.YELLOW;
		sender.sendMessage(ChatColor.GREEN + "----HungerArena Help----");
		sender.sendMessage(c + "/ha - Displays author message!");
		sender.sendMessage(c + "/ha arena [create|delete] [name] - Creates or deletes an arena.");
		sender.sendMessage(c + "/ha help - Displays this screen!");
		sender.sendMessage(c + "/ha join [name] - Joins the game with the given name!");
		sender.sendMessage(c + "/ha leave - Makes you leave the game!");
		sender.sendMessage(c + "/ha disable <name> - Prevents anyone from joining that arena! Leaving the name blank will disable all games!");
		sender.sendMessage(c + "/ha enable <name> - Opens the game allowing people to join! Leaving the name blank will enable all games!");
		sender.sendMessage(c + "/ha refill <name> - Refills all chests! Leaving the name blank will refill all games!");
		sender.sendMessage(c + "/ha kick [player] - Kicks a player from the arena!");
		
		
		sender.sendMessage(c + "/ha start [name] - Unfreezes tributes allowing them to fight!");
		
		
		
		
		
		
		sender.sendMessage(c + "/sponsor [Player] [ItemID] [Amount] - Lets you sponsor someone!");
		sender.sendMessage(c + "/startpoint [1,2,3,4,etc] [1,2,3,4,etc] - Sets the starting points of tributes in a specific arena!");
		
		sender.sendMessage(c + "/ha list (1,2,3,4,etc) - Shows a list of players in the game and their health! Numbers are optional.");
		sender.sendMessage(c + "/ha ready - Votes for the game to start!");
		sender.sendMessage(c + "/ha reload - Reloads the config!");
		sender.sendMessage(c + "/ha restart (1,2,3,4,etc) - Restarts the game! Numbers are optional");
		sender.sendMessage(c + "/ha rlist (1,2,3,4,etc) - See who's ready! Numbers are optional");
		sender.sendMessage(c + "/ha setspawn - Sets the spawn for dead tributes!");
		sender.sendMessage(c + "/ha tp [player] - Teleports you to a tribute!");
		sender.sendMessage(c + "/ha watch [1,2,3,4,etc] - Lets you watch the tributes!");
		sender.sendMessage(c + "/ha warpall [1,2,3,4,etc] - Warps all tribute into position!");
		sender.sendMessage(ChatColor.GREEN + "----------------------");
		return true;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("h"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.help";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha help";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}

}
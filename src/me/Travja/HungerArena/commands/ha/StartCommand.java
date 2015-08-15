package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StartCommand implements SubcommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		return true;
	}

	@Override
	public String getName() {
		return "start";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("s"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.start";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha start [Arena]";
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
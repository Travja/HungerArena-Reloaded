package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.commands.CommandInterface;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandCore implements CommandInterface {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage("§6HungerArena by §2Travja!\n§aType /ha help for help!");
		return true;
	}

	@Override
	public String getName() {
		return "ha";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("hungerarena"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.basic";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha [start|create...]";
	}
	
	

}

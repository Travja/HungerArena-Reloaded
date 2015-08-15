package me.Travja.HungerArena.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);
	
	public String getName();
	
	public ArrayList<String> getAliases();
	
	public String getPermission();
	
	public String getPermissionMessage();
	
	public String getUsage();
	
	public boolean isIndependent();
	
}

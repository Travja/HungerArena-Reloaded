package me.Travja.HungerArena.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCore implements CommandExecutor {
	
	String tag = "§3[HungerArena] ";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name,
			String[] args) {
		if(args.length>= 1){
			
		}else{
			sender.sendMessage("§6HungerArena by §2Travja!\n§aType /ha help for help!");
		}
		return false;
	}

}

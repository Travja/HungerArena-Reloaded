package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.commands.CommandModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StartModule extends CommandModule {

	public StartModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
		super(parent, name, permission, permissionMessage, usage, alias);
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		//TODO Implement
		return true;
	}
	
}
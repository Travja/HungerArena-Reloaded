package me.Travja.HungerArena.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandHandler implements CommandExecutor {

	private static HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();
	private static HashMap<String, SubcommandInterface> subcommands = new HashMap<String, SubcommandInterface>();

	public void register(CommandInterface cmd) {
		if(cmd instanceof CommandInterface)
			commands.put(cmd.getName(), cmd);
	}

	public void register(SubcommandInterface cmd) {
		if(cmd instanceof SubcommandInterface)
			subcommands.put(cmd.getName(), cmd);
	}

	public boolean exists(String name) {
		for(String ss: commands.keySet()) {
			CommandInterface command = commands.get(ss);
			if(command.getName().equals(name.toLowerCase()) || (command.getAliases()!= null && command.getAliases().contains(name.toLowerCase())))
				return true;
		}
		return false;
	}

	public boolean exists(String name, String arg) {
		for(String ss: subcommands.keySet()) {
			SubcommandInterface sub = subcommands.get(ss);
			if(sub.getName().equals(arg.toLowerCase()) || (sub.getAliases()!= null && sub.getAliases().contains(arg.toLowerCase())))
				if(sub.getParent().getName().equals(name.toLowerCase()) || (sub.getParent().getAliases()!= null && sub.getParent().getAliases().contains(name.toLowerCase())))
					return true;
		}
		return false;
	}



	public CommandInterface getExecutor(String name) {
		for(String ss: commands.keySet()) {
			CommandInterface command = commands.get(ss);
			if(command.getName().equals(name.toLowerCase()) || (command.getAliases()!= null && command.getAliases().contains(name.toLowerCase())))
				return command;
		}
		return null;
	}

	public SubcommandInterface getExecutor(String name, String arg) {
		for(String ss: subcommands.keySet()) {
			SubcommandInterface sub = subcommands.get(ss);
			if(sub.getName().equals(arg.toLowerCase()) || (sub.getAliases()!= null && sub.getAliases().contains(arg.toLowerCase())))
				if(sub.getParent().getName().equals(name.toLowerCase()) || (sub.getParent().getAliases()!= null && sub.getParent().getAliases().contains(name.toLowerCase())))
					return sub;
		}
		return null;
	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(exists(commandLabel)) {
			CommandInterface command = getExecutor(commandLabel);
			if(args.length== 0 || command.isIndependent()) {
				if(sender instanceof ConsoleCommandSender || sender.hasPermission(command.getPermission())) {
					boolean success = command.onCommand(sender, cmd, commandLabel, args);
					if(!success)
						sender.sendMessage(command.getUsage());
				}else
					sender.sendMessage(command.getPermissionMessage());
			} else {
				if(exists(commandLabel, args[0])) {
					SubcommandInterface subcommand = getExecutor(commandLabel, args[0]);
					if(sender instanceof ConsoleCommandSender || sender.hasPermission(subcommand.getPermission())) {
						boolean success = subcommand.onCommand(sender, cmd, commandLabel, args);
						if(!success)
							sender.sendMessage(subcommand.getUsage());
					} else
						sender.sendMessage(subcommand.getPermissionMessage());
				} else
					sender.sendMessage(command.getUsage());
			}
		}
		return true;
	}

}

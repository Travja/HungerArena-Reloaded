package me.travja.hungerarena.commands;

import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.modules.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandHandler implements CommandExecutor {

    private ArrayList<CommandModule> modules = new ArrayList<>();

    public void init() {
        CommandModule core = new CoreModule(null, "ha", "hungerarena.basic", ChatColor.RED + "You don't have permission for that!", "/<command> [start|create...]", "hungerarena");
        modules.add(core);
		modules.add(new ArenaModule());
		modules.add(new HelpCommand(core, "help", "hungerarena.help", ChatColor.RED + "You don't have permission for that!", "/<command> help", "h"));
		modules.add(new StartCommand());
		modules.add(new JoinCommand());
		modules.add(new LeaveCommand());
		modules.add(new RefillCommand());
		modules.add(new KickCommand());
		modules.add(new TPCommand());
		modules.add(new SetSpawnCommand());

        ListCommand lcom = new ListCommand();
		Bukkit.getServer().getPluginManager().registerEvents(lcom, Main.self);
		modules.add(lcom);

        ManageCommand mcom = new ManageCommand();
		Bukkit.getServer().getPluginManager().registerEvents(mcom, Main.self);
		modules.add(mcom);

        SpawnsCommand spawns = new SpawnsCommand(null, "startpoint", "hungerarena.startpoint", ChatColor.RED + "You don't have permission for that!", "/<command> [name|cancel] <number>", "sp");
		Bukkit.getServer().getPluginManager().registerEvents(spawns, Main.self);
		modules.add(spawns);
    }

    public boolean exists(String name) {
		for (CommandModule module : modules) {
			if (module.getAliases().contains(name.toLowerCase()))
				return true;
		}

        return false;
    }

    public CommandModule getExecutor(String name) {
        for (CommandModule module : modules) {
            if (module.getAliases().contains(name.toLowerCase()))
                return module;
        }

        return null;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (exists(label)) {
            CommandModule command = getExecutor(label);
            if (command.requirePlayer() && !(sender instanceof Player)) {
                sender.sendMessage("This can only be run in game!");
                return true;
            }

            boolean success = command.execute(sender, cmd, label, args);
            if (sender.hasPermission(command.getPermission())) {
                if (!success)
                    sender.sendMessage(command.getUsage().replace("<command>", label));
            } else
                sender.sendMessage(command.getPermissionMessage());


        }
        return true;
    }

}

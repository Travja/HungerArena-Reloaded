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

public class CommandHandler implements CommandExecutor {

    private ArrayList<CommandModule> modules = new ArrayList<>();

    public void init() {
        CommandModule core = new CoreModule(null, "ha", "hungerarena.basic", ChatColor.RED + "You don't have permission for that!", "/<command> <start|create...>", "hungerarena");
        modules.add(core);

        SpawnsModule spawns = new SpawnsModule(null, "startpoint", "hungerarena.startpoint", ChatColor.RED + "You don't have permission for that!", "/<command> <name|cancel> [number]", "sp");
        Bukkit.getServer().getPluginManager().registerEvents(spawns, Main.self);
        modules.add(spawns);

        addChildCommands(core);
    }

    private void addChildCommands(CommandModule core) {
        new ArenaModule(core, "arena", "hungerarena.arena", ChatColor.RED + "You don't have permission for that!", "/<command> arena <create|delete> <name>", "a").setRequirePlayer(true);
        new HelpModule(core, "help", "hungerarena.help", ChatColor.RED + "You don't have permission for that!", "/<command> help", "h");
        new StartModule(core, "start", "hungerarena.start", ChatColor.RED + "You don't have permission for that!", "/<command> start <arena>", "s");
        new JoinModule(core, "join", "hungerarena.join", ChatColor.RED + "You don't have permission for that!", "/<command> join [name]", "j").setRequirePlayer(true);
        new LeaveModule(core, "leave", "hungerarena.join", ChatColor.RED + "You don't have permission for that!", "/<command> leave", "l").setRequirePlayer(true);
        new RefillModule(core, "refill", "hungerarena.refill", ChatColor.RED + "You don't have permission for that!", "/<command> refill [name]", "r");
        new KickModule(core, "kick", "hungerarena.kick", ChatColor.RED + "You don't have permission for that!", "/<command> kick <player>", "k");
        new TPModule(core, "tp", "hungerarena.tp", ChatColor.RED + "You don't have permission for that!", "/<command> tp <player>", "teleport").setRequirePlayer(true);
        new SetSpawnModule(core, "setspawn", "hungerarena.setspawn", ChatColor.RED + "You don't have permision for that!", "/<command> setspawn <name>", "ss").setRequirePlayer(true);

        ListModule lcom = new ListModule(core, "list", "hungerarena.list", ChatColor.RED + "You don't have permission for that!", "/<command> list", "ls"); //TODO Allow these in Console v
        Bukkit.getServer().getPluginManager().registerEvents(lcom, Main.self);

        ManageModule mcom = new ManageModule(core, "manage", "hungerarena.manage", ChatColor.RED + "You don't have permission for that!", "/<command> manage", "man");
        Bukkit.getServer().getPluginManager().registerEvents(mcom, Main.self);
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

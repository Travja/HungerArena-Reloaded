package me.travja.hungerarena.commands;

import me.travja.hungerarena.commands.core.*;
import me.travja.hungerarena.commands.spawns.SpawnsModule;
import me.travja.hungerarena.commands.sponsor.SponsorModule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandHandler implements CommandExecutor {

    private ArrayList<CommandModule> modules = new ArrayList<>();
    private static final String NO_PERM = ChatColor.RED + "You don't have permission for that!";

    public void init() {
        CommandModule core = new CoreModule(null, "ha", "hungerarena.basic", NO_PERM, "/<command> <start|create...>", "hungerarena");
        modules.add(core);
        modules.add(new SpawnsModule(null, "startpoint", "hungerarena.startpoint", NO_PERM, "/<command> <name|cancel> [number]", "sp").setRequirePlayer(true));
        modules.add(new SponsorModule(null, "sponsor", "hungerarena.sponsor", NO_PERM, "/<command> <player> <item> [amount]"));

        addChildCommands(core);
    }

    private void addChildCommands(CommandModule core) {
        new ArenaModule(core, "arena", "hungerarena.arena", NO_PERM, "/<command> arena <create|delete> <name>", "a").setRequirePlayer(true);
        new HelpModule(core, "help", "hungerarena.help", NO_PERM, "/<command> help", "h");
        new StartModule(core, "start", "hungerarena.start", NO_PERM, "/<command> start <arena>", "s");
        new JoinModule(core, "join", "hungerarena.join", NO_PERM, "/<command> join [name]", "j").setRequirePlayer(true);
        new LeaveModule(core, "leave", "hungerarena.join", NO_PERM, "/<command> leave", "l").setRequirePlayer(true);
        new RefillModule(core, "refill", "hungerarena.refill", NO_PERM, "/<command> refill [name]", "r");
        new KickModule(core, "kick", "hungerarena.kick", NO_PERM, "/<command> kick <player>", "k");
        new TPModule(core, "tp", "hungerarena.tp", NO_PERM, "/<command> tp <player>", "teleport").setRequirePlayer(true);
        new SetSpawnModule(core, "setspawn", "hungerarena.setspawn", NO_PERM, "/<command> setspawn <name>", "ss").setRequirePlayer(true);
        new ListModule(core, "list", "hungerarena.list", NO_PERM, "/<command> list", "ls"); //TODO Allow these in Console v
        new ManageModule(core, "manage", "hungerarena.manage", NO_PERM, "/<command> manage", "man");
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

            if (sender.hasPermission(command.getPermission())) {
                boolean success = command.execute(sender, cmd, label, args);
                if (!success)
                    sender.sendMessage(command.getUsage().replace("<command>", label));
            } else
                sender.sendMessage(command.getPermissionMessage());
        }
        return true;
    }

}

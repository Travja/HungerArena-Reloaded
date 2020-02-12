package me.travja.hungerarena.commands.modules;

import me.travja.hungerarena.commands.CommandModule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CoreModule extends CommandModule {

    public CoreModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("§6HungerArena by §2Travja!\n§aType /ha help for help!");
        return true;
    }

}

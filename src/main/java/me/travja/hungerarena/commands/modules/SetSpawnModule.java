package me.travja.hungerarena.commands.modules;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.resources.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetSpawnModule extends CommandModule {

    public SetSpawnModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cThis can only be run in game!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length >= 2) {
            String name = args[1];
            if (GameManager.isGame(name)) {
                Game game = GameManager.getGame(name);
                game.setSpawn(p.getLocation());
                p.sendMessage(Main.tag + "§aSpawn set for §3" + game.getName());
            } else
                p.sendMessage("§cA game with that name doesn't exist!");
        }
        return true;
    }

}
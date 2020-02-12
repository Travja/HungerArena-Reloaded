package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.managers.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static me.travja.hungerarena.managers.MessageManager.sendMessage;

public class SetSpawnModule extends CommandModule {

    public SetSpawnModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, "&cThis can only be run in game!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length >= 2) {
            String name = args[1];
            if (GameManager.isGame(name)) {
                Game game = GameManager.getGame(name);
                game.setSpawn(p.getLocation());
                sendMessage(p, "&aSpawn set for &3" + game.getName());
            } else
                sendMessage(p, "&cA game with that name doesn't exist!");
        }
        return true;
    }

}
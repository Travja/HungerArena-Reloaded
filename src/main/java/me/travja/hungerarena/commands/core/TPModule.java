package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TPModule extends CommandModule {

    public TPModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 1)
            return false;
        else {
            Player p = (Player) sender;
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageManager.sendMessage(p, "&cThat player isn't online!");
                return true;
            }
            if (GameManager.isPlaying(p)) {
                MessageManager.sendMessage(p, "&cYou can't spectate another game while you're playing!");
                return true;
            }
            if (GameManager.isPlaying(target)) {
                Game game = GameManager.getGame(target);
                if (GameManager.getGame(p) != null)
                    GameManager.getGame(p).removeSpectator(p);
                game.addSpectator(p);
                MessageManager.sendMessage(p, "&7Teleporting...");
                p.teleport(target);
            } else {
                MessageManager.sendMessage(p, "&cThat player isn't in a game!");
            }
        }
        return true;
    }

}
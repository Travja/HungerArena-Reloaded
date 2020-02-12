package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.travja.hungerarena.managers.MessageManager.sendMessage;

public class KickModule extends CommandModule {

    public KickModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 1)
            return false;
        else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null && GameManager.isPlaying(target)) {
                Game game = GameManager.getGame(target);
                game.removePlayer(target);
                sendMessage(target, "&cYou have been kicked from the game!");
                sendMessage(sender, "&a" + target.getName() + " kicked from the game!");
            }
        }
        return true;
    }

}
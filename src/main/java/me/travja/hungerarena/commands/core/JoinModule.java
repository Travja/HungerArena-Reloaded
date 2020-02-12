package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.game.Game.State;
import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class JoinModule extends CommandModule {

    public JoinModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            MessageManager.sendMessage(sender, "&cYou're silly! Thinking that the console can join a game...");
            return true;
        }
        Player p = (Player) sender;
        if (args.length >= 2) {
            String name = args[1];
            if (GameManager.isGame(name)) {
                if (!GameManager.isPlaying(p)) {
                    Game game = GameManager.getGame(name);
                    if ((game.getState() == State.WAITING || game.getState() == State.STARTING) && game.getPlayers().size() < game.getMaxPlayers()) {
                        game.addPlayer(p);
                        MessageManager.sendMessage(p, "&aYou have joined §3" + game.getName());
                    } else
                        MessageManager.sendMessage(p, "&cYou can't join that game right now!");
                } else
                    MessageManager.sendMessage(p, "&cYou are already playing in a game!");
            } else
                MessageManager.sendMessage(p, "&cA game with that name doesn't exist!");
        } else {
            MessageManager.sendMessage(p, "&aRight click a game here to join!");
            p.performCommand("ha list");
        }
        return true;
    }

}
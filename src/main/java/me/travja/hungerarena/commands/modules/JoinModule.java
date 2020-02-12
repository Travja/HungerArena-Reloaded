package me.travja.hungerarena.commands.modules;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.resources.Game;
import me.travja.hungerarena.resources.Game.State;
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
            sender.sendMessage("§cYou're silly! Thinking that the console can join a game...");
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
                        p.sendMessage(Main.tag + "§aYou have joined §3" + game.getName());
                    } else
                        p.sendMessage("§cYou can't join that game right now!");
                } else
                    p.sendMessage("§cYou are already playing in a game!");
            } else
                p.sendMessage("§cA game with that name doesn't exist!");
        } else {
            p.sendMessage("§aRight click a game here to join!");
            p.performCommand("ha list");
        }
        return true;
    }

}
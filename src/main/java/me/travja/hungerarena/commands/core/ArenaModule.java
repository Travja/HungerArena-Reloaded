package me.travja.hungerarena.commands.core;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ArenaModule extends CommandModule {

    public ArenaModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 1)
            return false;
        if (args.length >= 3) {
            Player p = (Player) sender;
            String action = args[1];
            String name = args[2];

            if (action.equalsIgnoreCase("create")) {
                if (GameManager.isGame(name)) {
                    MessageManager.sendMessage(p, "&cA game with that name already exists!");
                    return true;
                }

                WorldEditPlugin we = Main.we;
                LocalSession session = we.getSession(p);
                World world = session.getSelectionWorld();
                if (!p.getWorld().getName().equals(world.getName())) {
                    MessageManager.sendMessage(p, ChatColor.RED + "Please make a selection in this world.");
                    return true;
                }
                try {
                    Region selection = session.getSelection(world);

                    if (selection != null) {
                        BlockVector3 minB = selection.getMinimumPoint();
                        BlockVector3 maxB = selection.getMaximumPoint();
                        Location min = new Location(p.getWorld(), minB.getBlockX(), minB.getBlockY(), minB.getBlockZ());
                        Location max = new Location(p.getWorld(), maxB.getBlockX(), maxB.getBlockY(), maxB.getBlockZ());
                        ;
                        Game game = new Game(name, min, max);
                        GameManager.addGame(game);
                        MessageManager.sendMessage(p, "&aNew game called §3" + name + "§a created!");
                    } else
                        MessageManager.sendMessage(p, "&cPlease make a selection first!");
                } catch (IncompleteRegionException e) {
                    MessageManager.sendMessage(p, "&cPlease make a selection first!");
                }
            } else if (action.equalsIgnoreCase("delete")) {
                if (GameManager.isGame(name)) {
                    Game game = GameManager.getGame(name);
                    game.delete();
                    GameManager.removeGame(game);
                    MessageManager.sendMessage(p, name + "&a deleted!");
                } else {
                    MessageManager.sendMessage(p, "&cA game with that name could not be found!");
                }
            }


        }
        return true;
    }

}
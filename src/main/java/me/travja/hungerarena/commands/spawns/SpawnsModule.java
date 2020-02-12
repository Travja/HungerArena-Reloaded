package me.travja.hungerarena.commands.spawns;

import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.managers.GameManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

import static me.travja.hungerarena.managers.MessageManager.sendMessage;

public class SpawnsModule extends CommandModule implements Listener {

    private Material tool;
    private HashMap<UUID, Game> setters = new HashMap<>();

    public SpawnsModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
        setRequirePlayer(true);
        tool = Material.matchMaterial(Main.config.getString("spawnsTool"));
        if (tool == null)
            Main.log.warning("Could not find a tool from material '" + Main.config.getString("spawnsTool") + "'");
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0)
            return false;
        if (args[0].equalsIgnoreCase("cancel")) {
            setters.remove(p.getUniqueId());
            sendMessage(p, "&aNo longer setting spawn points");
            return true;
        }
        if (GameManager.isGame(args[0])) {
            Game game = GameManager.getGame(args[0]);
            if (args.length == 1) {
                if (tool != null) {
                    if (game.getMaxPlayers() != GameManager.getMaximumPlayers()) {
                        setters.put(p.getUniqueId(), game);
                        sendMessage(p, "&aBeing setting spawns for §3" + game.getName() + "§a starting from §3" + (game.getMaxPlayers() + 1));
                        sendMessage(p, "&aType §3/startpoint cancel §ato stop setting points!");
                    } else
                        sendMessage(p, "&cAll the spawns have been set for this arena! Use the full command to override current spawns.");
                } else
                    sendMessage(p, "&cThere is no spawns tool. Please use the full command!");
            } else {
                try {
                    int i = Integer.parseInt(args[1]);
                    game.setSpawn(i, p.getLocation());
                    sendMessage(p, "&aSpawn point &3" + i + "&a set for &3" + game.getName());
                } catch (Exception e) {
                    sendMessage(p, "&cYour second argument was not a number! Please use numbers!");
                }
            }
        } else
            sendMessage(p, "&cThat game doesn't exist!");
        return true;
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.hasItem()) {
            ItemStack item = event.getItem();
            if (item.getType() == tool && setters.containsKey(p.getUniqueId())) {
                Game game = setters.get(p.getUniqueId());
                Location l = event.getClickedBlock().getLocation();
                l.add(0.5, 1, 0.5);
                game.setSpawn(game.getMaxPlayers() + 1, l);
                sendMessage(p, "&aSpawn point &3" + game.getMaxPlayers() + " &aset for &3" + game.getName());
                if (game.getMaxPlayers() == GameManager.getMaximumPlayers())
                    setters.remove(p.getUniqueId());
            }
        }
    }

}

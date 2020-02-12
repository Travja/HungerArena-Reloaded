package me.travja.hungerarena.commands.modules;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.Main;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.resources.Game;
import me.travja.hungerarena.resources.Game.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class ListCommand extends CommandModule implements Listener {

    static String title = "§2[§3§lGames§2]";
    Inventory menu = null;

    public ListCommand(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
        getInventory();

        new BukkitRunnable() {
            public void run() {
                update();
                GameManager.updateGames();
            }
        }.runTaskTimer(Main.self, 20L, 20L);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ((Player) sender).openInventory(menu);
        return true;
    }


    @EventHandler
    public void click(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        String name = event.getView().getTitle();
        if (name.equals(title) && event.getRawSlot() != -999) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem() != null ? event.getCurrentItem() : event.getCursor() != null ? event.getCursor() : inv.getItem(event.getRawSlot());
            if (item.getType() == Material.MAGMA_CREAM) {
                p.closeInventory();
                return;
            }
            String gamename = ChatColor.stripColor(item.getItemMeta().getDisplayName().split(" ")[0]);
            Game game = GameManager.getGame(gamename);
            if (event.isRightClick()) {
                p.performCommand("ha join " + game.getName());
            } else if (event.isLeftClick()) {
                game.listPlayers(p);
            }
        }
    }

    int games = 0;

    public void update() {
        if (GameManager.getGames().size() != games)
            getInventory();
        ItemStack[] contents = menu.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() != Material.MAGMA_CREAM) {
                ItemMeta im = item.getItemMeta();
                String name = ChatColor.stripColor(im.getDisplayName().split(" ")[0]);
                Game game = GameManager.getGame(name);
                menu.setItem(i, getDisplayItem(game));
            }
        }
    }

    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(null, (int) Math.ceil((double) (GameManager.getGames().size() + 1) / 9) * 9, title);
        int i = 0;
        for (Game game : GameManager.getGames()) {
            inv.setItem(i, getDisplayItem(game));
            i++;
        }
        ItemStack exit = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta eim = exit.getItemMeta();
        eim.setDisplayName("§cExit");
        exit.setItemMeta(eim);
        inv.setItem(inv.getContents().length - 1, exit);
        games = GameManager.getGames().size();
        menu = inv;
        return inv;
    }

    public ItemStack getDisplayItem(Game game) {
        ItemStack item = new ItemStack(Material.GREEN_WOOL, game.getPlayers().size() > 0 ? game.getPlayers().size() : 1);
        String prefix;
        if (game.getState() == State.DISABLED ||
                game.getState() == State.INGAME ||
                game.getState() == State.STARTING ||
                game.getPlayers().size() == game.getMaxPlayers()) {
            item.setType(Material.RED_WOOL);
            prefix = ChatColor.RED + "";
        } else if (game.getState() == State.WAITING) {
            item.setType(Material.GREEN_WOOL);
            prefix = ChatColor.GREEN + "";
        } else if (game.getState() == State.RESTARTING) {
            item.setType(Material.ORANGE_WOOL);
            prefix = ChatColor.GOLD + "";
        } else {
            prefix = ChatColor.AQUA + "Unknown state ";
        }

        ItemMeta im = item.getItemMeta();
        im.setDisplayName(prefix + game.getName() + " - " + game.getState());
        im.setLore(new ArrayList<>(Arrays.asList("§7Players: " + game.getPlayers().size() + "/" + game.getMaxPlayers(),
                "§7Time: " + game.getTimeString(), "§8Right click to join or", "§8Left click to list players")));
        item.setItemMeta(im);

        return item;
    }

}
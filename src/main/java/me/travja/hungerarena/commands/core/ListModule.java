package me.travja.hungerarena.commands.core;

import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
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

public class ListModule extends CommandModule implements Listener {

    private final String title = "§2[§3§lGames§2]";

    public ListModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);

    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ((Player) sender).openInventory(GameManager.getInventory(title));
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

}
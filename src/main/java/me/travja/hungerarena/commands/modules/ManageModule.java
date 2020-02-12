package me.travja.hungerarena.commands.modules;

import me.travja.hungerarena.GameManager;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.resources.Game;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ManageModule extends CommandModule implements Listener {

    private final String title = "§0[§c§lManage§0]";

    public ManageModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cYou're silly! Thinking that the console can join a game...");
            return true;
        }
        Player p = (Player) sender;
        p.openInventory(GameManager.getInventory(title));
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
            if (event.isShiftClick()) {
                game.restart();
            } else if (event.isRightClick()) {
                game.disable();
            } else if (event.isLeftClick()) {
                game.enable();
            }
        }
    }

}
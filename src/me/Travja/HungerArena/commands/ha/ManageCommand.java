package me.Travja.HungerArena.commands.ha;

import java.util.ArrayList;
import java.util.Arrays;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.Resources.Game.State;
import me.Travja.HungerArena.commands.CommandInterface;
import me.Travja.HungerArena.commands.SubcommandInterface;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ManageCommand implements SubcommandInterface, Listener {

	String title = "§0[§c§lManage§0]";
	Inventory menu = null;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cYou're silly! Thinking that the console can join a game...");
			return true;
		}
		Player p = (Player) sender;
		p.openInventory(menu);
		return true;
	}


	@EventHandler
	public void click(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		String name = inv.getTitle();
		if(name.equals(title) && event.getRawSlot()!= -999) {
			event.setCancelled(true);
			ItemStack item = event.getCurrentItem()!= null ? event.getCurrentItem() : event.getCursor()!= null ? event.getCursor() : inv.getItem(event.getRawSlot());
			if(item.getType()== Material.MAGMA_CREAM) {
				p.closeInventory();
				return;
			}
			String gamename = ChatColor.stripColor(item.getItemMeta().getDisplayName().split(" ")[0]);
			Game game = GameManager.getGame(gamename);
			if(event.isShiftClick()) {
				game.restart();
			} else if(event.isRightClick()) {
				game.disable();
			} else if(event.isLeftClick()) {
				game.enable();
			}
		}
	}

	public void init() {
		menu = getInventory();

		new BukkitRunnable() {
			public void run() {
				update();
				GameManager.updateGames();
			}
		}.runTaskTimer(Main.self, 20L, 20L);
	}

	public void update() {
		if(GameManager.getGames().size()> menu.getContents().length)
			getInventory();
		for(ItemStack item: menu.getContents()) {
			if(item!= null && item.getType()!= Material.MAGMA_CREAM) {
				ItemMeta im = item.getItemMeta();
				String name = ChatColor.stripColor(im.getDisplayName().split(" ")[0]);
				Game game = GameManager.getGame(name);
				if(game.getState()== State.WAITING) {
					item.setDurability((short) 5);
					im.setDisplayName("§a"+game.getName()+" - "+game.getState());
				} else if(game.getState()== State.RESTARTING) {
					item.setDurability((short) 1);
					im.setDisplayName("§6"+game.getName()+" - "+game.getState());
				} else {
					item.setDurability((short) 14);
					im.setDisplayName("§c"+game.getName()+" - "+game.getState());
				}
				im.setLore(new ArrayList<String>(Arrays.asList("§7Players: "+game.getPlayers().size()+"/"+GameManager.getMaximumPlayers(),
						"§7Time: "+game.getTimeString(), "§8Right click to close,", "§8Left click to open or", "§8Shift click to restart")));
				item.setItemMeta(im);
			}
		}
	}

	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, (int) Math.ceil((double) (GameManager.getGames().size()+1)/9)*9, title);
		int i = 0;
		for(Game game: GameManager.getGames()) {
			ItemStack item = new ItemStack(Material.WOOL, game.getPlayers().size()> 0 ? game.getPlayers().size() : 1);
			ItemMeta im = item.getItemMeta();
			if(game.getState()== State.WAITING) {
				item.setDurability((short) 5);
				im.setDisplayName("§a"+game.getName()+" - "+game.getState());
			} else if(game.getState()== State.RESTARTING) {
				item.setDurability((short) 1);
				im.setDisplayName("§6"+game.getName()+" - "+game.getState());
			} else {
				item.setDurability((short) 14);
				im.setDisplayName("§c"+game.getName()+" - "+game.getState());
			}
			item.setItemMeta(im);
			inv.setItem(i, item);
			i++;
		}
		ItemStack exit = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta eim = exit.getItemMeta();
		eim.setDisplayName("§cExit");
		exit.setItemMeta(eim);
		inv.setItem(inv.getContents().length-1, exit);
		return inv;
	}



	@Override
	public String getName() {
		return "manage";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("m"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.manage";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/ha manage";
	}

	@Override
	public CommandInterface getParent() {
		return Main.getCommandHandler().getExecutor("ha");
	}

}
package me.travja.hungerarena.managers;

import me.travja.hungerarena.Main;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameManager {

    private static ArrayList<Game> games = new ArrayList<>();

    private static int max;
    private static int min;

    public static void init() {
        max = Main.config.getInt("maxPlayers");
        min = Main.config.getInt("minPlayers");

        new BukkitRunnable() {
            public void run() {
                update();
                updateGames();
            }
        }.runTaskTimer(Main.self, 60L, 60L);
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void updateGames() {
        for (Game game : games) {
            game.update();
        }
    }

    /**
     * Adds the game to a list of active games
     *
     * @param g
     */
    public static void addGame(Game g) {
        games.add(g);
        Main.self.getServer().getPluginManager().registerEvents(g, Main.self);
    }

    /**
     * Removes the game from the list of active games
     *
     * @param g
     */
    public static void removeGame(Game g) {
        games.remove(g);
        InventoryClickEvent.getHandlerList().unregister(g);
    }

    /**
     * Disables all games
     */
    public static void disableAll() {
        for (Game g : games)
            g.disable();
    }

    /**
     * Enables all games
     */
    public static void enableAll() {
        for (Game g : games)
            g.enable();
    }

    /**
     * Stops all games
     */
    public static void stopAll() {
        for (Game g : games)
            g.stop();
    }

    /**
     * Checks if the player is spectating a game
     *
     * @param p
     * @return If the player is spectating any game
     */
    public static boolean isSpectator(Player p) {
        for (Game g : games)
            if (g.isSpectating(p))
                return true;
        return false;
    }

    /**
     * Checks if the player is playing in a game
     *
     * @param p
     * @return If the player is playing in any game
     */
    public static boolean isPlaying(Player p) {
        for (Game g : games)
            if (g.isPlaying(p))
                return true;
        return false;
    }

    /**
     * Gets the game the given player is in, spectating or playing
     *
     * @param p
     * @return Game object of the given player
     */
    public static Game getGame(Player p) {
        for (Game g : games)
            if (g.isPlaying(p) || g.isSpectating(p))
                return g;
        return null;
    }

    /**
     * Gets the game with the given name
     *
     * @param name of the game
     * @return The Game object
     */
    public static Game getGame(String name) {
        for (Game game : games)
            if (game.getName().equalsIgnoreCase(name))
                return game;
        return null;
    }

    /**
     * Check to see if a game with the given name already exists
     *
     * @param name of the arena to check
     * @return if the game exists already
     */
    public static boolean isGame(String name) {
        for (Game g : games)
            if (g.getName().equalsIgnoreCase(name))
                return true;
        return false;
    }

    public static int getMaximumPlayers() {
        return max;
    }

    public static int getMinimumPlayers() {
        return min;
    }


    private static int gameCount = 0;
    private static HashMap<String, Inventory> menus = new HashMap<>();

    public static void update() {
        if (getGames().size() != gameCount)
            for (String title : menus.keySet())
                refreshInventory(title);


        for (String title : menus.keySet()) {
            Inventory menu = menus.get(title);
            boolean manage = title.contains("Manage");
            ItemStack[] contents = menu.getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && item.getType() != Material.MAGMA_CREAM) {
                    ItemMeta im = item.getItemMeta();
                    String name = ChatColor.stripColor(im.getDisplayName().split(" ")[0]);
                    Game game = GameManager.getGame(name);
                    menu.setItem(i, getDisplayItem(game, manage));
                }
            }
        }
    }

    public static Inventory getInventory(String title) {
        refreshInventory(title);
        return menus.get(title);
    }

    private static Inventory refreshInventory(String title) {
        int newSize = (int) Math.ceil((double) (GameManager.getGames().size() + 1) / 9) * 9;
        Inventory inv = menus.get(title);
        if (inv == null || inv.getSize() != newSize)
            inv = Bukkit.createInventory(null, newSize, title);

        boolean manage = title.contains("Manage");

        int i = 0;
        for (Game game : getGames()) {
            inv.setItem(i, getDisplayItem(game, manage));
            i++;
        }
        ItemStack exit = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta eim = exit.getItemMeta();
        eim.setDisplayName("§cExit");
        exit.setItemMeta(eim);
        inv.setItem(inv.getContents().length - 1, exit);
        gameCount = getGames().size();
        menus.put(title, inv);
        return inv;
    }

    private static ItemStack getDisplayItem(Game game, boolean manage) {
        ItemStack item = new ItemStack(Material.GREEN_WOOL, game.getPlayers().size() > 0 ? game.getPlayers().size() : 1);
        String prefix;
        if (game.getGameState() == GameState.DISABLED ||
                game.getGameState() == GameState.INGAME ||
                game.getPlayers().size() == game.getMaxPlayers()) {
            item.setType(Material.RED_WOOL);
            prefix = ChatColor.RED + "";
        } else if (game.getGameState() == GameState.WAITING) {
            item.setType(Material.GREEN_WOOL);
            prefix = ChatColor.GREEN + "";
        } else if (game.getGameState() == GameState.RESTARTING) {
            item.setType(Material.ORANGE_WOOL);
            prefix = ChatColor.GOLD + "";
        } else if (game.getGameState() == GameState.STARTING) {
            item.setType(Material.YELLOW_WOOL);
            prefix = ChatColor.YELLOW + "";
        } else {
            prefix = ChatColor.AQUA + "Unknown state ";
        }

        ItemMeta im = item.getItemMeta();
        im.setDisplayName(prefix + game.getName() + " - " + game.getGameState());
        if (!manage)
            im.setLore(new ArrayList<>(Arrays.asList("§7Players: " + game.getPlayers().size() + "/" + game.getMaxPlayers(),
                    "§7Time: " + game.getTimeString(), "§8Left click to join or", "§8Right click to list players")));
        else
            im.setLore(new ArrayList<>(Arrays.asList("§7Players: " + game.getPlayers().size() + "/" + game.getMaxPlayers(),
                    "§7Time: " + game.getTimeString(), "§aLeft click to enable", "§cRight click to disable or", "§6Shift click to restart")));
        item.setItemMeta(im);

        return item;
    }
}

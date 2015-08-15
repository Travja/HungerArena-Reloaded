package me.Travja.HungerArena.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import me.Travja.HungerArena.CM;
import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Game implements Listener{

	public enum State{
		WAITING, STARTING, INGAME, RESTARTING, DISABLED
	}

	String name;
	State state;
	ArrayList<UUID> players;
	ArrayList<UUID> spectators;
	HashMap<UUID, GameMode> specGMs;
	HashMap<UUID, Location> specLocs;
	boolean global;
	boolean grace;
	int graceTime;
	int time = 0;
	boolean countdownEnabled;
	int countdown;

	Location min;
	Location max;
	Location spawn;

	HashMap<Location, ItemStack[]> chests = new HashMap<Location, ItemStack[]>();

	ArrayList<Location> signs = new ArrayList<Location>();

	HashMap<Integer, UUID> playerNumbers = new HashMap<Integer, UUID>();
	HashMap<UUID, ItemStack[]> playerInvs = new HashMap<UUID, ItemStack[]>();
	HashMap<UUID, ItemStack[]> playerArmor = new HashMap<UUID, ItemStack[]>();

	HashMap<Integer, Location> spawns = new HashMap<Integer, Location>();

	int maxPlayers;

	private FileConfiguration config;
	private FileConfiguration chestConfig;

	public Game(String name){
		this.name = name;
		this.state = State.WAITING;
		this.players = new ArrayList<UUID>();
		this.spectators = new ArrayList<UUID>();
		this.specGMs = new HashMap<UUID, GameMode>();
		this.specLocs = new HashMap<UUID, Location>();
		this.global = Main.config.getBoolean("broadcastAll");
		this.grace = true;
		this.countdownEnabled = Main.config.getBoolean("Countdown");
		if(countdownEnabled)
			this.countdown = Main.config.getInt("Countdown_Timer");
		save();
		load();

		maxPlayers = spawns.size();

		if(spawn== null)
			spawn = min.getWorld().getSpawnLocation();

		new BukkitRunnable() {
			public void run() {
				update();
			}
		}.runTaskTimer(Main.self, 20L, 20L);
	}

	public Game(String name, Location min, Location max) {
		this.name = name;
		this.min = min;
		this.max = max;
		this.state = State.WAITING;
		this.players = new ArrayList<UUID>();
		this.spectators = new ArrayList<UUID>();
		this.specLocs = new HashMap<UUID, Location>();
		this.specGMs = new HashMap<UUID, GameMode>();
		this.global = Main.config.getBoolean("broadcastAll");
		this.grace = true;
		save();
		load();

		maxPlayers = spawns.size();

		if(spawn== null)
			spawn = min.getWorld().getSpawnLocation();

		new BukkitRunnable() {
			public void run() {
				update();
			}
		}.runTaskTimer(Main.self, 20L, 20L);
	}

	public void setState(State state){
		this.state = state;
		updateSigns();
	}

	/**
	 * Starts the game
	 */
	public void start(){
		state = State.STARTING;
		//TODO Start the game
		updateSigns();
	}

	/**
	 * Stops the game
	 */
	public void stop(){
		state = State.DISABLED;
		//TODO Stop the game
		updateSigns();
	}

	/**
	 * Gets the time left in the game
	 * @return time left
	 */
	public int getTime() {
		return time;
	}

	public String getTimeString() {
		String string = "";

		int temptime = time;
		int hours = temptime/3600;
		temptime-=hours;
		int minutes = temptime/60;
		temptime-= minutes;

		if(hours> 0)
			string+=hours+":";
		if(minutes> 0 || string.length()> 0)
			string+=minutes+":";
		string+= temptime;

		return string;
	}

	/**
	 * Enables the game
	 */
	public void enable(){
		state = State.WAITING;
		updateSigns();
	}

	/**
	 * Disables the game
	 */
	public void disable(){
		//TODO kick all in game players
		state = State.DISABLED;
		updateSigns();
	}

	/**
	 * Restarts the game
	 */
	public void restart() {
		//TODO restart the game
		state = State.RESTARTING;
		updateSigns();
	}

	/**
	 * Declares the winner as the given player and stops the game
	 * @param p
	 */
	public void winner(Player p){
		//TODO winner stuff...
		updateSigns();
	}

	/**
	 * Set the spawn of the game
	 * @param l - the location to set the spawn to
	 */
	public void setSpawn(Location l){
		spawn = l;
		save();
	}

	/**
	 * Gets the name of the game
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Add a player to the arena
	 * @param player to add to the game
	 */
	public void addPlayer(Player player) {
		//TODO teleport player to game lobby
		sendMessage(Main.self+player.getName()+" §ajoined! §7"+(players.size()+1)+"/"+maxPlayers);
		players.add(player.getUniqueId());
		playerInvs.put(player.getUniqueId(), player.getInventory().getContents());
		playerArmor.put(player.getUniqueId(), player.getInventory().getArmorContents());

		if(state!= State.STARTING && players.size()>= GameManager.getMinimumPlayers())
			start();
		if(state== State.STARTING && time> 20 && ((double) maxPlayers*.75)<= players.size())
			countdown = 20;

		for(int i = 1; i<= maxPlayers; i++) {
			if(!playerNumbers.containsKey(i))
				playerNumbers.put(i, player.getUniqueId());
		}
		player.teleport(spawns.get(getNumber(player)));
	}

	/**
	 * Removes the player from the arena
	 * @param player to remove from the game
	 */
	@SuppressWarnings("deprecation")
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());

		player.getInventory().setContents(playerInvs.get(player.getUniqueId()));
		player.getInventory().setArmorContents(playerArmor.get(player.getUniqueId()));
		player.updateInventory();

		playerInvs.remove(player.getUniqueId());
		playerArmor.remove(player.getUniqueId());

		int num = 0;
		for(int i: playerNumbers.keySet())
			if(playerNumbers.get(i).equals(player.getUniqueId()))
				num = i;
		if(num!= 0)
			playerNumbers.remove(num);

		player.teleport(spawn);
		sendMessage(Main.tag+"§aThe tribute, §3" + player.getName() + " §ahas left!");
	}

	public int getNumber(Player player) {
		for(int i: playerNumbers.keySet())
			if(playerNumbers.get(i).equals(player.getUniqueId()))
				return i;
		return 0;
	}

	/**
	 * Adds the player to the spectator list for the game
	 * @param player to add to the spectator list
	 */
	@SuppressWarnings("deprecation")
	public void addSpectator(Player player) {
		spectators.add(player.getUniqueId());
		specGMs.put(player.getUniqueId(), player.getGameMode());
		specLocs.put(player.getUniqueId(), player.getLocation());
		player.setGameMode(GameMode.getByValue(3));
	}

	/**
	 * Removes the player from the spectator list
	 * @param player to remove from the list
	 */
	public void removeSpectator(Player player) {
		spectators.remove(player.getUniqueId());
		player.setGameMode(specGMs.get(player.getUniqueId()));
		player.teleport(specLocs.get(player.getUniqueId()));
		specGMs.remove(player.getUniqueId());
		specLocs.remove(player.getUniqueId());
	}

	/**
	 * Gets a list of players in the game
	 * @return ArrayList
	 */
	public ArrayList<UUID> getPlayers(){
		return players;
	}

	/**
	 * Gets a list of spectators in the game
	 * @return ArrayList
	 */
	public ArrayList<Player> getSpectators(){
		ArrayList<Player> toReturn = new ArrayList<Player>();
		for(UUID id: spectators)
			toReturn.add(Bukkit.getPlayer(id));
		return toReturn;
	}

	/**
	 * Checks if the player is playing in the game
	 * @param p
	 * @return If the player is playing in this game
	 */
	public boolean isPlaying(Player p){
		return players.contains(p.getUniqueId());
	}

	/**
	 * Checks if the player is spectating the game
	 * @param p
	 * @return If the player is spectating in this game
	 */
	public boolean isSpectating(Player p){
		return spectators.contains(p.getUniqueId());
	}

	/**
	 * Get the state of the game
	 * @return State of the game
	 */
	public State getState(){
		return this.state;
	}

	/**
	 * Checks if the game is in grace period
	 * @return If the game is in grace
	 */
	public boolean hasGrace(){
		return grace;
	}

	/**
	 * Reloads the chests in this arena
	 */
	public void refillChests() {
		for(Location l: chests.keySet()) {
			Block b = l.getWorld().getBlockAt(l);
			if(b.getType()== Material.CHEST) {
				Chest chest = (Chest) b.getState();
				chest.getBlockInventory().setContents(chests.get(l));
			}
		}
	}

	/**
	 * Sets the spawn for the given number to the given location
	 * @param spawn - the number of spawn to set
	 * @param l - the location to set the spawn to
	 */
	public void setSpawn(int spawn, Location l) {
		spawns.put(spawn, l);
		maxPlayers = spawns.size();
		save();
	}

	/**
	 * Send all players in the game a message
	 * @param message to be sent
	 */
	public void sendMessage(String message) {
		for(UUID id: players)
			Bukkit.getPlayer(id).sendMessage(message);
	}

	@SuppressWarnings("unchecked")
	private void load(){
		config = CM.getData(name);
		chestConfig = CM.getChests(name);
		min = getLocation(config.getString("Arena.min"), false);
		max = getLocation(config.getString("Arena.max"), false);

		//load chests
		if(chestConfig.getConfigurationSection("Chests")!= null){
			Map<String, Object> temp = chestConfig.getConfigurationSection("Chests").getValues(false);
			for(Entry<String, Object> entry: temp.entrySet()){
				Location l = getLocation(entry.getKey(), false);
				ItemStack[] items = null;
				Object o = chestConfig.get("Chests." + entry.getKey() + ".Items");
				if(o instanceof ItemStack[]){
					items = (ItemStack[]) o;
				}else if(o instanceof List){
					items = (ItemStack[]) ((List<ItemStack>) o).toArray(new ItemStack[0]);
				}
				chests.put(l, items);
			}
		}

		//load signs
		if(config.getConfigurationSection("Signs")!= null){
			Map<String, Object> temp = config.getConfigurationSection("Signs").getValues(false);
			for(Entry<String, Object> entry: temp.entrySet()){
				Location l = getLocation(entry.getKey(), false);
				signs.add(l);
			}
		}

		//load spawns
		spawn = getLocation(config.getString("Arena.spawn"), true);

		if(config.getConfigurationSection("Spawns")!= null) {
			Map<String, Object> temp = config.getConfigurationSection("Spawns").getValues(false);
			for(Entry<String, Object> entry: temp.entrySet())
				spawns.put(Integer.valueOf(entry.getKey()), getLocation(config.getString("Spawns."+entry.getKey()), true));
		}
		//TODO load info from the file
	}

	private void save(){
		config = CM.getData(name);
		chestConfig = CM.getChests(name);


		if(min!= null && max!= null) {
			config.set("Arena.min", locToString(min, false));
			config.set("Arena.max", locToString(max, false));
		}

		if(spawn!= null)
			config.set("Arena.spawn", locToString(spawn, true));

		if(!spawns.isEmpty())
			for(int i: spawns.keySet())
				config.set("Spawns."+i, locToString(spawns.get(i), true));


		CM.saveData(name);
		CM.saveChests(name);
		//TODO save data
	}

	public void updateSigns(){
		//TODO update signs
	}

	public Location getLocation(String string, boolean yaw) {
		Location l = new Location(null, 0, 0, 0);
		String[] coords = string.split(",");
		l.setWorld(Bukkit.getWorld(coords[0]));
		l.setX(Double.valueOf(coords[1]));
		l.setY(Double.valueOf(coords[2]));
		l.setZ(Double.valueOf(coords[3]));
		if(yaw) {
			l.setPitch(Float.valueOf(coords[4]));
			l.setYaw(Float.valueOf(coords[5]));
		}
		return l;
	}

	public String locToString(Location l, boolean yaw) {
		String coords = "";
		coords+=l.getWorld().getName();
		coords+=","+l.getX();
		coords+=","+l.getY();
		coords+=","+l.getZ();
		if(yaw) {
			coords+=","+l.getPitch();
			coords+=","+l.getYaw();
		}
		return coords;
	}

	public void delete() {
		File folder = new File(Main.self.getDataFolder()+File.separator+name);
		System.out.println(folder.exists() +"    "+ folder.getAbsolutePath());
		del(folder);
	}

	private void del(File file) {
		if(file.isDirectory())
			for(File f: file.listFiles())
				del(f);
		file.delete();
	}



	Inventory menu = null;
	String title = "";

	public void update() {
		title = "§2"+getName()+" - Players";
		if(menu== null || players.size()> menu.getContents().length)
			menu = Bukkit.createInventory(null, (int) Math.ceil((double) (players.size()+1)/9)*9, title);
		int i = 0;
		menu.clear();
		for(UUID id: players) {
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta im = (SkullMeta) item.getItemMeta();
			Player player = Bukkit.getPlayer(id);
			String name = player.getName();
			im.setOwner(name);
			im.setDisplayName("§7"+name);
			im.setLore(new ArrayList<String>(Arrays.asList(ChatColor.RED+"❤ "+player.getHealth()+" ❤")));
			item.setItemMeta(im);
			menu.setItem(i, item);
			i++;
		}
		ItemStack exit = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta eim = exit.getItemMeta();
		eim.setDisplayName("§cBack");
		exit.setItemMeta(eim);
		menu.setItem(menu.getContents().length-1, exit);
	}

	public void listPlayers(Player p) {
		p.openInventory(menu);
	}

	@EventHandler
	public void click(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		String name = inv.getTitle();
		if(name.equals(title) && event.getRawSlot()!= -999) {
			event.setCancelled(true);
			ItemStack item = event.getCurrentItem()!= null ? event.getCurrentItem() : event.getCursor()!= null ? event.getCursor() : inv.getItem(event.getRawSlot());
			if(item.getItemMeta().getDisplayName().equals("§cBack"))
				p.performCommand("ha list");
			else
				p.performCommand("ha tp "+ChatColor.stripColor(item.getItemMeta().getDisplayName()));
		}
	}
}
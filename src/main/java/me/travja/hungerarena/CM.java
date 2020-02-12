package me.travja.hungerarena;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CM {
	private static File optionsFile;
	public static FileConfiguration options;

	private static HashMap<String, File> spawnFiles = new HashMap<String, File>();
	private static HashMap<String, FileConfiguration> spawns = new HashMap<String, FileConfiguration>();
	public static void reloadSpawns(String arena) {
		if (!spawnFiles.containsKey(arena)) {
			spawnFiles.put(arena, new File(Main.self.getDataFolder() + File.separator + arena, "spawns.yml"));
		}
		spawns.put(arena, YamlConfiguration.loadConfiguration(spawnFiles.get(arena)));

		try{
			Reader defConfigStream = new InputStreamReader(Main.self.getResource("spawns.yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				spawns.get(arena).setDefaults(defConfig);
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	public static FileConfiguration getSpawns(String arena) {
		if (!spawns.containsKey(arena)) {
			reloadSpawns(arena);
		}
		return spawns.get(arena);
	}
	public static void saveSpawns(String arena) {
		if (!spawns.containsKey(arena) || !spawnFiles.containsKey(arena)) {
			return;
		}
		try {
			getSpawns(arena).save(spawnFiles.get(arena));
		} catch (IOException ex) {
			Main.log.log(Level.SEVERE, "Could not save config to " + spawnFiles.get(arena), ex);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	private static HashMap<String, File> dataFiles = new HashMap<String, File>();
	private static HashMap<String, FileConfiguration> Data = new HashMap<String, FileConfiguration>();
	public static void reloadData(String arena) {
		if (!dataFiles.containsKey(arena)) {
			dataFiles.put(arena, new File(Main.self.getDataFolder() + File.separator + arena, "Data.yml"));
		}
		Data.put(arena, YamlConfiguration.loadConfiguration(dataFiles.get(arena)));

		try{
			Reader defConfigStream = new InputStreamReader(Main.self.getResource("Data.yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				Data.get(arena).setDefaults(defConfig);
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	public static FileConfiguration getData(String arena) {
		if (!Data.containsKey(arena)) {
			reloadData(arena);
		}
		return Data.get(arena);
	}
	public static void saveData(String arena) {
		if (!Data.containsKey(arena) || !dataFiles.containsKey(arena)) {
			return;
		}
		try {
			getData(arena).save(dataFiles.get(arena));
		} catch (IOException ex) {
			Main.log.log(Level.SEVERE, "Could not save config to " + dataFiles.get(arena), ex);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void reloadOptions() {
		if (optionsFile == null) {
			optionsFile = new File(Main.self.getDataFolder(), "CmdsAndBlocks.yml.yml");
		}
		options = YamlConfiguration.loadConfiguration(optionsFile);

		try{
			Reader defConfigStream = new InputStreamReader(Main.self.getResource("CmdsAndBlocks.yml.yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				options.setDefaults(defConfig);
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	public static FileConfiguration getOptions() {
		if (options == null) {
			reloadOptions();
		}
		return options;
	}
	public static void saveOptions() {
		if (options == null || optionsFile == null) {
			return;
		}
		try {
			getOptions().save(optionsFile);
		} catch (IOException ex) {
			Main.log.log(Level.SEVERE, "Could not save config to " + optionsFile, ex);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private static HashMap<String, File> ChestFiles = new HashMap<String, File>();
	private static HashMap<String, FileConfiguration> Chests = new HashMap<String, FileConfiguration>();
	public static void reloadChests(String arena) {
		if (!ChestFiles.containsKey(arena)) {
			ChestFiles.put(arena, new File(Main.self.getDataFolder() + File.separator + arena, "Chests.yml"));
		}
		Chests.put(arena, YamlConfiguration.loadConfiguration(ChestFiles.get(arena)));

		try{
			Reader defConfigStream = new InputStreamReader(Main.self.getResource("Chests.yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				Chests.get(arena).setDefaults(defConfig);
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	public static FileConfiguration getChests(String arena) {
		if (!Chests.containsKey(arena)) {
			reloadChests(arena);
		}
		return Chests.get(arena);
	}
	public static void saveChests(String arena) {
		if (!Chests.containsKey(arena) || !ChestFiles.containsKey(arena)) {
			return;
		}
		try {
			getChests(arena).save(ChestFiles.get(arena));
		} catch (IOException ex) {
			Main.log.log(Level.SEVERE, "Could not save config to " + ChestFiles.get(arena), ex);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static HashMap<UUID, File> PFiles = new HashMap<UUID, File>();
	private static HashMap<UUID, FileConfiguration> PConfigs = new HashMap<UUID, FileConfiguration>();
	public static void reloadPFile(UUID uuid) {
		if (!PFiles.containsKey(uuid)) {
			PFiles.put(uuid, new File(Main.self.getDataFolder() + File.separator + "Players", uuid.toString() + ".yml"));
		}
		PConfigs.put(uuid, YamlConfiguration.loadConfiguration(PFiles.get(uuid)));

		try{
			Reader defConfigStream = new InputStreamReader(Main.self.getResource(uuid.toString() + ".yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				PConfigs.get(uuid).setDefaults(defConfig);
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	public static FileConfiguration getPConfig(UUID uuid) {
		if(!PConfigs.containsKey(uuid))
			reloadPFile(uuid);
		return PConfigs.get(uuid);
	}
	public static void savePFile(UUID uuid) {
		if (!PConfigs.containsKey(uuid) || !PFiles.containsKey(uuid)) {
			return;
		}
		try {
			getPConfig(uuid).save(PFiles.get(uuid));
		} catch (IOException ex) {
			Main.log.log(Level.SEVERE, "Could not save config to " + PFiles.get(uuid), ex);
		}
	}
}

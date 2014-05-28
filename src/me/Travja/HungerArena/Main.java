package me.Travja.HungerArena;

import java.io.File;
import java.util.logging.Logger;

import me.Travja.HungerArena.commands.CommandCore;
import me.Travja.HungerArena.listeners.PvP;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static FileConfiguration config;
	public static Logger log;
	
	public void onEnable(){
		config = this.getConfig();
		config.options().copyDefaults(true);
		if(!new File(this.getDataFolder(), "config.yml").exists())
			this.saveDefaultConfig();
		
		log = this.getLogger();
		
		this.getServer().getPluginManager().registerEvents(new PvP(), this);
		
		this.getCommand("ha").setExecutor(new CommandCore());
		
		log.info("HungerArena has been enabled!");		
	}
	public void onDisable(){
		
	}
}

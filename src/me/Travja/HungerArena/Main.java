package me.Travja.HungerArena;

import java.io.File;
import java.util.logging.Logger;

import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.commands.CommandHandler;
import me.Travja.HungerArena.commands.ha.ArenaCommand;
import me.Travja.HungerArena.commands.ha.CommandCore;
import me.Travja.HungerArena.commands.ha.DisableCommand;
import me.Travja.HungerArena.commands.ha.EnableCommand;
import me.Travja.HungerArena.commands.ha.HelpCommand;
import me.Travja.HungerArena.commands.ha.JoinCommand;
import me.Travja.HungerArena.commands.ha.KickCommand;
import me.Travja.HungerArena.commands.ha.LeaveCommand;
import me.Travja.HungerArena.commands.ha.ListCommand;
import me.Travja.HungerArena.commands.ha.ManageCommand;
import me.Travja.HungerArena.commands.ha.RefillCommand;
import me.Travja.HungerArena.commands.ha.StartCommand;
import me.Travja.HungerArena.listeners.PvP;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Main extends JavaPlugin {

	public static FileConfiguration config;
	public static Logger log;
	public static Main self;
	
	public static WorldEditPlugin we;
	
	public static String tag = "§3[HungerArena] ";
	
	public void onEnable(){
		self = this;
		config = this.getConfig();
		config.options().copyDefaults(true);
		if(!new File(this.getDataFolder(), "config.yml").exists())
			this.saveDefaultConfig();
		
		
		
		if(Version.getVersion(config.getString("config"))== Version.LEGACY)
			convert();
		
		log = this.getLogger();
		
		this.getServer().getPluginManager().registerEvents(new PvP(), this);
		
		we = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");

		load();
		
		GameManager.init();
		
		registerCommands();
		
		log.info("HungerArena has been enabled!");		
	}
	public void onDisable(){
		
		log.info("HungerArena has been disabled!");
	}
	
	static CommandHandler handler;
	
	private void registerCommands() {
		handler = new CommandHandler();
		
		handler.register(new CommandCore());
		handler.register(new ArenaCommand());
		handler.register(new HelpCommand());
		handler.register(new StartCommand());
		handler.register(new JoinCommand());
		handler.register(new LeaveCommand());
		handler.register(new EnableCommand());
		handler.register(new DisableCommand());
		handler.register(new RefillCommand());
		handler.register(new KickCommand());
		ListCommand lcom = new ListCommand();
		getServer().getPluginManager().registerEvents(lcom, this);
		handler.register(lcom);
		lcom.init();
		ManageCommand mcom = new ManageCommand();
		getServer().getPluginManager().registerEvents(mcom, this);
		handler.register(mcom);
		mcom.init();
		
		getCommand("ha").setExecutor(handler);
	}
	
	public static CommandHandler getCommandHandler() { 
		return handler;
	}
	
	private void load(){
		for(File file: this.getDataFolder().listFiles())
			if(file.isDirectory() && !file.getName().equals("Players"))
				GameManager.addGame(new Game(file.getName()));
	}
	
	public void convert(){
		//TODO convert configs to new version
		
		if(Version.getVersion(config.getString("config"))== Version.LEGACY && Version.getVersion(this.getDescription().getVersion())== Version.V2BETA) {
			//BIG Conversion
		}
		
		if(Version.getVersion(config.getString("config"))== Version.V2BETA && Version.getVersion(this.getDescription().getVersion())== Version.V2) {
			//Possibly no conversion
		}
		
		
		config.set("config", this.getDescription().getVersion());
		this.saveConfig();
	}
}

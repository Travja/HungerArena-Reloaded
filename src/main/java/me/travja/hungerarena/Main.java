package me.travja.hungerarena;

import java.io.File;
import java.util.logging.Logger;

import me.travja.hungerarena.resources.Game;
import me.travja.hungerarena.commands.CommandHandler;
import me.travja.hungerarena.listeners.MovementListener;
import me.travja.hungerarena.listeners.PvP;

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
		title();
		self = this;
		config = this.getConfig();
		config.options().copyDefaults(true);
		if(!new File(this.getDataFolder(), "config.yml").exists())
			this.saveDefaultConfig();

		log = this.getLogger();

		this.getServer().getPluginManager().registerEvents(new PvP(), this);
		this.getServer().getPluginManager().registerEvents(new MovementListener(), this);

		we = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");

		load();

		GameManager.init();

		registerCommands();

		log.info("HungerArena has been enabled!");		
	}
	public void onDisable(){

		log.info("HungerArena has been disabled!");
	}

	private static CommandHandler handler;

	private void registerCommands() {
		handler = new CommandHandler();

		handler.init();

		getServer().getPluginCommand("ha").setExecutor(handler);
		getServer().getPluginCommand("startpoint").setExecutor(handler);
	}

	public void title() {
		String spaces = "         ";
		String spaces2 = "            ";
		System.out.println(spaces+" _   _ _   _ _   _ _____  ___________");
		System.out.println(spaces+"| | | | | | | \\ | |  __ \\|  ___| ___ \\");
		System.out.println(spaces+"| |_| | | | |  \\| | |  \\/| |__ | |_/ /");
		System.out.println(spaces+"|  _  | | | | . ` | | __ |  __||    /");
		System.out.println(spaces+"| | | | |_| | |\\  | |_\\ \\| |___| |\\ \\");
		System.out.println(spaces+"\\_| |_/\\___/\\_| \\_/\\____/\\____/\\_| \\_\\");
		System.out.println(spaces2+"  ___  ______ _____ _   _   ___");
		System.out.println(spaces2+" / _ \\ | ___ \\  ___| \\ | | / _ \\ ");
		System.out.println(spaces2+"/ /_\\ \\| |_/ / |__ |  \\| |/ /_\\ \\");
		System.out.println(spaces2+"|  _  ||    /|  __|| . ` ||  _  |");
		System.out.println(spaces2+"| | | || |\\ \\| |___| |\\  || | | |");
		System.out.println(spaces2+"\\_| |_/\\_| \\_\\____/\\_| \\_/\\_| |_/");
	}

	public static CommandHandler getCommandHandler() { 
		return handler;
	}

	private void load(){
		for(File file: this.getDataFolder().listFiles())
			if(file.isDirectory() && !file.getName().equals("Players"))
				GameManager.addGame(new Game(file.getName()));
	}
}

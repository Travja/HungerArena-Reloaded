package me.Travja.HungerArena;

import java.util.ArrayList;

import me.Travja.HungerArena.Resources.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GameManager {
	
	private static ArrayList<Game> games = new ArrayList<Game>();
	
	private static int max;
	
	public static void init() {
		max = Main.config.getInt("maxPlayers");
	}
	
	public static ArrayList<Game> getGames() {
		return games;
	}
	
	public static void updateGames() {
		for(Game game: games) {
			game.update();
		}
	}
	
	/**
	 * Adds the game to a list of active games
	 * @param g
	 */
	public static void addGame(Game g){
		games.add(g);
		Main.self.getServer().getPluginManager().registerEvents(g, Main.self);
	}
	
	/**
	 * Removes the game from the list of active games
	 * @param g
	 */
	public static void removeGame(Game g){
		games.remove(g);
		InventoryClickEvent.getHandlerList().unregister(g);
	}
	
	/**
	 * Disables all games
	 */
	public static void disableAll(){
		for(Game g: games)
			g.disable();
	}
	
	/**
	 * Enables all games
	 */
	public static void enableAll(){
		for(Game g: games)
			g.enable();
	}
	
	/**
	 * Stops all games
	 */
	public static void stopAll(){
		for(Game g: games)
			g.stop();
	}
	
	/**
	 * Checks if the player is spectating a game
	 * @param p
	 * @return If the player is spectating any game
	 */
	public static boolean isSpectator(Player p){
		for(Game g: games)
			if(g.isSpectating(p))
				return true;
		return false;
	}
	
	/**
	 * Checks if the player is playing in a game
	 * @param p
	 * @return If the player is playing in any game
	 */
	public static boolean isPlaying(Player p){
		for(Game g: games)
			if(g.isPlaying(p))
				return true;
		return false;
	}
	
	/**
	 * Gets the game the given player is in, spectating or playing
	 * @param p
	 * @return Game object of the given player
	 */
	public static Game getGame(Player p){
		for(Game g: games)
			if(g.isPlaying(p) || g.isSpectating(p))
				return g;
		return null;
	}
	
	/**
	 * Gets the game with the given name
	 * @param name of the game
	 * @return The Game object
	 */
	public static Game getGame(String name) {
		for(Game game: games)
			if(game.getName().equalsIgnoreCase(name))
				return game;
		return null;
	}
	
	/**
	 * Check to see if a game with the given name already exists
	 * @param name of the arena to check
	 * @return if the game exists already
	 */
	public static boolean isGame(String name) {
		for(Game g: games)
			if(g.getName().equalsIgnoreCase(name))
				return true;
		return false;
	}
	
	public static int getMaximumPlayers() {
		return max;
	}
}

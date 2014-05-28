package me.Travja.HungerArena.Resources;

import java.util.ArrayList;

import me.Travja.HungerArena.Main;

public class Game {

	public enum State{
		WAITING, STARTING, INGAME, RESTARTING, DISABLED
	}

	State state;
	ArrayList<String> players;
	boolean global;
	public Game(String name){
		this.state = State.WAITING;
		this.players = new ArrayList<String>();
		this.global = Main.config.getBoolean("");
	}
	
	/**
	 * Starts the game
	 */
	public void start(){
		
	}
	
	/**
	 * Stops the game
	 */
	public void stop(){
		
	}
	
	/**
	 * Gets a list of players in the game
	 * @return ArrayList
	 */
	public ArrayList<String> getPlayers(){
		return this.players;
	}

	/**
	 * Get the state of the game
	 * @return State
	 */
	public State getState(){
		return this.state;
	}
	
}

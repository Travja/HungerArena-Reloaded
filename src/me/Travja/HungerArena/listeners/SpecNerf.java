package me.Travja.HungerArena.listeners;

import me.Travja.HungerArena.GameManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpecNerf implements Listener {

	
	@EventHandler
	public void place(BlockPlaceEvent event){
		if(GameManager.isSpectator(event.getPlayer())) event.setCancelled(true);
	}
}

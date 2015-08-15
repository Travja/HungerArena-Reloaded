package me.Travja.HungerArena.listeners;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Resources.Game;
import me.Travja.HungerArena.Resources.Game.State;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

	@EventHandler
	public void move(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if(GameManager.isPlaying(p)) {
			Game game = GameManager.getGame(p);
			if(game.getState()== State.WAITING || game.getState()== State.STARTING) {
				if(!event.getTo().getBlock().equals(event.getFrom().getBlock()))
					event.setTo(event.getFrom());
			}
		}
	}
	
}

package me.travja.hungerarena.listeners;

import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.game.GameState;

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
			if(game.getGameState()== GameState.WAITING || game.getGameState()== GameState.STARTING) {
				if(!event.getTo().getBlock().equals(event.getFrom().getBlock()))
					event.setTo(event.getFrom());
			}
		}
	}
	
}

package me.travja.hungerarena.listeners;

import me.travja.hungerarena.game.Game;
import me.travja.hungerarena.managers.GameManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PvP implements Listener {

    @EventHandler
    public void pvp(EntityDamageByEntityEvent event) {
        Entity e = event.getEntity();
        Entity d = event.getDamager();
        if (e instanceof Player) {
            Player p = (Player) e;
            Player damager = null;
            if (d instanceof Player)
                damager = (Player) d;
            else if (d instanceof Projectile)
                if (((Projectile) d).getShooter() instanceof Player)
                    damager = (Player) ((Projectile) d).getShooter();


            if (damager == null || !GameManager.isPlaying(p))
                return;


            Game game = GameManager.getGame(p);
            if (game.hasGrace())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void pvp(EntityDamageEvent event) {
        Entity e = event.getEntity();
        if (!(e instanceof Player) || !GameManager.isPlaying((Player) e))
            return;

        Player p = (Player) e;
        Game game = GameManager.getGame(p);
        if (game.hasGrace())
            event.setCancelled(true);
    }
}

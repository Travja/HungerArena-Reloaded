package me.Travja.HungerArena.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import me.Travja.HungerArena.GameManager;
import me.Travja.HungerArena.Main;
import me.Travja.HungerArena.Resources.Game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnsCommand implements CommandInterface, Listener {

	Material tool;
	HashMap<UUID, Game> setters = new HashMap<UUID, Game>();

	public SpawnsCommand() {
		tool = Material.matchMaterial(Main.config.getString("spawnsTool"));
		if(tool== null)
			Main.log.warning("Could not find a tool from material '"+Main.config.getString("spawnsTool")+"'");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cThis can only be run in game!");
			return true;
		}
		Player p = (Player) sender;
		if(args.length== 0)
			return false;
		if(args[0].equalsIgnoreCase("cancel")) {
			setters.remove(p.getUniqueId());
			p.sendMessage("§aNo longer setting spawn points");
			return true;
		}
		if(GameManager.isGame(args[0])) {
			Game game = GameManager.getGame(args[0]);
			if(args.length== 1) {
				if(tool!= null) {
					if(game.getMaxPlayers()!= GameManager.getMaximumPlayers()) {
						setters.put(p.getUniqueId(), game);
						p.sendMessage(Main.tag+"§aBeing setting spawns for §3"+game.getName()+"§a starting from §3"+(game.getMaxPlayers()+1));
						p.sendMessage("§aType §3/startpoint cancel §ato stop setting points!");
					} else
						p.sendMessage("§cAll the spawns have been set for this arena! Use the full command to override current spawns.");
				} else
					p.sendMessage("§cThere is no spawns tool. Please use the full command!");
			} else {
				try {
					int i = Integer.parseInt(args[1]);
					game.setSpawn(i, p.getLocation());
					p.sendMessage(Main.tag+"§aSpawn point §3"+i+"§a set for §3"+game.getName());
				} catch (Exception e){
					p.sendMessage("§cYour second argument was not a number! Please use numbers!");
				}
			}
		} else
			p.sendMessage("§cThat game doesn't exist!");
		return true;
	}

	@EventHandler
	public void interact(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.hasItem()) {
			ItemStack item = event.getItem();
			if(item.getType()== tool && setters.containsKey(p.getUniqueId())) {
				Game game = setters.get(p.getUniqueId());
				Location l = event.getClickedBlock().getLocation();
				l.add(0.5, 1, 0.5);
				game.setSpawn(game.getMaxPlayers()+1, l);
				p.sendMessage(Main.tag+"§aSpawn point §3"+game.getMaxPlayers()+" §aset for §3"+game.getName());
				if(game.getMaxPlayers()== GameManager.getMaximumPlayers())
					setters.remove(p.getUniqueId());
			}
		}
	}

	@Override
	public String getName() {
		return "startpoint";
	}

	@Override
	public ArrayList<String> getAliases() {
		return new ArrayList<String>(Arrays.asList("sp"));
	}

	@Override
	public String getPermission() {
		return "hungerarena.startpoint";
	}

	@Override
	public String getPermissionMessage() {
		return "§cYou don't have permission for that!";
	}

	@Override
	public String getUsage() {
		return "/startpoint [name|cancel] <number>";
	}

	@Override
	public boolean isIndependent() {
		return true;
	}

}

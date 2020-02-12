package me.travja.hungerarena.commands.sponsor;

import me.travja.hungerarena.managers.GameManager;
import me.travja.hungerarena.commands.CommandModule;
import me.travja.hungerarena.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SponsorModule extends CommandModule {

    public SponsorModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length >= 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && target.isOnline()) {
                Game game = GameManager.getGame(target);
                if (game != null) {
                    Material mat = Material.getMaterial(args[1]);
                    int amount = 1;
                    if (args.length >= 3)
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "3rd argument must be a number");
                            return true;
                        }

                    if (amount > 0) {
                        target.getInventory().addItem(new ItemStack(mat, amount));
                    } else
                        sender.sendMessage(ChatColor.RED + "We can't give them negative items.");

                } else
                    sender.sendMessage(ChatColor.RED + "That player isn't in a game right now!");
            } else
                sender.sendMessage(ChatColor.RED + "Couldn't find that player.");
            return true;
        }
        return false;
    }
}

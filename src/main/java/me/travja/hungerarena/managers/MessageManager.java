package me.travja.hungerarena.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

    public static String tag = ChatColor.DARK_AQUA + "[HungerArena] ";

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(tag + translate(sender, message));
    }

    private static String translate(CommandSender sender, String message) {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("%name%", sender.getName());
    }

}

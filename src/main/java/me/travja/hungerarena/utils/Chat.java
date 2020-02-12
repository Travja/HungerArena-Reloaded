package me.travja.hungerarena.utils;

import org.bukkit.ChatColor;

public class Chat {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}

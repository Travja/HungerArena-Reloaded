package me.travja.hungerarena.utils;

import me.travja.hungerarena.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Locale {

    public class Permission {
        public static final String NO_PERM = "permission.no_perm";
    }


    public static String getMessage(String path) {
       FileConfiguration conf = ConfigManager.getConfig(ConfigManager.ConfigType.LANG, null);

       if(conf == null)
           return "";

       String msg = ChatColor.translateAlternateColorCodes('&', conf.getString(path));

       //TODO Possible placeholders?

       return msg;
    }

}

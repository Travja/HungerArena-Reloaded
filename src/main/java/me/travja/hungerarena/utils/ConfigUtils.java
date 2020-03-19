package me.travja.hungerarena.utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public static void setLocIfNotNull(FileConfiguration config, String path, Location loc, boolean yaw) {
        if(loc != null)
            config.set(path, LocationUtils.locToString(loc, yaw));
    }

}

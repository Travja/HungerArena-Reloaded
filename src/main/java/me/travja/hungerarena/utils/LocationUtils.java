package me.travja.hungerarena.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static Location getLocation(String string, boolean yaw) {
        Location l = new Location(null, 0, 0, 0);
        if (string == null)
            return null;

        String[] coords = string.split(",");
        l.setWorld(Bukkit.getWorld(coords[0]));
        l.setX(Double.valueOf(coords[1]));
        l.setY(Double.valueOf(coords[2]));
        l.setZ(Double.valueOf(coords[3]));
        if (yaw) {
            l.setPitch(Float.valueOf(coords[4]));
            l.setYaw(Float.valueOf(coords[5]));
        }
        return l;
    }

    public static String locToString(Location l, boolean yaw) {
        String coords = "";
        coords += l.getWorld().getName();
        coords += "," + l.getX();
        coords += "," + l.getY();
        coords += "," + l.getZ();
        if (yaw) {
            coords += "," + l.getPitch();
            coords += "," + l.getYaw();
        }
        return coords;
    }

}

package me.travja.hungerarena.managers;

import me.travja.hungerarena.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class ConfigManager {
/*    private static HashMap<String, File> chestFiles = new HashMap<>();
    private static HashMap<String, FileConfiguration> chests = new HashMap<>();

    private static HashMap<String, File> dataFiles = new HashMap<>();
    private static HashMap<String, FileConfiguration> data = new HashMap<>();

    private static HashMap<UUID, File> pFiles = new HashMap<>();
    private static HashMap<UUID, FileConfiguration> pConfigs = new HashMap<>();

    private static File optionsFile;
    public static FileConfiguration options;

    private static HashMap<String, File> spawnFiles = new HashMap<>();
    private static HashMap<String, FileConfiguration> spawns = new HashMap<>();*/


    private static HashMap<String, File> files = new HashMap<>();
    private static HashMap<String, FileConfiguration> confs = new HashMap<>();

    public enum ConfigType {
        SPAWNS,
        PLAYER_DATA,
        DATA,
        CMDSBLOCKS,
        CHESTS,
        ARENA_DATA,
        LANG
    }

    public static void reloadConfig(ConfigType type, String data) {
        if (!files.containsKey(type.toString() + "-" + data))
            files.put(type.toString() + "-" + data, new File(Main.self.getDataFolder() + (
                    type == ConfigType.ARENA_DATA || type == ConfigType.PLAYER_DATA ||
                            type == ConfigType.CHESTS || type == ConfigType.DATA || type == ConfigType.SPAWNS
                            ? File.separator + data
                            : ""
            ), getFileName(type, data) + ".yml"));
        loadDefaults(type, data);


        confs.put(type.toString() + "-" + data, YamlConfiguration.loadConfiguration(files.get(type.toString() + "-" + data)));
    }

    private static void loadDefaults(ConfigType type, String data) {
        try {
            Reader defConfigStream = new InputStreamReader(Main.self.getResource(getFileName(type, data) + ".yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                confs.get(type.toString() + "-" + data).setDefaults(defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfig(ConfigType type, String data) {
        if (!confs.containsKey(type.toString() + "-" + data))
            reloadConfig(type, data);

        return confs.get(type.toString() + "-" + data);
    }

    public static void saveConfig(ConfigType type, String data) {
        if (!confs.containsKey(type.toString() + "-" + data) || !files.containsKey(type.toString() + "-" + data))
            return;

        try {
            getConfig(type, data).save(files.get(type.toString() + "-" + data));
        } catch (IOException ex) {
            Main.log.log(Level.SEVERE, "Could not save config to " + files.get(type.toString() + "-" + data), ex);
        }
    }

    public static String getFileName(ConfigType type, String data) {
        String ret = "";

        switch (type) {
            case PLAYER_DATA:
            case ARENA_DATA:
                ret = data;
                break;
            case CMDSBLOCKS:
            case CHESTS:
            case SPAWNS:
            case LANG:
            case DATA:
                ret = type.toString().toLowerCase();
                break;
            default:
                ret = "Unknown";
                break;
        }

        return ret;
    }

/*    public static void reloadSpawns(String arena) {
        if (!spawnFiles.containsKey(arena)) {
            spawnFiles.put(arena, new File(Main.self.getDataFolder() + File.separator + arena, "spawns.yml"));
        }
        spawns.put(arena, YamlConfiguration.loadConfiguration(spawnFiles.get(arena)));

        try {
            Reader defConfigStream = new InputStreamReader(Main.self.getResource("spawns.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                spawns.get(arena).setDefaults(defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getSpawns(String arena) {
        if (!spawns.containsKey(arena)) {
            reloadSpawns(arena);
        }
        return spawns.get(arena);
    }

    public static void saveSpawns(String arena) {
        if (!spawns.containsKey(arena) || !spawnFiles.containsKey(arena)) {
            return;
        }
        try {
            getSpawns(arena).save(spawnFiles.get(arena));
        } catch (IOException ex) {
            Main.log.log(Level.SEVERE, "Could not save config to " + spawnFiles.get(arena), ex);
        }
    }


    public static void reloadData(String arena) {
        if (!dataFiles.containsKey(arena)) {
            dataFiles.put(arena, new File(Main.self.getDataFolder() + File.separator + arena, "data.yml"));
        }
        data.put(arena, YamlConfiguration.loadConfiguration(dataFiles.get(arena)));

        try {
            Reader defConfigStream = new InputStreamReader(Main.self.getResource("data.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                data.get(arena).setDefaults(defConfig);
                //data.get(arena).save(dataFiles.get(arena));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getData(String arena) {
        if (!data.containsKey(arena)) {
            reloadData(arena);
        }
        return data.get(arena);
    }

    public static void saveData(String arena) {
        if (!data.containsKey(arena) || !dataFiles.containsKey(arena)) {
            return;
        }
        try {
            getData(arena).save(dataFiles.get(arena));
        } catch (IOException ex) {
            Main.log.log(Level.SEVERE, "Could not save config to " + dataFiles.get(arena), ex);
        }
    }


    public static void reloadOptions() {
        if (optionsFile == null) {
            optionsFile = new File(Main.self.getDataFolder(), "cmdsblocks.yml");
        }
        options = YamlConfiguration.loadConfiguration(optionsFile);

        try {
            Reader defConfigStream = new InputStreamReader(Main.self.getResource("cmdsblocks.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                options.setDefaults(defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getOptions() {
        if (options == null) {
            reloadOptions();
        }
        return options;
    }

    public static void saveOptions() {
        if (options == null || optionsFile == null) {
            return;
        }
        try {
            getOptions().save(optionsFile);
        } catch (IOException ex) {
            Main.log.log(Level.SEVERE, "Could not save config to " + optionsFile, ex);
        }
    }


    public static void reloadChests(String arena) {
        if (!chestFiles.containsKey(arena)) {
            chestFiles.put(arena, new File(Main.self.getDataFolder() + File.separator + arena, "chests.yml"));
        }
        chests.put(arena, YamlConfiguration.loadConfiguration(chestFiles.get(arena)));

        try {
            Reader defConfigStream = new InputStreamReader(Main.self.getResource("chests.yml"), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                chests.get(arena).setDefaults(defConfig);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getChests(String arena) {
        if (!chests.containsKey(arena)) {
            reloadChests(arena);
        }
        return chests.get(arena);
    }

    public static void saveChests(String arena) {
        if (!chests.containsKey(arena) || !chestFiles.containsKey(arena)) {
            return;
        }
        try {
            getChests(arena).save(chestFiles.get(arena));
        } catch (IOException ex) {
            Main.log.log(Level.SEVERE, "Could not save config to " + chestFiles.get(arena), ex);
        }
    }


    public static void reloadPFile(UUID uuid) {
        if (!pFiles.containsKey(uuid)) {
            pFiles.put(uuid, new File(Main.self.getDataFolder() + File.separator + "players", uuid.toString() + ".yml"));
        }
        pConfigs.put(uuid, YamlConfiguration.loadConfiguration(pFiles.get(uuid)));

//        try {
//            Reader defConfigStream = new InputStreamReader(Main.self.getResource(uuid.toString() + ".yml"), "UTF8");
//            if (defConfigStream != null) {
//                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
//                pConfigs.get(uuid).setDefaults(defConfig);
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }

    public static FileConfiguration getPConfig(UUID uuid) {
        if (!pConfigs.containsKey(uuid))
            reloadPFile(uuid);
        return pConfigs.get(uuid);
    }

    public static void savePFile(UUID uuid) {
        if (!pConfigs.containsKey(uuid) || !pFiles.containsKey(uuid)) {
            return;
        }
        try {
            getPConfig(uuid).save(pFiles.get(uuid));
        } catch (IOException ex) {
            Main.log.log(Level.SEVERE, "Could not save config to " + pFiles.get(uuid), ex);
        }
    }*/
}

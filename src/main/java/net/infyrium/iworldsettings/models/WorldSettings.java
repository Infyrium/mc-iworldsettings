package net.infyrium.iworldsettings.models;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;

import net.infyrium.iworldsettings.iWorldSettingsMain;

/**
 * Settings of one world: values from worlds.{world} with fallback to default.
 */
public class WorldSettings {

    public final boolean noDamage;
    public final boolean noHunger;
    public final GameMode gameMode;

    public final boolean noBreak;
    public final boolean noPlace;
    public final boolean noInteract;
    public final boolean noFluidFlow;
    public final boolean noExplosions;
    public final boolean noWeather;
    public final boolean noFireSpread;
    public final boolean noLeafDecay;
    public final boolean noGrowth;
    public final boolean noMobGriefing;
    public final boolean noMobSpawn;
    public final boolean fixedTime;
    public final long fixedTimeMeaning;

    private final iWorldSettingsMain plugin;
    private final FileConfiguration config;
    private final String worldPath;

    public WorldSettings(iWorldSettingsMain plugin, String worldName) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.worldPath = "worlds." + worldName + ".";

        this.noDamage = getBoolean("settings-player.noDamage");
        this.noHunger = getBoolean("settings-player.noHunger");
        this.gameMode = parseGameMode(getString("settings-player.gameMode"), worldName);

        this.noBreak = getBoolean("settings-world.noBreak");
        this.noPlace = getBoolean("settings-world.noPlace");
        this.noInteract = getBoolean("settings-world.noInteract");
        this.noFluidFlow = getBoolean("settings-world.noFluidFlow");
        this.noExplosions = getBoolean("settings-world.noExplosions");
        this.noWeather = getBoolean("settings-world.noWeather");
        this.noFireSpread = getBoolean("settings-world.noFireSpread");
        this.noLeafDecay = getBoolean("settings-world.noLeafDecay");
        this.noGrowth = getBoolean("settings-world.noGrowth");
        this.noMobGriefing = getBoolean("settings-world.noMobGriefing");
        this.noMobSpawn = getBoolean("settings-world.noMobSpawn");
        this.fixedTime = getBoolean("settings-world.fixedTime.enabled");
        this.fixedTimeMeaning = config.getLong(resolve("settings-world.fixedTime.meaning"));
    }

    private String resolve(String path) {
        return config.isSet(worldPath + path) ? worldPath + path : "default." + path;
    }

    private boolean getBoolean(String path) {
        return config.getBoolean(resolve(path));
    }

    private String getString(String path) {
        return config.getString(resolve(path));
    }

    private GameMode parseGameMode(String value, String worldName) {
        if (value == null || value.isEmpty()) return null;
        try {
            return GameMode.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Incorrect gamemode in the config for world '" + worldName + "': " + value);
            return null;
        }
    }
}

package net.infyrium.iworldsettings;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class iWorldSettingsMain extends JavaPlugin {

    private final Map<String, WorldSettings> settings = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockEventListener(this), this);

        for (World world : Bukkit.getWorlds()) {
            applyWorldSettings(world);
        }

        getLogger().info("Plugin has been enabled!");
        getLogger().info("Plugin developed by: " + String.join(", ", getPluginMeta().getAuthors()));
        getLogger().info("Website: " + getPluginMeta().getWebsite());
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }

    public WorldSettings getSettings(World world) {
        return settings.computeIfAbsent(world.getName(), name -> new WorldSettings(this, name));
    }

    /**
     * Applies settings that are stored in the world itself (time, gamerules, mobs).
     */
    public void applyWorldSettings(World world) {
        WorldSettings worldSettings = getSettings(world);

        if (worldSettings.fixedTime) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setTime(worldSettings.fixedTimeMeaning);
        }
        if (worldSettings.noMobGriefing) {
            world.setGameRule(GameRule.MOB_GRIEFING, false);
        }
        if (worldSettings.noMobSpawn) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }
        }
    }
}

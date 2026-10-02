package net.infyrium.iworldsettings;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
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

        disableGameRule(world, GameRule.DO_DAYLIGHT_CYCLE, worldSettings.fixedTime);
        if (worldSettings.fixedTime) {
            world.setTime(worldSettings.fixedTimeMeaning);
        }
        disableGameRule(world, GameRule.MOB_GRIEFING, worldSettings.noMobGriefing);
        if (worldSettings.noMobSpawn) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }
        }
    }

    /**
     * Sets the gamerule to false and remembers its previous value in the world data.
     * When the setting is turned off, restores the previous value.
     */
    private void disableGameRule(World world, GameRule<Boolean> rule, boolean disable) {
        NamespacedKey key = new NamespacedKey(this, rule.getName().toLowerCase());
        PersistentDataContainer data = world.getPersistentDataContainer();

        if (disable) {
            if (!data.has(key)) {
                Boolean previous = world.getGameRuleValue(rule);
                data.set(key, PersistentDataType.BOOLEAN, previous == null || previous);
            }
            world.setGameRule(rule, false);
        } else if (data.has(key)) {
            world.setGameRule(rule, data.get(key, PersistentDataType.BOOLEAN));
            data.remove(key);
        }
    }
}

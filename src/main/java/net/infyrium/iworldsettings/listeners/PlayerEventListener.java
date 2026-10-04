package net.infyrium.iworldsettings.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.infyrium.iworldsettings.iWorldSettingsMain;

public class PlayerEventListener implements Listener {

    private final iWorldSettingsMain plugin;

    public PlayerEventListener(iWorldSettingsMain plugin) {
        this.plugin = plugin;
    }

    private void setPlayerGameMode(Player player) {
        GameMode gameMode = plugin.getSettings(player.getWorld()).gameMode;
        if (gameMode != null) {
            player.setGameMode(gameMode);
        }
    }

    // HIGH so it runs after other plugins that set gamemode on join
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        setPlayerGameMode(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        setPlayerGameMode(event.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && plugin.getSettings(player.getWorld()).noDamage) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (plugin.getSettings(event.getEntity().getWorld()).noHunger) {
            event.setCancelled(true);
        }
    }
}

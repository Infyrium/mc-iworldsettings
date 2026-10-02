package net.infyrium.iworldsettings;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldLoadEvent;


public class BlockEventListener implements Listener {

    private final iWorldSettingsMain plugin;

    public BlockEventListener(iWorldSettingsMain plugin) {
        this.plugin = plugin;
    }

    private WorldSettings settings(World world) {
        return plugin.getSettings(world);
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        plugin.applyWorldSettings(event.getWorld());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (settings(event.getBlock().getWorld()).noBreak) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        if (settings(event.getBlock().getWorld()).noBreak) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (settings(event.getBlock().getWorld()).noPlace) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (settings(event.getPlayer().getWorld()).noInteract) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        if (settings(event.getBlock().getWorld()).noFluidFlow) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (settings(event.getEntity().getWorld()).noExplosions) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (settings(event.getBlock().getWorld()).noExplosions) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (settings(event.getWorld()).noWeather && event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLightning(LightningStrikeEvent event) {
        if (settings(event.getWorld()).noWeather) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (settings(event.getBlock().getWorld()).noFireSpread && event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        if (settings(event.getBlock().getWorld()).noFireSpread) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (settings(event.getBlock().getWorld()).noLeafDecay) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        if (settings(event.getBlock().getWorld()).noGrowth) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        if (settings(event.getBlock().getWorld()).noGrowth) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (settings(event.getEntity().getWorld()).noMobSpawn) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent event) {
        if (settings(event.getWorld()).noGrowth) {
            event.setCancelled(true);
        }
    }
}

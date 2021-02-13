package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class WorldListener implements Listener {

    private UltraCTW plugin;

    public WorldListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM) || e.getEntity().getType().equals(EntityType.PLAYER) || e.getEntity() instanceof Item) {
            return;
        }
        e.setCancelled(true);
    }

}
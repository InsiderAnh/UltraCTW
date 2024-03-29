package io.github.Leonardo0013YT.UltraCTW.addons;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.TextLine;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HologramsAddon {

    public HashMap<Location, Hologram> holograms = new HashMap<>();
    private HologramManager hologramManager;

    public HologramsAddon() {
        this.hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
    }

    public void createHologram(Location spawn, List<String> lines) {
        Hologram h = new Hologram(UUID.randomUUID().toString(), spawn.clone().add(0, 0.9 + (lines.size() * 0.3), 0), false);
        for (String l : lines) {
            HologramLine hl = new TextLine(h, l.replaceAll("&", "§"));
            h.addLine(hl);
        }
        h.spawn();
        hologramManager.addActiveHologram(h);
        holograms.put(spawn, h);
    }

    public void createHologram(Location spawn, List<String> lines, ItemStack item) {
        Hologram h = new Hologram(UUID.randomUUID().toString(), spawn.clone().add(0, 0.9 + (lines.size() * 0.3), 0), false);
        for (String l : lines) {
            HologramLine hl;
            if (l.equals("<item>")) {
                hl = new ItemLine(h, item);
            } else {
                hl = new TextLine(h, l.replaceAll("&", "§"));
            }
            h.addLine(hl);
        }
        h.spawn();
        hologramManager.addActiveHologram(h);
        holograms.put(spawn, h);
    }

    public void deleteHologram(Location spawn) {
        hologramManager.deleteHologram(holograms.get(spawn));
    }

    public boolean hasHologram(Location spawn) {
        return holograms.containsKey(spawn);
    }

    public void remove() {
        for (Hologram h : holograms.values()) {
            hologramManager.deleteHologram(holograms.get(h.getLocation()));
        }
        holograms.clear();
    }

}
package io.github.Leonardo0013YT.UltraCTW.addons;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class HolographicDisplaysAddon {

    private HashMap<Location, Hologram> holograms = new HashMap<>();

    public void createHologram(Location spawn, List<String> lines) {
        Hologram h = HologramsAPI.createHologram(UltraCTW.get(), spawn.clone().add(0, 1.3 + (lines.size() * 0.3), 0));
        for (String l : lines) {
            h.appendTextLine(l.replaceAll("&", "§"));
        }
        holograms.put(spawn, h);
    }

    public void createHologram(Location spawn, List<String> lines, ItemStack item) {
        Hologram h = HologramsAPI.createHologram(UltraCTW.get(), spawn.clone().add(0, 1.3 + (lines.size() * 0.3), 0));
        for (String l : lines) {
            if (l.equals("<item>")) {
                h.appendItemLine(item);
            } else {
                h.appendTextLine(l.replaceAll("&", "§"));
            }
        }
        holograms.put(spawn, h);
    }

    public void deleteHologram(Location spawn) {
        holograms.get(spawn).delete();
        holograms.remove(spawn);
    }

    public boolean hasHologram(Location spawn) {
        return holograms.containsKey(spawn);
    }

    public void remove() {
        for (Hologram h : holograms.values()) {
            h.delete();
        }
        holograms.clear();
    }

}
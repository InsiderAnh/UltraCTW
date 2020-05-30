package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.parting.Parting;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PartingManager {

    private HashMap<Integer, Parting> partings = new HashMap<>();
    private Main plugin;
    private int lastPage;

    public PartingManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadPartings() {
        partings.clear();
        if (plugin.getParting().isSet("partings")) {
            ConfigurationSection conf = plugin.getParting().getConfig().getConfigurationSection("partings");
            for (String c : conf.getKeys(false)) {
                int id = plugin.getParting().getInt("partings." + c + ".id");
                partings.put(id, new Parting(plugin, "partings." + c));
            }
        }
    }

    public int getNextId() {
        return partings.size();
    }

    public void executeParting(Player k, Player d, int id) {
        Parting parting = partings.get(id);
        if (parting == null) {
            return;
        }
        parting.execute(k, d);
    }

    public int getPartingsSize() {
        return partings.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (partings.containsKey(sw.getParting())) {
            return partings.get(sw.getParting()).getName();
        }
        return plugin.getLang().get(null, "messages.noSelected");
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        if (getLastPage() < lastPage) {
            this.lastPage = lastPage;
        }
    }

}
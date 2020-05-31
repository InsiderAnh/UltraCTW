package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.windances.*;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WinDancesManager {

    private HashMap<Integer, UltraWinDance> winDance = new HashMap<>();
    private Main plugin;
    private int lastPage;

    public WinDancesManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadWinDances() {
        winDance.clear();
        if (plugin.getWindance().isSet("windances")) {
            ConfigurationSection conf = plugin.getWindance().getConfig().getConfigurationSection("windances");
            for (String c : conf.getKeys(false)) {
                int id = plugin.getWindance().getInt("windances." + c + ".id");
                winDance.put(id, new UltraWinDance(plugin, "windances." + c));
            }
        }
    }

    public void execute(Game game, Player p, int id) {
        UltraWinDance uwe = winDance.get(id);
        if (uwe == null || uwe.getType().equals("none")) {
            return;
        }
        WinDance we;
        switch (uwe.getType()) {
            case "anvilland":
                we = new WinDanceAnvilLand();
                we.start(p);
                break;
            case "daynight":
                we = new WinDanceDayNight();
                we.start(p);
                break;
            case "explode":
                we = new WinDanceDestroyIsland();
                we.start(p);
                break;
            case "fireworks":
                we = new WinDanceFireworks();
                we.start(p);
                break;
            case "icewalker":
                we = new WinDanceIceWalker();
                we.start(p);
                break;
            default:
                we = new WinDanceThunder();
                we.start(p);
                break;
        }
        game.addWinDance(we);
    }

    public UltraWinDance getWinDanceByItem(Player p, ItemStack item) {
        for (UltraWinDance k : winDance.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, UltraWinDance> getWinDance() {
        return winDance;
    }

    public int getWinDancesSize() {
        return winDance.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (winDance.containsKey(sw.getWinDance())) {
            return winDance.get(sw.getWinDance()).getName();
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
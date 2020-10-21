package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.addons.HologramsAddon;
import io.github.Leonardo0013YT.UltraCTW.addons.HolographicDisplaysAddon;
import io.github.Leonardo0013YT.UltraCTW.addons.PlaceholderAPIAddon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AddonManager {

    private Main plugin;
    private PlaceholderAPIAddon placeholder;
    private HologramsAddon h;
    private HolographicDisplaysAddon hd;

    public AddonManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        if (plugin.getCm().isPlaceholdersAPI()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholder = new PlaceholderAPIAddon();
                plugin.sendLogMessage("Hooked into §aPlaceholderAPI§e!");
            } else {
                plugin.getConfig().set("addons.placeholdersAPI", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (h != null){
            h.remove();
        }
        if (hd != null){
            hd.remove();
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.getCm().isHolograms()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("Holograms")) {
                        h = new HologramsAddon();
                        plugin.sendLogMessage("Hooked into §aHolograms§e!");
                    } else {
                        plugin.getConfig().set("addons.holograms", false);
                        plugin.saveConfig();
                    }
                }
                if (plugin.getCm().isHolographicdisplays()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
                        hd = new HolographicDisplaysAddon();
                        plugin.sendLogMessage("Hooked into §aHolographicDisplays§e!");
                    } else {
                        plugin.getConfig().set("addons.holographicdisplays", false);
                        plugin.saveConfig();
                    }
                }
                plugin.getDb().loadTopBounty();
                plugin.getDb().loadTopCaptured();
                plugin.getDb().loadTopKills();
                plugin.getDb().loadTopWins();
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.get(), () -> plugin.getTop().createTops());
            }
        }.runTaskLater(plugin, 80);
    }

    public String parsePlaceholders(Player p, String value) {
        if (plugin.getCm().isPlaceholdersAPI()) {
            return placeholder.parsePlaceholders(p, value);
        }
        return value;
    }

    public double getCoins(Player p) {
        return plugin.getDb().getCTWPlayer(p).getCoins();
    }

    public void removeCoins(Player p, double price) {
        plugin.getDb().getCTWPlayer(p).removeCoins(price);
    }

    public void createHologram(Location spawn, List<String> lines) {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.createHologram(spawn, lines);
        } else if (plugin.getCm().isHolograms()) {
            h.createHologram(spawn, lines);
        }
    }

    public void remove() {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.remove();
        } else if (plugin.getCm().isHolograms()) {
            h.remove();
        }
    }

    public boolean hasHologram(Location spawn) {
        if (plugin.getCm().isHolographicdisplays()) {
            return hd.hasHologram(spawn);
        } else {
            return h.hasHologram(spawn);
        }
    }

    public void deleteHologram(Location spawn) {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.deleteHologram(spawn);
        } else if (plugin.getCm().isHolograms()) {
            h.deleteHologram(spawn);
        }
    }

    public boolean hasHologramPlugin() {
        return !(h == null && hd == null);
    }

}
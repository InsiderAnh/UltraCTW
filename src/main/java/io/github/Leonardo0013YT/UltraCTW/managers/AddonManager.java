package io.github.Leonardo0013YT.UltraSkyWars.managers;

import io.github.Leonardo0013YT.UltraSkyWars.Main;
import io.github.Leonardo0013YT.UltraSkyWars.addons.*;
import io.github.Leonardo0013YT.UltraSkyWars.addons.leaderheads.*;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.SWPlayer;
import io.github.Leonardo0013YT.UltraSkyWars.utils.Utils;
import me.robin.leaderheads.api.LeaderHeadsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {

    private Main plugin;
    private CoinsAddon coins;
    private VaultAddon vault;
    private PlayerPointsAddon points;
    private HologramsAddon h;
    private HolographicDisplaysAddon hd;
    private NametagEditAddon nte;
    private PartiesAddon parties;
    private TabAPI tabapi;
    private PlaceholderAPIAddon placeholder;
    private PAFAddon paf;
    private SlimeWorldManagerAddon slime;
    private MVdWPlaceholderAPIAddon mvdw;

    public AddonManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        if (plugin.getCm().isLeaderheads()) {
            if (Bukkit.getPluginManager().isPluginEnabled("LeaderHeads")) {
                new SkyWarsElo(plugin);
                new SkyWarsSoloKills(plugin);
                new SkyWarsTeamKills(plugin);
                new SkyWarsRankedKills(plugin);
                new SkyWarsSoloWins(plugin);
                new SkyWarsTeamWins(plugin);
                new SkyWarsRankedWins(plugin);
                new SkyWarsSoloDeaths(plugin);
                new SkyWarsTeamDeaths(plugin);
                new SkyWarsRankedDeaths(plugin);
                plugin.sendLogMessage("Hooked into §aLeaderHeads§e!");
            } else {
                plugin.getConfig().set("addons.leaderheads", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isPartyandfriends()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PartyAndFriends")) {
                paf = new PAFAddon();
                plugin.sendLogMessage("Hooked into §aPartyAndFriends§e!");
            } else {
                plugin.getConfig().set("addons.partyandfriends", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isSlimeworldmanager()) {
            if (Bukkit.getPluginManager().isPluginEnabled("SlimeWorldManager")) {
                slime = new SlimeWorldManagerAddon(plugin);
                plugin.sendLogMessage("Hooked into §aSlimeWorldManager§e!");
            } else {
                plugin.getConfig().set("addons.slimeworldmanager.enabled", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isMVdWPlaceholderAPI()) {
            if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
                mvdw = new MVdWPlaceholderAPIAddon();
                plugin.sendLogMessage("Hooked into §aMVdWPlaceholderAPI§e!");
            } else {
                plugin.getConfig().set("addons.MVdWPlaceholderAPI", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
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
        if (plugin.getCm().isTabapi()) {
            if (Bukkit.getPluginManager().isPluginEnabled("TAB")) {
                tabapi = new TabAPI();
                plugin.sendLogMessage("Hooked into §aTAB§e!");
            } else {
                plugin.getConfig().set("addons.tabapi", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isParties()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Parties")) {
                parties = new PartiesAddon();
                plugin.sendLogMessage("Hooked into §aParties§e!");
            } else {
                plugin.getConfig().set("addons.parties", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isNametagedit()) {
            if (Bukkit.getPluginManager().isPluginEnabled("NametagEdit")) {
                nte = new NametagEditAddon();
                plugin.sendLogMessage("Hooked into §aNametagEdit§e!");
            } else {
                plugin.getConfig().set("addons.nametagedit", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isVault()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                vault = new VaultAddon();
                plugin.sendLogMessage("Hooked into §aVault§e!");
            } else {
                plugin.getConfig().set("addons.vault", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isPlayerpoints()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
                points = new PlayerPointsAddon();
                plugin.sendLogMessage("Hooked into §aPlayerPoints§e!");
            } else {
                plugin.getConfig().set("addons.playerpoints", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        if (plugin.getCm().isCoins()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Coins")) {
                coins = new CoinsAddon();
                plugin.sendLogMessage("Hooked into §aCoins§e!");
            } else {
                plugin.getConfig().set("addons.coins", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
        new BukkitRunnable(){
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
                    plugin.getCbm().reload();
                }
                if (plugin.getCm().isHolographicdisplays()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
                        hd = new HolographicDisplaysAddon();
                        plugin.sendLogMessage("Hooked into §aHolographicDisplays§e!");
                    } else {
                        plugin.getConfig().set("addons.holographicdisplays", false);
                        plugin.saveConfig();
                    }
                    plugin.getCbm().reload();
                }
            }
        }.runTaskLater(plugin, 20);
    }

    public PAFAddon getPaf() {
        return paf;
    }

    public PartiesAddon getParties() {
        return parties;
    }

    public void addCoins(Player p, double amount) {
        if (plugin.getCm().isVault()) {
            vault.addCoins(p, amount);
        } else if (plugin.getCm().isPlayerpoints()) {
            points.addCoins(p, amount);
        } else if (plugin.getCm().isCoins()) {
            coins.addCoins(p, amount);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            sw.addCoins((int) amount);
        }
    }

    public void removeCoins(Player p, double amount) {
        if (plugin.getCm().isVault()) {
            vault.removeCoins(p, amount);
        } else if (plugin.getCm().isPlayerpoints()) {
            points.removeCoins(p, amount);
        } else if (plugin.getCm().isCoins()) {
            coins.removeCoins(p, amount);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            sw.removeCoins((int) amount);
        }
        Utils.updateSB(p);
    }

    public double getCoins(Player p) {
        if (plugin.getCm().isVault()) {
            return vault.getCoins(p);
        } else if (plugin.getCm().isPlayerpoints()) {
            return points.getCoins(p);
        } else if (plugin.getCm().isCoins()) {
            return coins.getCoins(p);
        } else {
            SWPlayer sw = plugin.getDb().getSWPlayer(p);
            return sw.getCoins();
        }
    }

    public void createHologram(Location spawn, List<String> lines) {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.createHologram(spawn, lines);
        } else if (plugin.getCm().isHolograms()) {
            h.createHologram(spawn, lines);
        }
    }

    public void createHologram(Location spawn, List<String> lines, ItemStack item) {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.createHologram(spawn, lines, item);
        } else if (plugin.getCm().isHolograms()) {
            h.createHologram(spawn, lines, item);
        }
    }

    public void remove() {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.remove();
        } else if (plugin.getCm().isHolograms()) {
            h.remove();
        }
    }

    public void deleteHologram(Location spawn) {
        if (plugin.getCm().isHolographicdisplays()) {
            hd.deleteHologram(spawn);
        } else if (plugin.getCm().isHolograms()) {
            h.deleteHologram(spawn);
        }
    }

    public void addPlayerNameTag(Player p) {
        if (nte != null) {
            nte.addPlayerNameTag(p);
        }
        if (tabapi != null) {
            tabapi.addPlayerNameTag(p);
        }
    }

    public void resetPlayerNameTag(Player p) {
        if (nte != null) {
            nte.resetPlayerNameTag(p);
        }
        if (tabapi != null) {
            tabapi.resetPlayerNameTag(p);
        }
    }

    public boolean hasHologram(Location spawn) {
        if (plugin.getCm().isHolographicdisplays()) {
            return hd.hasHologram(spawn);
        } else {
            return h.hasHologram(spawn);
        }
    }

    public String parsePlaceholders(Player p, String value) {
        if (plugin.getCm().isPlaceholdersAPI()) {
            return placeholder.parsePlaceholders(p, value);
        }
        if (plugin.getCm().isMVdWPlaceholderAPI()) {
            return mvdw.parsePlaceholders(p, value);
        }
        return value;
    }

    public SlimeWorldManagerAddon getSlime() {
        return slime;
    }

    public boolean hasHologramPlugin() {
        return !(h == null && hd == null);
    }

}
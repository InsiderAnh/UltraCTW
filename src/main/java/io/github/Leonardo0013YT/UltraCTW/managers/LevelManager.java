package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.Level;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LevelManager {

    private HashMap<Integer, Level> levels = new HashMap<>();
    private HashMap<Player, Level> playerLevel = new HashMap<>();
    private Main plugin;

    public LevelManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        levels.clear();
        if (!plugin.getLevels().isSet("levels")) return;
        ConfigurationSection conf = plugin.getLevels().getConfig().getConfigurationSection("levels");
        for (String c : conf.getKeys(false)) {
            levels.put(levels.size(), new Level(plugin, "levels." + c, levels.size()));
        }
    }

    public void checkUpgrade(Player p) {
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        if (sw == null) return;
        int level = sw.getLevel();
        Level lvl = getLevel(p);
        Level act = getLevelByLevel(level);
        if (lvl.getLevel() == act.getLevel()) {
            return;
        }
        if (levels.get(lvl.getId()).getLevel() > levels.get(act.getId()).getLevel()) {
            sw.setLevel(lvl.getLevel());
            p.playSound(p.getLocation(), plugin.getCm().getUpgradeSound(), 1.0f, 1.0f);
            p.sendMessage(plugin.getLang().get(p, "messages.levelUp.msg").replaceAll("<level>", lvl.getPrefix()));
            Utils.updateSB(p);
            for (Player on : Bukkit.getOnlinePlayers()) {
                on.sendMessage(plugin.getLang().get(p, "messages.levelUp.broadcast").replaceAll("<player>", p.getName()).replaceAll("<level>", lvl.getPrefix()));
            }
        }
    }

    public Level getLevel(Player p) {
        int elo = plugin.getDb().getCTWPlayer(p).getXp();
        for (Level lvl : levels.values()) {
            if (elo >= lvl.getXp() && elo < lvl.getLevelUp()) {
                playerLevel.put(p, lvl);
                return lvl;
            }
        }
        return levels.get(0);
    }

    public Level getLevelByLevel(int level) {
        for (Level l : levels.values()) {
            if (l.getLevel() == level) {
                return l;
            }
        }
        return null;
    }

    public String getLevelPrefix(Player p) {
        if (playerLevel.get(p) == null) {
            return plugin.getLang().get(p, "progressBar.max");
        }
        return playerLevel.get(p).getPrefix();
    }

    public HashMap<Player, Level> getPlayerLevel() {
        return playerLevel;
    }

    public HashMap<Integer, Level> getLevels() {
        return levels;
    }
}
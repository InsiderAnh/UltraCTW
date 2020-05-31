package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.*;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WinEffectsManager {

    private HashMap<Integer, UltraWinEffect> winEffects = new HashMap<>();
    private Main plugin;
    private int lastPage;

    public WinEffectsManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadWinEffects() {
        winEffects.clear();
        if (plugin.getWineffect().isSet("wineffects")) {
            ConfigurationSection conf = plugin.getWineffect().getConfig().getConfigurationSection("wineffects");
            for (String c : conf.getKeys(false)) {
                int id = plugin.getWineffect().getInt("wineffects." + c + ".id");
                winEffects.put(id, new UltraWinEffect(plugin, "wineffects." + c));
            }
        }
    }

    public void execute(Game game, Player p, int id) {
        UltraWinEffect uwe = winEffects.get(id);
        if (uwe == null || uwe.getType().equals("none")) {
            return;
        }
        WinEffect we;
        switch (uwe.getType()) {
            case "fireworks":
                we = new WinEffectFireworks();
                we.start(p);
                break;
            case "vulcanfire":
                we = new WinEffectVulcanFire();
                we.start(p);
                break;
            case "icewalker":
                we = new WinEffectIceWalker();
                we.start(p);
                break;
            case "notes":
                we = new WinEffectNotes();
                we.start(p);
                break;
            case "chickens":
                we = new WinEffectChicken();
                we.start(p);
                break;
            case "guardian":
                we = new WinEffectGuardians();
                we.start(p);
                break;
            default:
                we = new WinEffectVulcanWool();
                we.start(p);
                break;
        }
        game.addWinEffects(we);
    }

    public UltraWinEffect getWinEffectByItem(Player p, ItemStack item) {
        for (UltraWinEffect k : winEffects.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, UltraWinEffect> getWinEffects() {
        return winEffects;
    }

    public int getWinEffectsSize() {
        return winEffects.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (winEffects.containsKey(sw.getWinEffect())) {
            return winEffects.get(sw.getWinEffect()).getName();
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
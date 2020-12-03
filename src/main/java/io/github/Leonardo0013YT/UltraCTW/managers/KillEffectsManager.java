package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects.*;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KillEffectsManager {

    private HashMap<Integer, UltraKillEffect> killEffect = new HashMap<>();
    private UltraCTW plugin;
    private int lastPage;

    public KillEffectsManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadKillEffects() {
        killEffect.clear();
        if (plugin.getKilleffect().isSet("killeffects")) {
            ConfigurationSection conf = plugin.getKilleffect().getConfig().getConfigurationSection("killeffects");
            for (String c : conf.getKeys(false)) {
                int id = plugin.getKilleffect().getInt("killeffects." + c + ".id");
                killEffect.put(id, new UltraKillEffect(plugin, "killeffects." + c));
            }
        }
    }

    public void execute(Game game, Player p, Player death, Location loc, int id) {
        UltraKillEffect uwe = killEffect.get(id);
        if (uwe == null || uwe.getType().equals("none")) {
            return;
        }
        KillEffect we;
        switch (uwe.getType().toLowerCase()) {
            case "blood":
                we = new KillEffectBlood();
                we.start(p, death, loc);
                break;
            case "diamondexplode":
                we = new KillEffectDiamondExplode();
                we.start(p, death, loc);
                break;
            case "firework":
                we = new KillEffectFirework();
                we.start(p, death, loc);
                break;
            case "flower":
                we = new KillEffectFlowerPower();
                we.start(p, death, loc);
                break;
            case "squid":
                we = new KillEffectSquid();
                we.start(p, death, loc);
                break;
            case "thunder":
                we = new KillEffectThunder();
                we.start(p, death, loc);
                break;
            case "head":
                we = new KillEffectHead();
                we.start(p, death, loc);
                break;
            case "snow":
                we = new KillEffectSnowExplosion();
                we.start(p, death, loc);
                break;
            default:
                we = new KillEffectTNT();
                we.start(p, death, loc);
                break;
        }
        game.addKillEffects(we);
    }

    public void execute(GameFlag game, Player p, Player death, Location loc, int id) {
        UltraKillEffect uwe = killEffect.get(id);
        if (uwe == null || uwe.getType().equals("none")) {
            return;
        }
        KillEffect we;
        switch (uwe.getType().toLowerCase()) {
            case "blood":
                we = new KillEffectBlood();
                we.start(p, death, loc);
                break;
            case "diamondexplode":
                we = new KillEffectDiamondExplode();
                we.start(p, death, loc);
                break;
            case "firework":
                we = new KillEffectFirework();
                we.start(p, death, loc);
                break;
            case "flower":
                we = new KillEffectFlowerPower();
                we.start(p, death, loc);
                break;
            case "squid":
                we = new KillEffectSquid();
                we.start(p, death, loc);
                break;
            case "thunder":
                we = new KillEffectThunder();
                we.start(p, death, loc);
                break;
            case "head":
                we = new KillEffectHead();
                we.start(p, death, loc);
                break;
            case "snow":
                we = new KillEffectSnowExplosion();
                we.start(p, death, loc);
                break;
            default:
                we = new KillEffectTNT();
                we.start(p, death, loc);
                break;
        }
        game.addKillEffects(we);
    }

    public UltraKillEffect getKillEffectByItem(Player p, ItemStack item) {
        for (UltraKillEffect k : killEffect.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, UltraKillEffect> getKillEffect() {
        return killEffect;
    }

    public int getKillEffectSize() {
        return killEffect.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (killEffect.containsKey(sw.getKillEffect())) {
            return killEffect.get(sw.getKillEffect()).getName();
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
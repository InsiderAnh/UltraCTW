package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.kits.Kit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitManager {

    private HashMap<Integer, Kit> kits = new HashMap<>();
    private int defKit;
    private Main plugin;

    public KitManager(Main plugin) {
        this.plugin = plugin;
        if (!plugin.getKits().isSet("kits")) return;
        for (String k : plugin.getKits().getConfig().getConfigurationSection("kits").getKeys(false)){
            kits.put(plugin.getKits().getInt("kits." + k + ".id"), new Kit(plugin, "kits." + k));
        }
        this.defKit = plugin.getConfig().getInt("defKit");
    }

    public void giveDefaultKit(Player p){
        Kit kit = kits.get(defKit);
        if (kit == null) return;
        kit.giveKit(p, 1);
    }

    public void giveKit(Player p){

    }

    public HashMap<Integer, Kit> getKits() {
        return kits;
    }

}
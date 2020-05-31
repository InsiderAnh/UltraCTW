package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.kits.Kit;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitManager {

    private HashMap<Integer, Kit> kits = new HashMap<>();
    private Main plugin;

    public KitManager(Main plugin) {
        this.plugin = plugin;
        if (!plugin.getKits().isSet("kits")) return;
        for (String k : plugin.getKits().getConfig().getConfigurationSection("kits").getKeys(false)){
            kits.put(plugin.getKits().getInt("kits." + k + ".id"), new Kit(plugin, "kits." + k));
        }
    }

    public void giveDefaultKit(Player p, Game game, Team team){
        Kit kit = kits.get(game.getDefKit());
        if (kit == null) return;
        kit.giveKit(p, 1, team);
    }

    public void giveKit(Player p){

    }

    public int getNextID(){
        return kits.size();
    }

    public HashMap<Integer, Kit> getKits() {
        return kits;
    }

}
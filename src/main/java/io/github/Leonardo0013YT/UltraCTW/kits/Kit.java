package io.github.Leonardo0013YT.UltraCTW.kits;

import io.github.Leonardo0013YT.UltraCTW.Main;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class Kit {

    private HashMap<Integer, KitLevel> levels = new HashMap<>();
    private int id, slot, page;
    private String name, permission;

    public Kit(Main plugin, String path) {
        this.id = plugin.getKits().getInt(path + ".id");
        this.slot = plugin.getKits().getInt(path + ".slot");
        this.page = plugin.getKits().getInt(path + ".page");
        this.name = plugin.getKits().get(null, path + ".name");
        this.permission = plugin.getKits().get(null, path + ".permission");
    }

    public void giveKit(Player p, int level){
        if (levels.containsKey(level)){
            levels.get(level).giveKitLevel(p);
        }
    }

}
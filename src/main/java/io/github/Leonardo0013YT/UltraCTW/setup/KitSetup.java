package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter@Setter
public class KitSetup {

    private Main plugin;
    private String name, permission;
    private ItemStack icon;
    private int slot = 10, page = 1;
    private HashMap<Integer, KitLevelSetup> levels = new HashMap<>();
    private KitLevelSetup kls;

    public KitSetup(Main plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.permission = "UltraCTW.kits." + name;
    }

    public void saveKitLevel(){
        levels.put(kls.getLevel(), kls);
        kls = null;
    }

}
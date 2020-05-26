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

    public void save(){
        String n = "kits." + name;
        plugin.getKits().set(n + ".id", name);
        plugin.getKits().set(n + ".name", name);
        plugin.getKits().set(n + ".permission", permission);
        plugin.getKits().set(n + ".slot", slot);
        plugin.getKits().set(n + ".page", page);
        for (KitLevelSetup kls : levels.values()){
            String nl = "kits." + name + ".levels." + kls.getLevel();
            plugin.getKits().set(nl + ".level", kls.getLevel());
            plugin.getKits().set(nl + ".price", kls.getPrice());
            plugin.getKits().set(nl + ".slot", kls.getSlot());
            plugin.getKits().set(nl + ".page", kls.getPage());
            plugin.getKits().set(nl + ".icon", kls.getIcon());
            plugin.getKits().set(nl + ".armor", kls.getArmor());
            plugin.getKits().set(nl + ".inv", kls.getInv());
            plugin.getKits().set(nl + ".permission", permission + "." + kls.getLevel());
        }
        plugin.getKits().save();
    }

}
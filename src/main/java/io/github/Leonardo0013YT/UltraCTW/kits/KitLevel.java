package io.github.Leonardo0013YT.UltraCTW.kits;

import io.github.Leonardo0013YT.UltraCTW.Main;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class KitLevel {

    private ItemStack[] inv, armors;
    private ItemStack icon;
    private String permission;
    private int price, slot, level;

    public KitLevel(Main plugin, String path) {
        this.level = plugin.getKits().getInt(path + ".level");
        this.permission = plugin.getKits().get(null, path + ".permission");
        this.icon = plugin.getKits().getConfig().getItemStack(path + ".icon");
        this.price = plugin.getKits().getInt(path + ".price");
        this.slot = plugin.getKits().getInt(path + ".slot");
        this.armors = ((List<String>) plugin.getKits().getConfig().get(path + ".armor")).toArray(new ItemStack[0]);
        this.inv = ((List<String>) plugin.getKits().getConfig().get(path + ".inv")).toArray(new ItemStack[0]);
    }

    public void giveKitLevel(Player p) {
        p.getInventory().setArmorContents(armors);
        p.getInventory().setContents(inv);
    }


}
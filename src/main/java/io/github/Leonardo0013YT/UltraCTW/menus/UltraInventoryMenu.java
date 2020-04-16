package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.inventories.SetupArenaMenu;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class UltraInventoryMenu {

    private HashMap<String, UltraInventory> menus = new HashMap<>();
    private HashMap<Player, Integer> pages = new HashMap<>();
    private Main plugin;

    public UltraInventoryMenu(Main plugin) {
        this.plugin = plugin;
        loadMenus();
    }

    public void loadMenus() {
        menus.clear();
        menus.put("setup", new SetupArenaMenu(plugin, "setup"));
    }

    public UltraInventory getMenus(String t) {
        return menus.get(t);
    }

    public void openInventory(Player p, UltraInventory i) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (Map.Entry<Integer, ItemStack> entry : i.getConfig().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue();
            inv.setItem(s, it);
        }
        p.openInventory(inv);
    }

    public Inventory openContentInventory(Player p, UltraInventory i) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (Map.Entry<Integer, ItemStack> entry : i.getContents().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue();
            inv.setItem(s, it);
        }
        p.openInventory(inv);
        return inv;
    }

    public void openInventory(Player p, UltraInventory i, String[]... t) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (Map.Entry<Integer, ItemStack> entry : i.getContents().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue().clone();
            inv.setItem(s, ItemBuilder.parseVariables(p, it, t));
        }
        p.openInventory(inv);
    }

    public void setInventory(String inv, Inventory close) {
        if (menus.containsKey(inv)) {
            Map<Integer, ItemStack> items = new HashMap<>();
            for (int i = 0; i < close.getSize(); i++) {
                ItemStack it = close.getItem(i);
                if (it == null || it.getType().equals(Material.AIR)) {
                    continue;
                }
                items.put(i, it);
            }
            menus.get(inv).setConfig(items);
            menus.get(inv).save();
        }
    }

    public HashMap<Player, Integer> getPages() {
        return pages;
    }

    public void addPage(Player p) {
        pages.put(p, pages.get(p) + 1);
    }

    public void removePage(Player p) {
        pages.put(p, pages.get(p) - 1);
    }

}
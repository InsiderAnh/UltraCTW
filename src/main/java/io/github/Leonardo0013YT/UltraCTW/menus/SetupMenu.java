package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SetupMenu {

    private int[] slots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 31};
    private Main plugin;

    public SetupMenu(Main plugin) {
        this.plugin = plugin;
    }

    public void createSetupSelectMenu(Player p){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.teamsColor.title"));
        int i = 0;
        for (ChatColor color : ChatColor.values()){
            if (color.isFormat() || color.equals(ChatColor.RESET) || color.equals(ChatColor.DARK_RED) || color.equals(ChatColor.DARK_BLUE)) continue;
            ItemStack wool = NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(color)).setDisplayName(plugin.getLang().get("menus.teamsColor.color.nameItem").replaceAll("<name>", plugin.getLang().get("teams." + color.name().toLowerCase())).replaceAll("<color>", "" + color)).setLore(plugin.getLang().get("menus.teamsColor.color.loreItem")).build(), color.name(), "SETUP", "TEAM", "COLOR");
            inv.setItem(slots[i], wool);
            i++;
        }
        ItemStack BACK = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.back.nameItem")).setLore(plugin.getLang().get("menus.back.loreItem").replaceAll("<menu>", "Main Setup Menu")).build();
        p.openInventory(inv);
    }

}
package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SetupMenu {

    private Main plugin;

    public SetupMenu(Main plugin) {
        this.plugin = plugin;
    }

    public void createSetupMenu(Player p){
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get("menus.setup.title"));
        ItemStack lobby = new ItemUtils(XMaterial.COMPASS).setDisplayName(plugin.getLang().get("menus.setup.lobby.nameItem")).setLore(plugin.getLang().get("menus.setup.lobby.loreItem")).build();
        ItemStack spect = new ItemUtils(XMaterial.COMPASS).setDisplayName(plugin.getLang().get("menus.setup.spect.nameItem")).setLore(plugin.getLang().get("menus.setup.spect.loreItem")).build();
        ItemStack name = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("menus.setup.name.nameItem")).setLore(plugin.getLang().get("menus.setup.name.loreItem")).build();
        ItemStack schematic = new ItemUtils(XMaterial.BOOK).setDisplayName(plugin.getLang().get("menus.setup.schematic.nameItem")).setLore(plugin.getLang().get("menus.setup.schematic.loreItem")).build();
        ItemStack min = new ItemUtils(XMaterial.WRITABLE_BOOK).setDisplayName(plugin.getLang().get("menus.setup.min.nameItem")).setLore(plugin.getLang().get("menus.setup.min.loreItem")).build();
        ItemStack teamSize = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("menus.setup.teamSize.nameItem")).setLore(plugin.getLang().get("menus.setup.teamSize.loreItem")).build();
        inv.setItem(3, lobby);
        inv.setItem(5, spect);
        inv.setItem(11, name);
        inv.setItem(13, schematic);
        inv.setItem(15, min);
        inv.setItem(22, teamSize);
        p.openInventory(inv);
    }

}
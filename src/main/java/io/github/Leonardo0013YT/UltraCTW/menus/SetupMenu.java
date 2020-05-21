package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.KitLevelSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.KitSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.TeamSetup;
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
    private int[] whites = {4, 5, 6, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private Main plugin;

    public SetupMenu(Main plugin) {
        this.plugin = plugin;
    }

    public void createSetupKitMenu(Player p, KitSetup ks){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.kitsetup.title"));
        ItemStack name = new ItemUtils(XMaterial.OAK_SIGN).setDisplayName(plugin.getLang().get("menus.kitsetup.name.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.name.loreItem")).build();
        ItemStack icon = new ItemUtils(XMaterial.DIAMOND_SWORD).setDisplayName(plugin.getLang().get("menus.kitsetup.icon.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.icon.loreItem")).build();
        ItemStack levels = new ItemUtils(XMaterial.EXPERIENCE_BOTTLE).setDisplayName(plugin.getLang().get("menus.kitsetup.levels.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.levels.loreItem")).build();
        ItemStack permission = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.kitsetup.permission.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.permission.loreItem")).build();
        ItemStack slot = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("menus.kitsetup.slot.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.slot.loreItem")).build();
        ItemStack page = new ItemUtils(XMaterial.MAP).setDisplayName(plugin.getLang().get("menus.kitsetup.page.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.page.loreItem")).build();
        ItemStack save = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("menus.kitsetup.save.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.save.loreItem")).build();
        inv.setItem(4, name);
        inv.setItem(11, icon);
        inv.setItem(13, levels);
        inv.setItem(15, permission);
        inv.setItem(21, slot);
        inv.setItem(23, page);
        inv.setItem(40, save);
        p.openInventory(inv);
    }

    public void createSetupKitLevelsMenu(Player p, KitLevelSetup kls){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.kitlevelssetup.title"));
        ItemStack price = new ItemUtils(XMaterial.GOLD_NUGGET).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.price.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.price.loreItem")).build();
        ItemStack items = new ItemUtils(XMaterial.CHEST).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.items.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.items.loreItem")).build();
        ItemStack buy = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.buy.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.buy.loreItem")).build();
        ItemStack slot = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.slot.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.slot.loreItem")).build();
        ItemStack page = new ItemUtils(XMaterial.MAP).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.page.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.page.loreItem")).build();
        ItemStack save = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.save.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.save.loreItem")).build();
        inv.setItem(11, price);
        inv.setItem(13, items);
        inv.setItem(15, buy);
        inv.setItem(21, slot);
        inv.setItem(23, page);
        inv.setItem(40, save);
        p.openInventory(inv);
    }

    public void createSetupKitItemsMenu(Player p){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.kititems.title"));
        ItemStack white = new ItemUtils(XMaterial.WHITE_STAINED_GLASS_PANE).setDisplayName("§7").setLore("§7").build();
        ItemStack helmet = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cHelmet").setLore("§7Click to change!").build();
        ItemStack chestplate = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cChestplate").setLore("§7Click to change!").build();
        ItemStack leggings = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cLeggings").setLore("§7Click to change!").build();
        ItemStack boots = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cBoots").setLore("§7Click to change!").build();
        ItemStack analize = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.kititems.analize.nameItem")).setLore(plugin.getLang().get("menus.kititems.analize.nameItem")).build();
        ItemStack save = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.kititems.save.nameItem")).setLore(plugin.getLang().get("menus.kititems.save.nameItem")).build();
        for (int i : whites){
            inv.setItem(i, white);
        }
        inv.setItem(0, helmet);
        inv.setItem(1, chestplate);
        inv.setItem(2, leggings);
        inv.setItem(3, boots);
        inv.setItem(7, analize);
        inv.setItem(8, save);
        p.openInventory(inv);
    }

    public void createSetupSelectMenu(Player p, ArenaSetup as){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.teamsColor.title"));
        int i = 0;
        int max = as.getAmountTeams();
        int amount = 0;
        for (ChatColor color : ChatColor.values()){
            if (color.isFormat() || color.equals(ChatColor.BLACK) || color.equals(ChatColor.RESET) || color.equals(ChatColor.DARK_RED) || color.equals(ChatColor.DARK_BLUE)) continue;
            ItemStack wool = NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(color)).setDisplayName(plugin.getLang().get("menus.teamsColor.color.nameItem").replaceAll("<name>", plugin.getLang().get("teams." + color.name().toLowerCase())).replaceAll("<color>", "" + color)).setLore(plugin.getLang().get("menus.teamsColor.color.loreItem")).build(), color.name(), "SETUP", "TEAM", "COLOR");
            inv.setItem(slots[i], wool);
            amount++;
            i++;
            if (amount >= max){
                break;
            }
        }
        ItemStack back = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.back.nameItem")).setLore(plugin.getLang().get("menus.back.loreItem").replaceAll("<menu>", "Main Setup Menu")).build();
        inv.setItem(40, back);
        p.openInventory(inv);
    }

    public void createSetupColorTeam(Player p, ArenaSetup as){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.teamColor.title"));
        int i = 0;
        for (ChatColor xm : as.getAvailableColors()){
            inv.setItem(slots[i], NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(xm)).setDisplayName(xm + "Team " + plugin.getLang().get("teams." + xm.name().toLowerCase())).build(), xm.name(), "SELECT", "TEAM", "COLORS"));
            i++;
        }
        p.openInventory(inv);
    }

    public void createSetupSpawnerColor(Player p, TeamSetup ts){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.spawners.title"));
        int i = 0;
        for (ChatColor xm : ts.getColors()) {
            inv.setItem(slots[i], NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(xm)).setDisplayName(xm + "Spawner " + plugin.getLang().get("teams." + xm.name().toLowerCase())).build(), xm.name(), "SELECT", "TEAM", "SPAWNER"));
            i++;
        }
        p.openInventory(inv);
    }

}
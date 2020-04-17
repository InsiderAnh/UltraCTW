package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.TeamSetup;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetupListener implements Listener {

    private Main plugin;

    public SetupListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)){
            return;
        }
        ItemStack item = p.getItemInHand();
        if (item.equals(plugin.getIm().getSetup())){
            if (!plugin.getSm().isSetup(p)){
                return;
            }
            ArenaSetup as = plugin.getSm().getSetup(p);
            plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                    new String[]{"<name>", as.getName()},
                    new String[]{"<schematic>", as.getSchematic()},
                    new String[]{"<min>", "" + as.getMin()},
                    new String[]{"<teamSize>", "" + as.getTeamSize()},
                    new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                    new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
        }
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e){
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE) || e.getClickedInventory().getType().equals(InventoryType.PLAYER) || e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.setup.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            ItemStack item = e.getCurrentItem();
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get("menus.back.nameItem"))){
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                return;
            }
            String c = NBTEditor.getString(item, "SETUP", "TEAM", "COLOR");
            if (c == null){
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            plugin.getSm().setSetupTeam(p, new TeamSetup(color));
            plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                    new String[]{"<color>", as.getName()},
                    new String[]{"<generators>", as.getSchematic()},
                    new String[]{"<spawn>", "" + as.getMin()});

        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.setup.title"))){
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            ItemMeta im = e.getCurrentItem().getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.setup.teams.nameItem"))) {
                if (!plugin.getSm().isSetupTeam(p)){
                    plugin.getSem().createSetupSelectMenu(p);
                } else {
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                            new String[]{"<color>", as.getName()},
                            new String[]{"<generators>", as.getSchematic()},
                            new String[]{"<spawn>", "" + as.getMin()});
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.min.nameItem"))) {
                plugin.getSm().setSetupName(p, "min");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setMin"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.teamSize.nameItem"))) {
                plugin.getSm().setSetupName(p, "teamsize");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setTeamSize"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.lobby.nameItem"))) {
                as.setLobby(p.getLocation());
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setLobby"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.spect.nameItem"))) {
                as.setSpectator(p.getLocation());
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setSpect"));
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (plugin.getSm().isSetupInventory(p)) {
            UltraInventory i = plugin.getSm().getSetupInventory(p);
            plugin.getUim().setInventory(i.getName(), e.getInventory());
            plugin.getSm().removeInventory(p);
            p.sendMessage(plugin.getLang().get(p, "setup.menus.finishEdit"));
        }
    }

}
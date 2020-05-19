package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.TeamSetup;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.utils.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SetupListener implements Listener {

    private Main plugin;

    public SetupListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (plugin.getSm().isSetup(p)) {
            if (plugin.getSm().isSetupName(p)) {
                e.setCancelled(true);
                ArenaSetup as = plugin.getSm().getSetup(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("min")) {
                    int min;
                    try {
                        min = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (min < 2) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minMin"));
                        return;
                    }
                    as.setMin(min);
                    plugin.getSm().removeName(p);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
                if (type.equals("teamsize")) {
                    int teamsize;
                    try {
                        teamsize = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (teamsize < 1) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minTeamSize"));
                        return;
                    }
                    as.setTeamSize(teamsize);
                    plugin.getSm().removeName(p);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
                if (type.equals("woolsize")) {
                    int woolsize;
                    try {
                        woolsize = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (woolsize < 1) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minWoolSize"));
                        return;
                    }
                    as.setWoolSize(woolsize);
                    plugin.getSm().removeName(p);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
                if (type.equals("amountteams")) {
                    int amountteams;
                    try {
                        amountteams = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (amountteams < 2) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minAmountTeams"));
                        return;
                    }
                    as.setAmountTeams(amountteams);
                    plugin.getSm().removeName(p);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
            }
        }
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
                    new String[]{"<woolSize>", "" + as.getWoolSize()},
                    new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                    new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                    new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (!plugin.getSm().isSetup(p)){
                return;
            }
            ArenaSetup as = plugin.getSm().getSetup(p);
            if (as.getActual() == null){
                return;
            }
            if (item.getType().name().contains("WOOL")){
                XMaterial ma = XMaterial.matchXMaterial(item);
                TeamSetup ts = as.getActual();
                Block b = e.getClickedBlock();
                ts.getWools().put(Utils.getColorByXMaterial(ma), b.getLocation());
                removeItemInHand(p);
                p.sendMessage(plugin.getLang().get("setup.arena.addWool").replaceAll("<loc>", Utils.getFormatedLocation(b.getLocation())));
            }
        }
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e){
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE) || e.getClickedInventory().getType().equals(InventoryType.PLAYER) || e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (plugin.getSm().isSetupInventory(p)){
            return;
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamColor.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            TeamSetup ts = plugin.getSm().getSetupTeam(p);
            ItemStack item = e.getCurrentItem();
            String c = NBTEditor.getString(item, "SELECT", "TEAM", "COLORS");
            if (c == null){
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            ts.getColors().add(color);
            if (ts.getColors().size() >= as.getWoolSize()){
                p.sendMessage(plugin.getLang().get("setup.arena.setColors"));
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
                return;
            }
            p.sendMessage(plugin.getLang().get("setup.arena.addColor").replaceAll("<color>", plugin.getLang().get("teams." + color.name().toLowerCase())));
            plugin.getSem().createSetupColorTeam(p, as);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamsetup.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            TeamSetup ts = plugin.getSm().getSetupTeam(p);
            ItemMeta im = e.getCurrentItem().getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.wools.nameItem"))) {
                if (ts.getColors().isEmpty() || ts.getColors().size() < as.getWoolSize()){
                    p.sendMessage(plugin.getLang().get("setup.team.firstTeamColors"));
                    return;
                }
                ArrayList<ChatColor> wools = ts.getColors();
                wools.forEach(w -> p.getInventory().addItem(Utils.getXMaterialByColor(w).parseItem()));
                p.sendMessage(plugin.getLang().get("setup.team.giveAvailableWools"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.spawn.nameItem"))) {
                ts.setSpawn(p.getLocation());
                p.sendMessage(plugin.getLang().get("setup.arena.setSpawn").replaceAll("<location>", Utils.getFormatedLocation(p.getLocation())));
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});

            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.spawner.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)){
                    if (ts.getSpawners().size() == 0){
                        p.sendMessage(plugin.getLang().get("setup.arena.noSpawner"));
                        return;
                    }
                    ts.getSpawners().remove(ts.getSpawners().size() - 1);
                    p.sendMessage(plugin.getLang().get("setup.arena.spawnerRemoved"));
                } else {
                    ts.getSpawners().add(p.getLocation());
                    p.sendMessage(plugin.getLang().get("setup.arena.addSpawner").replaceAll("<location>", Utils.getFormatedLocation(p.getLocation())));
                }
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.wool.nameItem"))) {
                if (ts.getColors().size() >= as.getWoolSize()){
                    p.sendMessage(plugin.getLang().get("setup.arena.alreadySetWool"));
                    return;
                }
                plugin.getSem().createSetupColorTeam(p, as);
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.save.nameItem"))) {
                as.saveTeam();
                p.sendMessage(plugin.getLang().get("setup.arena.teamSaved"));
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamsColor.title"))) {
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
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                return;
            }
            String c = NBTEditor.getString(item, "SETUP", "TEAM", "COLOR");
            if (c == null){
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            TeamSetup ts = new TeamSetup(color);
            plugin.getSm().setSetupTeam(p, ts);
            ArrayList<String> sp = new ArrayList<>();
            ts.getSpawners().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
            plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                    new String[]{"<color>", ts.getColor().name()},
                    new String[]{"<generators>", getString(sp)},
                    new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            p.sendMessage(plugin.getLang().get("setup.arena.createDontWools"));
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.setup.title"))){
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            ItemMeta im = e.getCurrentItem().getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.setup.teams.nameItem"))) {
                if (!plugin.getSm().isSetupTeam(p)){
                    plugin.getSem().createSetupSelectMenu(p, as);
                } else {
                    TeamSetup ts = plugin.getSm().getSetupTeam(p);
                    as.setActual(ts);
                    ArrayList<String> sp = new ArrayList<>();
                    ts.getSpawners().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                            new String[]{"<color>", ts.getColor().name()},
                            new String[]{"<generators>", getString(sp)},
                            new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
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
            if (display.equals(plugin.getLang().get(p, "menus.setup.teamsAmount.nameItem"))) {
                plugin.getSm().setSetupName(p, "amountteams");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setAmountTeams"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.woolSize.nameItem"))) {
                if (as.getTeams().size() > 0){
                    p.sendMessage(plugin.getLang().get("setup.arena.alreadyTeam"));
                    return;
                }
                plugin.getSm().setSetupName(p, "woolsize");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setWoolSize"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.lobby.nameItem"))) {
                as.setLobby(p.getLocation());
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
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
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
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

    public String getString(ArrayList<String> list){
        StringBuilder r = new StringBuilder();
        for (String s : list){
            r.append("<newLine>").append(s);
        }
        return r.toString().replaceFirst("<newLine>", "");
    }

    private void removeItemInHand(Player p) {
        ItemStack item = p.getItemInHand();
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            p.setItemInHand(null);
        }
    }

}
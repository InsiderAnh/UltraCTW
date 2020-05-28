package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.objects.Selection;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.KitLevelSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.KitSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.TeamSetup;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.TreeMap;

public class SetupListener implements Listener {

    private Main plugin;

    public SetupListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (plugin.getSm().isSetupName(p)) {
            if (plugin.getSm().isSetup(p)) {
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
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()){
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
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
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()){
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
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
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()){
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
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
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()){
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
            }
            if (plugin.getSm().isSetupKit(p)) {
                e.setCancelled(true);
                KitSetup ks = plugin.getSm().getSetupKit(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("kitpermission")) {
                    ks.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitMenu(p, ks);
                }
                if (type.equals("kitslot")) {
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.setSlot(slot);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitMenu(p, ks);
                }
                if (type.equals("kitpage")) {
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.setPage(page);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitMenu(p, ks);
                }
                if (type.equals("kitlevelslot")) {
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.getKls().setSlot(slot);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
                }
                if (type.equals("kitlevelpage")) {
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.getKls().setPage(page);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
                }
                if (type.equals("kitlevelprice")) {
                    double price;
                    try {
                        price = Double.parseDouble(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.getKls().setPrice(price);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
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
            ArrayList<String> sq = new ArrayList<>();
            for (Squared s : as.getSquareds()){
                sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                sq.add("§7");
            }
            plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                    new String[]{"<name>", as.getName()},
                    new String[]{"<schematic>", as.getSchematic()},
                    new String[]{"<min>", "" + as.getMin()},
                    new String[]{"<teamSize>", "" + as.getTeamSize()},
                    new String[]{"<woolSize>", "" + as.getWoolSize()},
                    new String[]{"<squareds>", "" + getString(sq)},
                    new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                    new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                    new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (plugin.getSm().isSetup(p)){
                ArenaSetup as = plugin.getSm().getSetup(p);
                if (item.equals(plugin.getIm().getPoints())){
                    as.getSelection().setPos2(e.getClickedBlock().getLocation());
                    p.sendMessage(plugin.getLang().get("setup.setPosition").replaceAll("<pos>", "2"));
                }
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
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (plugin.getSm().isSetup(p)) {
                ArenaSetup as = plugin.getSm().getSetup(p);
                if (item.equals(plugin.getIm().getPoints())) {
                    e.setCancelled(true);
                    as.getSelection().setPos1(e.getClickedBlock().getLocation());
                    p.sendMessage(plugin.getLang().get("setup.setPosition").replaceAll("<pos>", "1"));
                }
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
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.kititems.title"))) {
            KitSetup ks = plugin.getSm().getSetupKit(p);
            KitLevelSetup kls = ks.getKls();
            ItemStack item = e.getCurrentItem();
            String display = item.getItemMeta().getDisplayName();
            if (e.getSlot() == 0) {
                e.setCancelled(true);
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[3]);
                            kls.getArmor()[3] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    kls.getArmor()[3] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (e.getSlot() == 1){
                e.setCancelled(true);
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[2]);
                            kls.getArmor()[2] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    kls.getArmor()[2] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (e.getSlot() == 2){
                e.setCancelled(true);
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[1]);
                            kls.getArmor()[1] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    kls.getArmor()[1] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (e.getSlot() == 3){
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[0]);
                            kls.getArmor()[0] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    kls.getArmor()[0] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (plugin.getSem().getWhites().contains(e.getSlot())){
                e.setCancelled(true);
            }
            if (e.getSlot() == 7){
                e.setCancelled(true);
            }
            if (e.getSlot() == 8) {
                e.setCancelled(true);
                for (int i = 10; i < 36; i++) {
                    kls.getInv()[i] = e.getInventory().getItem(i);
                }
                for (int i = 45; i < 54; i++) {
                    kls.getInv()[i - 45] = e.getInventory().getItem(i);
                }
                p.sendMessage(plugin.getLang().get("setup.kits.save"));
                plugin.getSem().createSetupKitLevelsMenu(p, kls);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.kitlevelssetup.title"))) {
            e.setCancelled(true);
            KitSetup ks = plugin.getSm().getSetupKit(p);
            KitLevelSetup kls = ks.getKls();
            ItemStack item = e.getCurrentItem();
            String display = item.getItemMeta().getDisplayName();
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.icon.nameItem"))){
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    p.sendMessage(plugin.getLang().get(p, "setup.noCursor"));
                    return;
                }
                ItemStack it = p.getItemOnCursor();
                if (it.hasItemMeta()) {
                    ItemMeta imt = it.getItemMeta();
                    imt.setDisplayName("§a" + ks.getName());
                    imt.setLore(null);
                    it.setItemMeta(imt);
                }
                kls.setIcon(it);
                p.sendMessage(plugin.getLang().get(p, "setup.kits.setIcon"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.slot.nameItem"))){
                plugin.getSm().setSetupName(p, "kitlevelslot");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setSlot"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.page.nameItem"))){
                plugin.getSm().setSetupName(p, "kitlevelpage");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPage"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.buy.nameItem"))){
                kls.setBuy(!kls.isBuy());
                p.sendMessage(plugin.getLang().get(p, "setup.setBuy").replaceAll("<state>", Utils.parseBoolean(kls.isBuy())));
                plugin.getSem().createSetupKitLevelsMenu(p, kls);
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.price.nameItem"))){
                plugin.getSm().setSetupName(p, "kitlevelprice");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPrice"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.items.nameItem"))) {
                plugin.getSem().createSetupKitItemsMenu(p, kls);
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.save.nameItem"))) {
                ks.saveKitLevel();
                plugin.getSem().createSetupKitMenu(p, ks);
                p.sendMessage(plugin.getLang().get("setup.kits.saveLevel"));
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.kitsetup.title"))) {
            e.setCancelled(true);
            KitSetup ks = plugin.getSm().getSetupKit(p);
            ItemStack item = e.getCurrentItem();
            String display = item.getItemMeta().getDisplayName();
            if (display.equals(plugin.getLang().get("menus.kitsetup.permission.nameItem"))){
                plugin.getSm().setSetupName(p, "kitpermission");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPermission"));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.slot.nameItem"))){
                plugin.getSm().setSetupName(p, "kitslot");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setSlot"));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.page.nameItem"))){
                plugin.getSm().setSetupName(p, "kitpage");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPage"));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.levels.nameItem"))){
                if (ks.getKls() == null) {
                    ks.setKls(new KitLevelSetup(ks.getLevels().size() + 1));
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.newLevel"));
                }
                plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.save.nameItem"))){
                plugin.getSm().removeSetupKit(p);
                ks.save();
                p.closeInventory();
                p.sendMessage(plugin.getLang().get("setup.kits.saveKit"));
            }
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
                p.closeInventory();
                p.sendMessage(plugin.getLang().get("setup.arena.setColors"));
                ArrayList<ChatColor> wools = ts.getColors();
                wools.forEach(w -> p.getInventory().addItem(Utils.getXMaterialByColor(w).parseItem()));
                p.sendMessage(plugin.getLang().get("setup.team.giveAvailableWools"));
                return;
            }
            p.sendMessage(plugin.getLang().get("setup.arena.addColor").replaceAll("<color>", plugin.getLang().get("teams." + color.name().toLowerCase())));
            plugin.getSem().createSetupColorTeam(p, as);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.spawners.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            TeamSetup ts = plugin.getSm().getSetupTeam(p);
            ItemStack item = e.getCurrentItem();
            String c = NBTEditor.getString(item, "SELECT", "TEAM", "SPAWNER");
            if (c == null){
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            ts.getSpawners().put(color, p.getLocation());
            p.sendMessage(plugin.getLang().get("setup.arena.addSpawner").replaceAll("<location>", Utils.getFormatedLocation(p.getLocation())));
            if (ts.getSpawners().size() >= ts.getColors().size()){
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : ts.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<squareds>", getString(sq)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            } else {
                plugin.getSem().createSetupSpawnerColor(p, ts);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamsetup.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            TeamSetup ts = plugin.getSm().getSetupTeam(p);
            ItemMeta im = e.getCurrentItem().getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.spawn.nameItem"))) {
                ts.setSpawn(p.getLocation());
                p.sendMessage(plugin.getLang().get("setup.arena.setSpawn").replaceAll("<location>", Utils.getFormatedLocation(p.getLocation())));
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : ts.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<squareds>", getString(sq)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});

            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.squared.nameItem"))) {
                Selection s = as.getSelection();
                if (s.getPos1() == null || s.getPos2() == null){
                    p.sendMessage(plugin.getLang().get("setup.arena.needPositions"));
                    return;
                }
                ts.addSquared(s);
                p.sendMessage(plugin.getLang().get("setup.arena.setProteccion"));
                s.setPos1(null);
                s.setPos2(null);
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared ss : ts.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(ss.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(ss.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<squareds>", getString(sq)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.spawner.nameItem"))) {
                if (ts.getColors().size() < as.getWoolSize()){
                    p.sendMessage(plugin.getLang().get("setup.arena.firstWool"));
                    return;
                }
                if (e.getClick().equals(ClickType.RIGHT)){
                    if (ts.getSpawners().isEmpty()){
                        p.sendMessage(plugin.getLang().get("setup.team.noSpawners"));
                        return;
                    }
                    TreeMap<ChatColor, Location> tm = new TreeMap<>(ts.getSpawners());
                    tm.remove(tm.lastKey());
                    p.sendMessage(plugin.getLang().get("setup.team.removed"));
                } else {
                    plugin.getSem().createSetupSpawnerColor(p, ts);
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.wool.nameItem"))) {
                if (ts.getColors().size() >= as.getWoolSize()){
                    p.sendMessage(plugin.getLang().get("setup.arena.alreadySetWool"));
                    return;
                }
                plugin.getSem().createSetupColorTeam(p, as);
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.save.nameItem"))) {
                if (ts.getSpawn() == null){
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.spawn"));
                    return;
                }
                if (ts.getColors().size() < as.getWoolSize()){
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.noColors"));
                    return;
                }
                if (ts.getSpawners().size() < as.getWoolSize()){
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.noSpawner"));
                    return;
                }
                plugin.getSm().removeTeam(p);
                as.saveTeam();
                p.sendMessage(plugin.getLang().get("setup.arena.teamSaved"));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
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
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
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
            ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
            ArrayList<String> sq = new ArrayList<>();
            for (Squared s : ts.getSquareds()){
                sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                sq.add("§7");
            }
            plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                    new String[]{"<color>", ts.getColor().name()},
                    new String[]{"<generators>", getString(sp)},
                    new String[]{"<squareds>", getString(sq)},
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
                    ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : ts.getSquareds()){
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                            new String[]{"<color>", ts.getColor().name()},
                            new String[]{"<generators>", getString(sp)},
                            new String[]{"<squareds>", getString(sq)},
                            new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.squared.nameItem"))) {
                Selection s = as.getSelection();
                if (s.getPos1() == null || s.getPos2() == null){
                    p.sendMessage(plugin.getLang().get("setup.arena.needPositions"));
                    return;
                }
                as.addSquared(s);
                p.sendMessage(plugin.getLang().get("setup.arena.setProteccion"));
                s.setPos1(null);
                s.setPos2(null);
                ArrayList<String> sq = new ArrayList<>();
                for (Squared ss : as.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(ss.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(ss.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
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
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setLobby"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.spect.nameItem"))) {
                as.setSpectator(p.getLocation());
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()){
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setSpect"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.save.nameItem"))) {
                if (as.getLobby() == null){
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.lobby"));
                    return;
                }
                if (as.getSpectator() == null){
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.spectator"));
                    return;
                }
                if (as.getTeams().size() < 2){
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.needTwoTeams"));
                    return;
                }
                p.closeInventory();
                as.save(p);
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
        if (list.isEmpty()){
            return "§cEmpty";
        }
        StringBuilder r = new StringBuilder();
        for (String s : list){
            r.append("<newLine>").append("§e").append(s);
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
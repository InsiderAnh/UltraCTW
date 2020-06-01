package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects.UltraKillEffect;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds.KillSound;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts.Taunt;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.windances.UltraWinDance;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.UltraWinEffect;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuListener implements Listener {

    private Main plugin;

    public MenuListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e){
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE) || e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teams.title"))){
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()){
                return;
            }
            Game game = plugin.getGm().getGameByPlayer(p);
            String d = item.getItemMeta().getDisplayName();
            if (d.equals(plugin.getLang().get("menus.teams.random.nameItem"))){
                if (game.isState(State.WAITING) || game.isState(State.STARTING)){
                    e.setCancelled(true);
                    p.sendMessage(plugin.getLang().get("messages.needStart"));
                    return;
                }
                game.addPlayerRandomTeam(p);
                return;
            }
            String co = NBTEditor.getString(item, "SELECTOR", "TEAM", "COLOR");
            if (co == null) return;
            ChatColor c = ChatColor.valueOf(co);
            Team team = game.getTeams().get(c);
            if (game.isState(State.WAITING) || game.isState(State.STARTING)){
                e.setCancelled(true);
                p.sendMessage(plugin.getLang().get("messages.needStart"));
                return;
            }
            game.addPlayerTeam(p, team);
            p.sendMessage(plugin.getLang().get("messages.joinTeam").replaceAll("<team>", team.getName()));
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.lobby.title"))) {
            if (plugin.getSm().isSetupInventory(p)) {
                return;
            }
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.lobby.perks.nameItem"))) {
                
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.trails.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createTrailsSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.taunts.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createTauntsSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.wineffects.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createWinEffectSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.killeffects.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createKillEffectSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.windances.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createWinDanceSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.killsound.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createKillSoundSelectorMenu(p);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.trailsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.trailsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.trailsselector.deselect.nameItem"))) {
                if (sw.getTrail() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setTrail(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectTrail"));
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.trailsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            Trail k = plugin.getTlm().getTrailByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setTrail(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTrail").replaceAll("<trail>", k.getName()));
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            if (!sw.getTrails().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setTrail(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTrail").replaceAll("<trail>", k.getName()));
            }
            plugin.getUim().createTrailsSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.tauntsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.tauntsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.tauntsselector.deselect.nameItem"))) {
                if (sw.getTaunt() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setTaunt(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectTaunt"));
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.tauntsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            Taunt k = plugin.getTm().getTauntByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setTaunt(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTaunt").replaceAll("<taunt>", k.getName()));
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (!sw.getTaunts().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setTaunt(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTaunt").replaceAll("<taunt>", k.getName()));
            }
            plugin.getUim().createTauntsSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.killsoundsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killsoundsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killsoundsselector.deselect.nameItem"))) {
                if (sw.getKillSound() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setKillSound(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselect"));
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killsoundsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            KillSound k = plugin.getKsm().getKillSoundByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setKillSound(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillSound").replaceAll("<killsound>", k.getName()));
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (!sw.getKillsounds().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setKillSound(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillSound").replaceAll("<killsound>", k.getName()));
            }
            plugin.getUim().createKillSoundSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.wineffectsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.wineffectsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.wineffectsselector.deselect.nameItem"))) {
                if (sw.getWinEffect() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setWinEffect(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectWinEffect"));
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.wineffectsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            UltraWinEffect k = plugin.getWem().getWinEffectByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setWinEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinEffect").replaceAll("<wineffect>", k.getName()));
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (!sw.getWineffects().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setWinEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinEffect").replaceAll("<wineffect>", k.getName()));
            }
            plugin.getUim().createWinEffectSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.killeffectsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killeffectsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killeffectsselector.deselect.nameItem"))) {
                if (sw.getKillEffect() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setKillEffect(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectKillEffect"));
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killeffectsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            UltraKillEffect k = plugin.getKem().getKillEffectByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setKillEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillEffect").replaceAll("<killeffect>", k.getName()));
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (!sw.getKilleffects().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setKillEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillEffect").replaceAll("<killeffect>", k.getName()));
            }
            plugin.getUim().createKillEffectSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.windancesselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            ItemStack item = e.getCurrentItem();
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.windancesselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.windancesselector.deselect.nameItem"))) {
                if (sw.getWinDance() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setWinDance(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectWinDance"));
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.windancesselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            UltraWinDance k = plugin.getWdm().getWinDanceByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setWinDance(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinDance").replaceAll("<windance>", k.getName()));
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (!sw.getWindances().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setWinDance(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinDance").replaceAll("<windance>", k.getName()));
            }
            plugin.getUim().createWinDanceSelectorMenu(p);
        }
    }

}
package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects.UltraKillEffect;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds.KillSound;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.KitLevel;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts.Taunt;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.windances.UltraWinDance;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.UltraWinEffect;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.objects.ShopItem;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class ShopManager {

    private HashMap<Integer, ShopItem> items = new HashMap<>();
    private Main plugin;

    public ShopManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload(){
        items.clear();
        if (!plugin.getShop().isSet("shop")) return;
        for (String s : plugin.getShop().getConfig().getConfigurationSection("shop").getKeys(false)){
            int id = Integer.parseInt(s);
            items.put(id, new ShopItem(plugin, "shop." + s));
        }
    }

    public void addItem(ItemStack item, double price){
        String path = "shop." + items.size();
        ItemStack newItem = item.clone();
        ItemMeta im = newItem.getItemMeta();
        im.setDisplayName("§eItem " + items.size());
        im.setLore(Arrays.asList("§7This is a default lore", "§7change in shop.yml"));
        newItem.setItemMeta(im);
        plugin.getShop().set(path + ".item", newItem);
        plugin.getShop().set(path + ".price", price);
        plugin.getShop().save();
        reload();
    }

    public void buy(Player p, Purchasable purchasable, String name) {
        if (!purchasable.isBuy()) {
            p.sendMessage(plugin.getLang().get(p, "messages.noBuy"));
            return;
        }
        if (plugin.getAdm().getCoins(p) < purchasable.getPrice()) {
            p.sendMessage(plugin.getLang().get(p, "messages.noCoins"));
            return;
        }
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        plugin.getAdm().removeCoins(p, purchasable.getPrice());
        if (purchasable instanceof ShopItem) {
            ShopItem si = (ShopItem) purchasable;
            p.getInventory().addItem(si.getItem());
        } else if (purchasable instanceof KitLevel) {
            KitLevel k = (KitLevel) purchasable;
            Game g = plugin.getGm().getGameByPlayer(p);
            Team team = g.getTeamPlayer(p);
            plugin.getKm().giveKit(p, k.getKitID(), k.getLevel(), team);
        } else if (purchasable instanceof UltraKillEffect) {
            UltraKillEffect k = (UltraKillEffect) purchasable;
            sw.addKillEffects(k.getId());
        } else if (purchasable instanceof KillSound) {
            KillSound k = (KillSound) purchasable;
            sw.addKillSounds(k.getId());
        } else if (purchasable instanceof Taunt) {
            Taunt k = (Taunt) purchasable;
            sw.addTaunts(k.getId());
        } else if (purchasable instanceof Trail) {
            Trail k = (Trail) purchasable;
            sw.addTrails(k.getId());
        } else if (purchasable instanceof UltraWinDance) {
            UltraWinDance k = (UltraWinDance) purchasable;
            sw.addWinDances(k.getId());
        } else if (purchasable instanceof UltraWinEffect) {
            UltraWinEffect k = (UltraWinEffect) purchasable;
            sw.addWinEffects(k.getId());
        }
        Utils.updateSB(p);
        p.sendMessage(plugin.getLang().get(p, "messages.bought").replaceAll("<name>", name));
    }

    public HashMap<Integer, ShopItem> getItems() {
        return items;
    }
}
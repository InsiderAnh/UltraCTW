package io.github.Leonardo0013YT.UltraCTW.cosmetics.parting;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Parting implements Purchasable {

    private ArrayList<String> lines;
    private String name, permission;
    private boolean isBuy, needPermToBuy;
    private int id, slot, page, price;
    private ItemStack icon;

    public Parting(Main plugin, String s) {
        this.id = plugin.getParting().getInt(s + ".id");
        this.lines = (ArrayList<String>) plugin.getParting().getList(s + ".message");
        this.name = plugin.getParting().get(null, s + ".name");
        this.permission = plugin.getParting().get(null, s + ".permission");
        this.slot = plugin.getParting().getInt(s + ".slot");
        this.page = plugin.getParting().getInt(s + ".page");
        this.price = plugin.getParting().getInt(s + ".price");
        this.isBuy = plugin.getParting().getBoolean(s + ".isBuy");
        this.icon = plugin.getParting().getConfig().getItemStack(s + ".icon");
        this.needPermToBuy = plugin.getParting().getBooleanOrDefault(s + ".needPermToBuy", false);
        plugin.getPm().setLastPage(page);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getAutoGivePermission() {
        return null;
    }

    @Override
    public boolean isBuy() {
        return isBuy;
    }

    @Override
    public boolean needPermToBuy() {
        return needPermToBuy;
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public int getPage() {
        return page;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void execute(Player k, Player d) {
        Location l = d.getLocation();

    }

}
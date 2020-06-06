package io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class ShopKeeper implements Purchasable {

    private String name, permission, autoGivePermission, type, entityType;
    private boolean isBuy, needPermToBuy;
    private UUID uuid;
    private int id, slot, page, price;
    private ItemStack icon;

    public ShopKeeper(Main plugin, String s) {
        this.id = plugin.getShopkeepers().getInt(s + ".id");
        this.name = plugin.getShopkeepers().get(s + ".name");
        this.type = plugin.getShopkeepers().get(s + ".type");
        this.permission = plugin.getShopkeepers().get(s + ".permission");
        this.entityType = plugin.getShopkeepers().get(s + ".entity");
        this.uuid = UUID.fromString(plugin.getShopkeepers().get(s + ".uuid"));
        this.slot = plugin.getShopkeepers().getInt(s + ".slot");
        this.page = plugin.getShopkeepers().getInt(s + ".page");
        this.price = plugin.getShopkeepers().getInt(s + ".price");
        this.isBuy = plugin.getShopkeepers().getBoolean(s + ".isBuy");
        this.needPermToBuy = plugin.getShopkeepers().getBooleanOrDefault(s + ".needPermToBuy", false);
        this.icon = plugin.getShopkeepers().getConfig().getItemStack(s + ".icon");
        this.autoGivePermission = plugin.getShopkeepers().getOrDefault(s + ".autoGivePermission", "ultraskywars.shopkeepers.autogive." + name);
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getAutoGivePermission() {
        return autoGivePermission;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public boolean isBuy() {
        return isBuy;
    }

    @Override
    public boolean needPermToBuy() {
        return needPermToBuy;
    }
}
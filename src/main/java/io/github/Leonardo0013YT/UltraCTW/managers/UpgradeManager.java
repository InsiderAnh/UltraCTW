package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.shop.Shop;
import io.github.Leonardo0013YT.UltraCTW.upgrades.Upgrade;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class UpgradeManager {

    private UltraCTW plugin;
    private HashMap<String, Upgrade> upgrades = new HashMap<>();
    private HashMap<String, Shop> shops = new HashMap<>();

    public UpgradeManager(UltraCTW plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        if (plugin.getUpgrades().isSet("upgrades")) {
            for (String s : plugin.getUpgrades().getConfig().getConfigurationSection("upgrades").getKeys(false)) {
                upgrades.put(s, new Upgrade(plugin, "upgrades." + s, s));
            }
        }
        if (plugin.getUpgrades().isSet("shop")) {
            for (String s : plugin.getUpgrades().getConfig().getConfigurationSection("shop").getKeys(false)) {
                shops.put(s, new Shop(plugin, "shop." + s, s));
            }
        }
    }

    public Upgrade getUpgrade(String key) {
        return upgrades.get(key);
    }

}
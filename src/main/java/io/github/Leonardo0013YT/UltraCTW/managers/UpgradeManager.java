package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.upgrades.Upgrade;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class UpgradeManager {

    private Main plugin;
    private HashMap<String, Upgrade> upgrades = new HashMap<>();

    public UpgradeManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        if (!plugin.getUpgrades().isSet("upgrades")) return;
        for (String s : plugin.getUpgrades().getConfig().getConfigurationSection("upgrades").getKeys(false)) {
            upgrades.put(s, new Upgrade(plugin, "upgrades." + s, s));
        }
    }

    public Upgrade getUpgrade(String key) {
        return upgrades.get(key);
    }

}
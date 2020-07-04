package io.github.Leonardo0013YT.UltraCTW.upgrades;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@Getter
public class Upgrade {

    private int slot;
    private String name, lore;
    private List<String> materials;
    private HashMap<Integer, UpgradeLevel> levels = new HashMap<>();
    private Main plugin;
    private String key;

    public Upgrade(Main plugin, String path, String key) {
        this.plugin = plugin;
        this.key = key;
        this.slot = plugin.getUpgrades().getInt(path + ".slot");
        this.name = plugin.getUpgrades().get(path + ".name");
        this.lore = plugin.getUpgrades().get(path + ".lore");
        this.materials = plugin.getUpgrades().getList(path + ".materials");
        for (String s : plugin.getUpgrades().getConfig().getConfigurationSection(path + ".levels").getKeys(false)) {
            int level = plugin.getUpgrades().getInt(path + ".levels." + s + ".level");
            levels.put(level, new UpgradeLevel(plugin, path + ".levels." + s));
        }
    }

    public ItemStack getIcon(GamePlayer gp) {
        UpgradeLevel ul = getLevel(gp.getPiUpgrade());
        UpgradeLevel next = getNextLevel(gp.getPiUpgrade());
        String state;
        if (next == null) {
            state = plugin.getUpgrades().get("max");
        } else if (gp.getCoins() < next.getPrice()) {
            state = plugin.getUpgrades().get("noMoney");
        } else {
            state = plugin.getUpgrades().get("buy");
        }
        return new ItemUtils(XMaterial.IRON_PICKAXE).setDisplayName(name).setLore(lore.replace("<now>", ul.getName()).replace("<next>", (next == null) ? plugin.getUpgrades().get("max") : next.getName()).replace("<status>", state)).build();
    }

    public UpgradeLevel getLevel(int level) {
        return levels.get(level);
    }

    public UpgradeLevel getNextLevel(int level) {
        return levels.get(level + 1);
    }

}
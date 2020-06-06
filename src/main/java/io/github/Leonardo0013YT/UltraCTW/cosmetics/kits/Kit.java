package io.github.Leonardo0013YT.UltraCTW.cosmetics.kits;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
public class Kit {

    private HashMap<Integer, KitLevel> levels = new HashMap<>();
    private int id, slot, page;
    private String name, permission;

    public Kit(Main plugin, String path) {
        this.id = plugin.getKits().getInt(path + ".id");
        this.slot = plugin.getKits().getInt(path + ".slot");
        this.page = plugin.getKits().getInt(path + ".page");
        this.name = plugin.getKits().get(null, path + ".name");
        this.permission = plugin.getKits().get(null, path + ".permission");
        for (String s : plugin.getKits().getConfig().getConfigurationSection(path + ".levels").getKeys(false)) {
            String lpa = path + ".levels." + s;
            levels.put(plugin.getKits().getInt(lpa + ".level"), new KitLevel(plugin, lpa, id));
        }
        plugin.getKm().setLastPage(page);
    }

    public void giveKit(Player p, int level, Team team) {
        if (levels.containsKey(level)) {
            levels.get(level).giveKitLevel(p, team);
        }
    }

    public KitLevel getKitLevelByItem(Player p, ItemStack item) {
        for (KitLevel l : getLevels().values()) {
            if (l.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return l;
            }
        }
        return null;
    }

}
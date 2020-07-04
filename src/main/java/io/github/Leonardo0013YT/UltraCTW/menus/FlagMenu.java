package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.upgrades.Upgrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FlagMenu {

    private Main plugin;

    public FlagMenu(Main plugin) {
        this.plugin = plugin;
    }

    public void createMainUpgradeMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, plugin.getLang().get("menus.upgrades.title"));
        GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
        GamePlayer gp = gf.getGamePlayer(p);
        for (Upgrade upgrade : plugin.getUm().getUpgrades().values()) {
            inv.setItem(upgrade.getSlot(), upgrade.getIcon(gp));
        }
        p.openInventory(inv);
    }

}
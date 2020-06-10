package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ItemManager {

    private ItemStack setup, points, teams, lobby;

    public ItemManager(Main plugin) {
        this.lobby = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("items.lobby.nameItem")).setLore(plugin.getLang().get("items.lobby.loreItem")).build();
        this.setup = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("items.setup.nameItem")).setLore(plugin.getLang().get("items.setup.loreItem")).build();
        this.points = new ItemUtils(XMaterial.STICK).setDisplayName(plugin.getLang().get("items.points.nameItem")).setLore(plugin.getLang().get("items.points.loreItem")).build();
        this.teams = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("items.teams.nameItem")).setLore(plugin.getLang().get("items.teams.loreItem")).build();
    }

}
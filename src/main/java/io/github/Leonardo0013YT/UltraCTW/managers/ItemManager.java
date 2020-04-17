package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.XMaterial;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    private ItemStack setup;

    public ItemManager(Main plugin) {
        this.setup = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("items.setup.nameItem")).setLore(plugin.getLang().get("items.setup.loreItem")).build();
    }

    public ItemStack getSetup() {
        return setup;
    }

}
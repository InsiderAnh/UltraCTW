package io.github.Leonardo0013YT.UltraCTW.upgrades;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.xseries.XEnchantment;
import io.github.Leonardo0013YT.UltraCTW.xseries.XPotion;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UpgradeLevel {

    private double price;
    private int level;
    private String name;
    private ArrayList<XEnchantment> enchantments = new ArrayList<>();
    private ArrayList<XPotion> selfEffects = new ArrayList<>(), teamEffects = new ArrayList<>();

    public UpgradeLevel(Main plugin, String path) {
        this.price = plugin.getUpgrades().getConfig().getDouble(path + ".price");
        this.level = plugin.getUpgrades().getInt(path + ".level");
        this.name = plugin.getUpgrades().get(path + ".name");
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".enchants", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            enchantments.add(XEnchantment.matchXEnchantment(s).orElse(XEnchantment.DIG_SPEED));
        }
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".selfEffects", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            selfEffects.add(XPotion.matchXPotion(s).orElse(XPotion.ABSORPTION));
        }
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".teamEffects", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            teamEffects.add(XPotion.matchXPotion(s).orElse(XPotion.ABSORPTION));
        }
    }

}
package io.github.Leonardo0013YT.UltraCTW.upgrades;

import io.github.Leonardo0013YT.UltraCTW.xseries.XPotion;
import lombok.Getter;

@Getter
public class UpgradePotion {

    private XPotion potion;
    private int level;
    private int duration;

    public UpgradePotion(XPotion potion, int level, int duration) {
        this.potion = potion;
        this.level = level - 1;
        this.duration = duration;
    }

}
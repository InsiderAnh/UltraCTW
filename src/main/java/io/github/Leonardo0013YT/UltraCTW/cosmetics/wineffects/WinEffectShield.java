package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.entity.Player;

public class WinEffectShield implements WinEffect {

    @Override
    public void start(Player p) {

    }

    @Override
    public void stop() {

    }

    @Override
    public WinEffect clone() {
        return new WinEffectShield();
    }

}
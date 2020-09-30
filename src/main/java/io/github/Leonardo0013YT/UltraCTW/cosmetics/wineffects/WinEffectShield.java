package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.entity.Player;

public class WinEffectShield implements WinEffect {

    @Override
    public void start(Player p, Game game) {

    }

    @Override
    public void start(Player p, GameFlag game) {

    }

    @Override
    public void stop() {

    }

    @Override
    public WinEffect clone() {
        return new WinEffectShield();
    }

}
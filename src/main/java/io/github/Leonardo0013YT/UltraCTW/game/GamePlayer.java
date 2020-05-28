package io.github.Leonardo0013YT.UltraCTW.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter@Setter
public class GamePlayer {

    private Player p;
    private int kills = 0, deaths = 0;

    public GamePlayer(Player p) {
        this.p = p;
    }

}
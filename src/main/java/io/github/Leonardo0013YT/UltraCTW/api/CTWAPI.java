package io.github.Leonardo0013YT.UltraCTW.api;

import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.entity.Player;

public class CTWAPI {

    private static Main plugin = Main.get();

    public static boolean isInLobby(Player p) {
        return !plugin.getGm().isPlayerInGame(p);
    }

}
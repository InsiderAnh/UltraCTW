package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface IDatabase {
    void loadPlayer(Player p);

    void savePlayer(UUID uuid, boolean sync);

    void close();

    HashMap<UUID, CTWPlayer> getPlayers();
}
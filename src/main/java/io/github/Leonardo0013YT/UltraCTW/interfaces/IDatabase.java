package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface IDatabase {
    void loadTopCaptured();

    void loadTopKills();

    void loadTopWins();

    void loadTopBounty();

    void loadPlayer(Player p);

    void savePlayer(UUID uuid, boolean sync);

    void close();

    HashMap<UUID, CTWPlayer> getPlayers();

    CTWPlayer getCTWPlayer(Player p);
}
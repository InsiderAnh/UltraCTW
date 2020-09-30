package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface KillEffect {

    void loadCustoms(Main plugin, String path);

    void start(Player p, Player death, Location loc);

    void stop();
}

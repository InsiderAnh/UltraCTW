package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.KeeperData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public interface NPC {

    void spawn(Player p, Location loc, EntityType type, KeeperData kd);

    void destroy();

    org.bukkit.entity.Entity getBukkitEntity();

}
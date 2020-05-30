package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class KillEffectSnowExplosion implements KillEffect, Cloneable {

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        ArrayList<Snowball> it = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            it.add(spawnSnow(loc, random(-0.35, 0.35), random(-0.35, 0.35)));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Snowball snow : it) {
                    snow.remove();
                }
            }
        }.runTaskLater(Main.get(), 40);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectSnowExplosion();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

    private Snowball spawnSnow(Location location, double d, double d3) {
        Snowball item = location.getWorld().spawn(location, Snowball.class);
        item.setVelocity(new Vector(d, 0.5, d3));
        item.setMetadata("SNOWBALL", new FixedMetadataValue(Main.get(), "SNOWBALL"));
        return item;
    }

}
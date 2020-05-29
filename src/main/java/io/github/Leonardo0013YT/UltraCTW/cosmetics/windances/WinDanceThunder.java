package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class WinDanceThunder implements WinDance, Cloneable {

    private BukkitTask task;
    private Random random;

    public WinDanceThunder() {
        this.random = new Random();
    }

    @Override
    public void start(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        World w = p.getWorld();
        Location loc1 = new Location(w, 10, w.getHighestBlockYAt(10, 10), 10);
        Location loc2 = new Location(w, -10, w.getHighestBlockYAt(10, 10), 10);
        Location loc3 = new Location(w, 10, w.getHighestBlockYAt(10, 10), -10);
        Location loc4 = new Location(w, -10, w.getHighestBlockYAt(10, 10), -10);
        Location loc5 = new Location(w, 15, w.getHighestBlockYAt(10, 10), 15);
        Location loc6 = new Location(w, -15, w.getHighestBlockYAt(10, 10), 15);
        Location loc7 = new Location(w, 15, w.getHighestBlockYAt(10, 10), -15);
        Location loc8 = new Location(w, -15, w.getHighestBlockYAt(10, 10), -15);
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    cancel();
                    return;
                }
                thunder(loc1);
                thunder(loc2);
                thunder(loc3);
                thunder(loc4);
                thunder(loc5);
                thunder(loc6);
                thunder(loc7);
                thunder(loc8);
            }
        }.runTaskTimer(Main.get(), 0, 10);
    }

    private void thunder(Location loc) {
        loc.getWorld().strikeLightningEffect(loc);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceThunder();
    }

}
package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class WinDanceDayNight implements WinDance, Cloneable {

    private BukkitTask task;

    @Override
    public void start(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        task = new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().setTime(p.getWorld().getTime() + 700);
            }
        }.runTaskTimer(Main.get(), 0, 1);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceDayNight();
    }
}
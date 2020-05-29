package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class WinDanceDestroyIsland implements WinDance, Cloneable {

    private BukkitTask task;
    private Random random;

    public WinDanceDestroyIsland() {
        this.random = new Random();
    }

    @Override
    public void start(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        Game game = Main.get().getGm().getGameByPlayer(p);
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Team team : game.getTeams().values()) {
                    explode(team.getSpawn().clone());
                }
            }
        }.runTaskLater(Main.get(), 20);
    }

    private void explode(Location loc) {
        loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()));
        loc.getWorld().strikeLightning(loc);
        TNTPrimed tnt = loc.getWorld().spawn(loc, TNTPrimed.class);
        tnt.setFuseTicks(15);
        TNTPrimed tnt2 = loc.getWorld().spawn(loc, TNTPrimed.class);
        tnt2.setFuseTicks(30);
        TNTPrimed tnt3 = loc.getWorld().spawn(loc, TNTPrimed.class);
        tnt3.setFuseTicks(45);
        TNTPrimed tnt4 = loc.getWorld().spawn(loc, TNTPrimed.class);
        tnt4.setFuseTicks(60);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceDestroyIsland();
    }
}
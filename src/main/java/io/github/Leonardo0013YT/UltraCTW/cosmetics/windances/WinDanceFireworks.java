package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class WinDanceFireworks implements WinDance, Cloneable {

    private BukkitTask task;
    private Random random;

    public WinDanceFireworks() {
        this.random = new Random();
    }

    @Override
    public void start(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        World world = p.getWorld();
        Location loc1 = new Location(world, 10, 90, 10);
        Location loc2 = new Location(world, -10, 90, 10);
        Location loc3 = new Location(world, 10, 90, -10);
        Location loc4 = new Location(world, -10, 90, -10);
        Location loc5 = new Location(world, 15, 90, 15);
        Location loc6 = new Location(world, -15, 90, 15);
        Location loc7 = new Location(world, 15, 90, -15);
        Location loc8 = new Location(world, -15, 90, -15);
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.getWorld().getName().equals(world.getName())) {
                    cancel();
                    return;
                }
                if (!p.isOnline()) {
                    cancel();
                    return;
                }
                firework(loc1);
                firework(loc2);
                firework(loc3);
                firework(loc4);
                firework(loc5);
                firework(loc6);
                firework(loc7);
                firework(loc8);
            }
        }.runTaskTimer(Main.get(), 0, 20);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceFireworks();
    }

    private void firework(Location loc) {
        Firework fa = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fam = fa.getFireworkMeta();
        FireworkEffect.Type tipo = FireworkEffect.Type.values()[random.nextInt(4)];
        Color c1 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        Color c2 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        FireworkEffect ef = FireworkEffect.builder().withColor(c1).withFade(c2).with(tipo).build();
        fam.addEffect(ef);
        fam.setPower(0);
        fa.setFireworkMeta(fam);
    }

}
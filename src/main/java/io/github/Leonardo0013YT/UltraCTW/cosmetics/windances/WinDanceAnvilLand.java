package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WinDanceAnvilLand implements WinDance, Cloneable {

    private BukkitTask task;
    private Random random;

    public WinDanceAnvilLand() {
        this.task = null;
        this.random = ThreadLocalRandom.current();
    }

    @Override
    public void start(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        World world = p.getWorld();
        task = new BukkitRunnable() {
            public void run() {
                byte blockData = 0x0;
                Location loc1 = new Location(world, random.nextInt(25), 110 + random.nextInt(5), random.nextInt(25));
                Location loc2 = new Location(world, -random.nextInt(25), 110 + random.nextInt(5), random.nextInt(25));
                Location loc3 = new Location(world, random.nextInt(25), 110 + random.nextInt(5), -random.nextInt(25));
                Location loc4 = new Location(world, -random.nextInt(25), 110 + random.nextInt(5), -random.nextInt(25));
                Location loc5 = new Location(world, random.nextInt(25), 110 + random.nextInt(5), random.nextInt(25));
                Location loc6 = new Location(world, -random.nextInt(25), 110 + random.nextInt(5), random.nextInt(25));
                Location loc7 = new Location(world, random.nextInt(25), 110 + random.nextInt(5), -random.nextInt(25));
                Location loc8 = new Location(world, -random.nextInt(25), 110 + random.nextInt(5), -random.nextInt(25));
                Location loc9 = new Location(world, random.nextInt(25), 110 + random.nextInt(5), random.nextInt(25));
                Location loc10 = new Location(world, -random.nextInt(25), 110 + random.nextInt(5), random.nextInt(25));
                Location loc11 = new Location(world, random.nextInt(25), 110 + random.nextInt(5), -random.nextInt(25));
                Location loc12 = new Location(world, -random.nextInt(25), 110 + random.nextInt(5), -random.nextInt(25));
                world.spawnFallingBlock(loc1, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc2, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc3, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc4, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc5, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc6, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc7, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc8, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc9, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc10, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc11, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc12, Material.ANVIL, blockData);
            }
        }.runTaskTimer(Main.get(), 15, 15);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceAnvilLand();
    }

}
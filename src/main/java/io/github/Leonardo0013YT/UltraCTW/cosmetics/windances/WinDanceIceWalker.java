package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class WinDanceIceWalker implements WinDance, Cloneable {

    private BukkitTask task;
    private int round;

    public WinDanceIceWalker() {
        this.round = 1;
    }

    @Override
    public void start(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        World world = p.getWorld();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.getWorld().getName().equals(world.getName())) {
                    cancel();
                    return;
                }
                for (Block block : getNearbyBlocks(p.getLocation(), 5 * round)) {
                    block.setType(Material.ICE);
                }
                round++;
            }
        }.runTaskTimer(Main.get(), 0, 20);
    }

    public BukkitTask getTask() {
        return task;
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR || block.getType() == Material.ICE || block.getType() == Material.PACKED_ICE) {
                        continue;
                    }
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceIceWalker();
    }

}
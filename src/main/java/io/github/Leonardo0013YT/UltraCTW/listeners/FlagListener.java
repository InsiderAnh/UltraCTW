package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.flag.Mine;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.objects.MineCountdown;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FlagListener implements Listener {

    private Main plugin;

    public FlagListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        Block b = e.getBlock();
        Material material = b.getType();
        Location loc = b.getLocation();
        if (g.getMines().containsKey(loc)) {
            Mine mine = plugin.getFm().getMine(material);
            if (g.getCountdowns().containsKey(loc)) {
                e.setCancelled(true);
            } else {
                g.getCountdowns().put(loc, new MineCountdown(mine.getKey(), loc, mine.getRegenerate()));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.setType(Material.COBBLESTONE);
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        if (g.isGracePeriod()){
            Block b = e.getBlock();
            boolean isVoid = isVoidBlock(b.getLocation().clone());
            e.setCancelled(isVoid);
            if (isVoid){
                p.sendMessage(plugin.getLang().get("messages.noPlaceInGrace"));
            }
        }
    }

    public boolean isVoidBlock(Location loc){
        int airBlocks = 0;
        for (int i = 1; i < 30; i++){
            if (!loc.clone().subtract(0, i, 0).getBlock().getType().equals(Material.AIR)){
                break;
            }
            airBlocks++;
        }
        return airBlocks > 27;
    }

}
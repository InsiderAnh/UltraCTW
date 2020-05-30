package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KillEffectSquid implements KillEffect, Cloneable {

    private BukkitTask task;
    private int pased = 0;

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        Squid squid = loc.getWorld().spawn(loc, Squid.class);
        squid.setNoDamageTicks(999999999);
        squid.setMetadata("KILLEFFECT", new FixedMetadataValue(Main.get(), "KILLEFFECT"));
        task = new BukkitRunnable() {
            @Override
            public void run() {
                pased++;
                if (pased >= 20) {
                    squid.remove();
                    cancel();
                    return;
                }
                Location loc = squid.getLocation().clone().add(0, 0.3 * pased, 0);
                squid.teleport(loc);
                loc.getWorld().playEffect(loc, Effect.EXPLOSION, 1);
                loc.getWorld().playEffect(loc, Effect.LAVADRIP, 1);
                p.playSound(loc, Main.get().getCm().getKillEffectSquid(), 1.0f, 1.0f);
            }
        }.runTaskTimer(Main.get(), 0, 2);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public KillEffect clone() {
        return new KillEffectSquid();
    }

}
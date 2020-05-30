package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

public class KillEffectTNT implements KillEffect, Cloneable {

    @Override
    public void start(Player p, Player death, Location loc) {
        if (p == null || !p.isOnline()) {
            return;
        }
        if (death == null || !death.isOnline()) {
            return;
        }
        TNTPrimed primed = loc.getWorld().spawn(loc, TNTPrimed.class);
        primed.setFuseTicks(4);
        new BukkitRunnable() {
            @Override
            public void run() {
                loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 1);
                p.playSound(p.getLocation(), Main.get().getCm().getKillEffectTNT(), 1.0f, 1.0f);
                primed.remove();
            }
        }.runTaskLater(Main.get(), 2);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectSquid();
    }

}

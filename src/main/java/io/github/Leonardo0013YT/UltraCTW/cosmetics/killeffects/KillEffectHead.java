package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KillEffectHead implements KillEffect, Cloneable {

    private boolean loaded = false;
    private XSound punchSound;
    private BukkitTask task;
    private int pased = 0;

    @Override
    public void loadCustoms(Main plugin, String path) {
        if (!loaded) {
            punchSound = XSound.matchXSound(plugin.getKilleffect().getOrDefault(path + ".punchSound", XSound.ENTITY_FIREWORK_ROCKET_BLAST.parseSound().name())).orElse(XSound.ENTITY_FIREWORK_ROCKET_BLAST);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        ItemStack head = ItemBuilder.skull(XMaterial.PLAYER_HEAD, 1, "§e", "§e", death.getName());
        ArmorStand armor = loc.getWorld().spawn(loc, ArmorStand.class);
        armor.setVisible(false);
        armor.setCustomName(death.getName());
        armor.setCustomNameVisible(true);
        armor.setHelmet(head);
        armor.setNoDamageTicks(999999999);
        armor.setMetadata("KILLEFFECT", new FixedMetadataValue(Main.get(), "KILLEFFECT"));
        task = new BukkitRunnable() {
            @Override
            public void run() {
                pased++;
                if (pased >= 20) {
                    armor.getWorld().playEffect(armor.getLocation(), Effect.STEP_SOUND, Material.COAL_BLOCK);
                    p.playSound(p.getLocation(), punchSound.parseSound(), 1.0f, 1.0f);
                    armor.remove();
                    cancel();
                    return;
                }
                Location loc = armor.getLocation().clone().add(0, 0.3 * pased, 0);
                armor.teleport(loc);
                loc.getWorld().playEffect(loc, Effect.SMOKE, 1);
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
        return new KillEffectHead();
    }

}
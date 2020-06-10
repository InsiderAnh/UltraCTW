package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects.UltraKillEffect;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds.KillSound;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.KitLevel;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts.Taunt;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.windances.UltraWinDance;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.UltraWinEffect;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;

public class ShopManager {

    private Main plugin;

    public ShopManager(Main plugin) {
        this.plugin = plugin;
    }

    public void buy(Player p, Purchasable purchasable, String name) {
        if (!purchasable.isBuy()) {
            p.sendMessage(plugin.getLang().get(p, "messages.noBuy"));
            return;
        }
        if (plugin.getAdm().getCoins(p) < purchasable.getPrice()) {
            p.sendMessage(plugin.getLang().get(p, "messages.noCoins"));
            return;
        }
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        plugin.getAdm().removeCoins(p, purchasable.getPrice());
        if (purchasable instanceof KitLevel) {
            KitLevel k = (KitLevel) purchasable;
            Game g = plugin.getGm().getGameByPlayer(p);
            Team team = g.getTeamPlayer(p);
            plugin.getKm().giveKit(p, k.getKitID(), k.getLevel(), team);
        } else if (purchasable instanceof UltraKillEffect) {
            UltraKillEffect k = (UltraKillEffect) purchasable;
            sw.addKillEffects(k.getId());
        } else if (purchasable instanceof KillSound) {
            KillSound k = (KillSound) purchasable;
            sw.addKillSounds(k.getId());
        } else if (purchasable instanceof Taunt) {
            Taunt k = (Taunt) purchasable;
            sw.addTaunts(k.getId());
        } else if (purchasable instanceof Trail) {
            Trail k = (Trail) purchasable;
            sw.addTrails(k.getId());
        } else if (purchasable instanceof UltraWinDance) {
            UltraWinDance k = (UltraWinDance) purchasable;
            sw.addWinDances(k.getId());
        } else if (purchasable instanceof UltraWinEffect) {
            UltraWinEffect k = (UltraWinEffect) purchasable;
            sw.addWinEffects(k.getId());
        }
        Utils.updateSB(p);
        p.sendMessage(plugin.getLang().get(p, "messages.bought").replaceAll("<name>", name));
    }

}
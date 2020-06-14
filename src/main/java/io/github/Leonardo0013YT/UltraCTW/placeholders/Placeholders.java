package io.github.Leonardo0013YT.UltraCTW.placeholders;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.Level;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private Main plugin;

    public Placeholders(Main plugin) {
        this.plugin = plugin;
    }

    public String getIdentifier() {
        return "ctw";
    }

    public String getPlugin() {
        return null;
    }

    public String getAuthor() {
        return "Leonardo0013YT";
    }

    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String id) {
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        if (sw == null) {
            return "";
        }
        if (id.equals("selected_trail")) {
            return plugin.getTlm().getSelected(sw);
        }
        if (id.equals("selected_windance")) {
            return plugin.getWdm().getSelected(sw);
        }
        if (id.equals("selected_wineffect")) {
            return plugin.getWem().getSelected(sw);
        }
        if (id.equals("selected_taunt")) {
            return plugin.getTm().getSelected(sw);
        }
        if (id.equals("selected_killsound")) {
            return plugin.getKsm().getSelected(sw);
        }
        if (id.equals("selected_killeffect")) {
            return plugin.getKem().getSelected(sw);
        }
        if (id.equals("unlocked_trails")) {
            return "" + sw.getTrails().size();
        }
        if (id.equals("total_trails")) {
            return "" + plugin.getTlm().getTrailsSize();
        }
        if (id.equals("percentage_trails")) {
            return "" + Utils.getProgressBar(sw.getTrails().size(), plugin.getTlm().getTrailsSize(), plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("unlocked_windances")) {
            return "" + sw.getWindances().size();
        }
        if (id.equals("total_windances")) {
            return "" + plugin.getWdm().getWinDancesSize();
        }
        if (id.equals("percentage_windances")) {
            return "" + Utils.getProgressBar(sw.getWindances().size(), plugin.getWdm().getWinDancesSize(), plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("unlocked_wineffects")) {
            return "" + sw.getWineffects().size();
        }
        if (id.equals("total_wineffects")) {
            return "" + plugin.getWem().getWinEffectsSize();
        }
        if (id.equals("percentage_wineffects")) {
            return "" + Utils.getProgressBar(sw.getWineffects().size(), plugin.getWem().getWinEffectsSize(), plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("unlocked_taunts")) {
            return "" + sw.getTaunts().size();
        }
        if (id.equals("total_taunts")) {
            return "" + plugin.getTm().getTauntsSize();
        }
        if (id.equals("percentage_taunts")) {
            return "" + Utils.getProgressBar(sw.getTaunts().size(), plugin.getTm().getTauntsSize(), plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("unlocked_killsounds")) {
            return "" + sw.getKillsounds().size();
        }
        if (id.equals("total_killsounds")) {
            return "" + plugin.getKsm().getKillSoundsSize();
        }
        if (id.equals("percentage_killsounds")) {
            return "" + Utils.getProgressBar(sw.getKillsounds().size(), plugin.getKsm().getKillSoundsSize(), plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("unlocked_killeffects")) {
            return "" + sw.getKilleffects().size();
        }
        if (id.equals("total_killeffects")) {
            return "" + plugin.getKem().getKillEffectSize();
        }
        if (id.equals("percentage_killeffects")) {
            return "" + Utils.getProgressBar(sw.getKilleffects().size(), plugin.getKem().getKillEffectSize(), plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("level_progress")) {
            Level l = plugin.getLvl().getLevel(p);
            int xp = sw.getXp() - l.getXp();
            int max = l.getLevelUp() - l.getXp();
            return Utils.getProgressBar(xp, max, plugin.getCm().getProgressBarAmount());
        }
        if (id.equals("level_prefix")) {
            return plugin.getLvl().getLevelPrefix(p);
        }
        if (id.equals("level")) {
            return "" + sw.getLevel();
        }
        if (id.equals("coins")) {
            return "" + plugin.getAdm().getCoins(p);
        }
        if (id.equals("xp")) {
            return "" + sw.getXp();
        }
        if (id.equals("kills")) {
            return "" + sw.getKills();
        }
        if (id.equals("wins")) {
            return "" + sw.getWins();
        }
        if (id.equals("deaths")) {
            return "" + sw.getDeaths();
        }
        if (id.equals("shots")) {
            return "" + sw.getShots();
        }
        if (id.equals("sshots")) {
            return "" + sw.getsShots();
        }
        if (id.equals("walked")) {
            return "" + sw.getWalked();
        }
        if (id.equals("played")) {
            return "" + sw.getPlayed();
        }
        if (id.equals("placed")) {
            return "" + sw.getPlaced();
        }
        if (id.equals("break")) {
            return "" + sw.getBroken();
        }
        return null;
    }

}
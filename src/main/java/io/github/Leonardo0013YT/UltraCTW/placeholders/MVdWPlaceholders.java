package io.github.Leonardo0013YT.UltraCTW.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.Level;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;

public class MVdWPlaceholders {

    private UltraCTW plugin;

    public MVdWPlaceholders(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void register() {
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_sshots", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getsShots();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_walked", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getWalked();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_played", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getPlayed();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_placed", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getPlaced();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_break", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getBroken();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_shots", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getShots();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_deaths", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getDeaths();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_wins", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getWins();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_kills", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getKills();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_xp", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getXp();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_coins", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getAdm().getCoins(p);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_level", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getLevel();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_level_prefix", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getLvl().getLevelPrefix(p);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_level_progress", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                Level l = plugin.getLvl().getLevel(p);
                int xp = sw.getXp() - l.getXp();
                int max = l.getLevelUp() - l.getXp();
                return Utils.getProgressBar(xp, max, plugin.getCm().getProgressBarAmount());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_total_taunts", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getTm().getTauntsSize();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_percentage_taunts", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getTaunts().size(), plugin.getTm().getTauntsSize());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_bar_taunts", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getTaunts().size(), plugin.getTm().getTauntsSize(), plugin.getCm().getProgressBarAmount());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_unlocked_killsounds", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getKillsounds().size();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_total_killsounds", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getKsm().getKillSoundsSize();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_percentage_killsounds", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getKillsounds().size(), plugin.getKsm().getKillSoundsSize());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_bar_killsounds", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getKillsounds().size(), plugin.getKsm().getKillSoundsSize(), plugin.getCm().getProgressBarAmount());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_unlocked_killeffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getKilleffects().size();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_total_killeffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getKem().getKillEffectSize();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_percentage_killeffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getKilleffects().size(), plugin.getKem().getKillEffectSize());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_bar_killeffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getKilleffects().size(), plugin.getKem().getKillEffectSize(), plugin.getCm().getProgressBarAmount());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_total_windances", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getWdm().getWinDancesSize();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_percentage_windances", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getWindances().size(), plugin.getWdm().getWinDancesSize());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_bar_windances", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getWindances().size(), plugin.getWdm().getWinDancesSize(), plugin.getCm().getProgressBarAmount());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_unlocked_wineffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getWineffects().size();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_total_wineffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getWem().getWinEffectsSize();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_percentage_wineffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getWineffects().size(), plugin.getWem().getWinEffectsSize());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_bar_wineffects", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + Utils.getProgressBar(sw.getWineffects().size(), plugin.getWem().getWinEffectsSize(), plugin.getCm().getProgressBarAmount());
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_unlocked_taunts", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getTaunts().size();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_selected_trail", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getTlm().getSelected(sw);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_selected_windance", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getWdm().getSelected(sw);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_selected_wineffect", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getWem().getSelected(sw);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_selected_taunt", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getTm().getSelected(sw);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_selected_killsound", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getKsm().getSelected(sw);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_selected_killeffect", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return plugin.getKem().getSelected(sw);
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_unlocked_trails", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + sw.getTrails().size();
            }
            return "";
        });
        PlaceholderAPI.registerPlaceholder(plugin, "ctw_total_trails", e -> {
            Player p = e.getPlayer();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (sw != null) {
                return "" + plugin.getTlm().getTrailsSize();
            }
            return "";
        });
    }

}
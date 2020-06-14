package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.TopType;
import io.github.Leonardo0013YT.UltraCTW.tops.Top;
import io.github.Leonardo0013YT.UltraCTW.tops.TopPlayer;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopManager {

    private HashMap<TopType, Top> tops = new HashMap<>();
    private ArrayList<Location> holograms = new ArrayList<>();
    private Main plugin;

    public TopManager(Main plugin) {
        this.plugin = plugin;
    }

    public void addTop(TopType type, List<String> tops) {
        this.tops.put(type, new Top(type, tops));
    }

    public void createTops() {
        if (!plugin.getAdm().hasHologramPlugin()) {
            return;
        }
        remove();
        if (plugin.getCm().getTopBounty() != null) {
            List<String> l = plugin.getLang().getList("holograms.tops.bounty");
            ArrayList<String> lines = new ArrayList<>();
            Top top = tops.get(TopType.BOUNTY);
            for (String s : l) {
                if (s.equals("<top>")) {
                    for (int i : top.getTops().keySet()) {
                        TopPlayer tp = top.getTops().get(i);
                        lines.add(plugin.getLang().get("topFormat").replaceAll("<amount>", "" + tp.getAmount()).replaceAll("<name>", tp.getName()).replaceAll("<top>", "" + tp.getPosition()));
                    }
                } else {
                    lines.add(s.replaceAll("&", "§"));
                }
            }
            plugin.getAdm().createHologram(plugin.getCm().getTopBounty().clone(), lines);
            holograms.add(plugin.getCm().getTopBounty().clone());
        }
        if (plugin.getCm().getTopKills() != null) {
            List<String> l = plugin.getLang().getList("holograms.tops.kills");
            ArrayList<String> lines = new ArrayList<>();
            Top top = tops.get(TopType.KILLS);
            for (String s : l) {
                if (s.equals("<top>")) {
                    for (int i : top.getTops().keySet()) {
                        TopPlayer tp = top.getTops().get(i);
                        lines.add(plugin.getLang().get("topFormat").replaceAll("<amount>", "" + tp.getAmount()).replaceAll("<name>", tp.getName()).replaceAll("<top>", "" + tp.getPosition()));
                    }
                } else {
                    lines.add(s.replaceAll("&", "§"));
                }
            }
            plugin.getAdm().createHologram(plugin.getCm().getTopKills().clone(), lines);
            holograms.add(plugin.getCm().getTopKills().clone());
        }
        if (plugin.getCm().getTopWins() != null) {
            List<String> l = plugin.getLang().getList("holograms.tops.wins");
            ArrayList<String> lines = new ArrayList<>();
            Top top = tops.get(TopType.WINS);
            for (String s : l) {
                if (s.equals("<top>")) {
                    for (int i : top.getTops().keySet()) {
                        TopPlayer tp = top.getTops().get(i);
                        lines.add(plugin.getLang().get("topFormat").replaceAll("<amount>", "" + tp.getAmount()).replaceAll("<name>", tp.getName()).replaceAll("<top>", "" + tp.getPosition()));
                    }
                } else {
                    lines.add(s.replaceAll("&", "§"));
                }
            }
            plugin.getAdm().createHologram(plugin.getCm().getTopWins().clone(), lines);
            holograms.add(plugin.getCm().getTopWins().clone());
        }
        if (plugin.getCm().getTopCaptured() != null) {
            List<String> l = plugin.getLang().getList("holograms.tops.captured");
            ArrayList<String> lines = new ArrayList<>();
            Top top = tops.get(TopType.CAPTURED);
            for (String s : l) {
                if (s.equals("<top>")) {
                    for (int i : top.getTops().keySet()) {
                        TopPlayer tp = top.getTops().get(i);
                        lines.add(plugin.getLang().get("topFormat").replaceAll("<amount>", "" + tp.getAmount()).replaceAll("<name>", tp.getName()).replaceAll("<top>", "" + tp.getPosition()));
                    }
                } else {
                    lines.add(s.replaceAll("&", "§"));
                }
            }
            plugin.getAdm().createHologram(plugin.getCm().getTopCaptured().clone(), lines);
            holograms.add(plugin.getCm().getTopCaptured().clone());
        }
    }

    public void remove() {
        for (Location l : holograms) {
            if (plugin.getAdm().hasHologram(l)) {
                plugin.getAdm().deleteHologram(l);
            }
        }
    }

}
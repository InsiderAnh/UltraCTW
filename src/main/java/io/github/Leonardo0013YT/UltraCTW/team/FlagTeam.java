package io.github.Leonardo0013YT.UltraCTW.team;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Getter
public class FlagTeam {

    private ChatColor color;
    private Location spawn, flag;
    private GameFlag gameFlag;
    private ArrayList<Player> members = new ArrayList<>();
    private int id;

    public FlagTeam(Main plugin, GameFlag gameFlag, String path, int id) {
        this.gameFlag = gameFlag;
        this.id = id;
        this.color = ChatColor.valueOf(plugin.getArenas().get(path + ".color"));
        this.spawn = Utils.getStringLocation(plugin.getArenas().get(path + ".spawn"));
        this.flag = Utils.getStringLocation(plugin.getArenas().get(path + ".flag"));
    }

    public void addMember(Player p) {
        if (!members.contains(p)) {
            members.add(p);
        }
    }

    public void reset() {
        members.clear();
    }

    public void removeMember(Player p) {
        members.remove(p);
    }

    public int getTeamSize() {
        return members.size();
    }

}
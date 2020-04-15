package io.github.Leonardo0013YT.UltraCTW.team;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class Team {

    private Collection<Player> members = new ArrayList<>();
    private Main plugin;
    private Game game;
    private int id;
    private int kills;
    private Location spawn;

    public Team(Main plugin, Game game, String path, int id) {
        this.plugin = plugin;
        this.game = game;
        this.id = id;
        this.kills = 0;
        this.spawn = Utils.getStringLocation(plugin.getArenas().get(null, path + ".spawn"));
    }

    public void addKill() {
        kills++;
    }

    public int getKills() {
        return kills;
    }

    public void reset() {
        members.clear();
    }

    public void addMember(Player p) {
        if (!members.contains(p)) {
            members.add(p);
        }
    }

    public void removeMember(Player p) {
        members.remove(p);
    }

    public int getTeamSize() {
        return members.size();
    }

    public Collection<Player> getMembers() {
        return members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

}
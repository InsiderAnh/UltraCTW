package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Game {

    private Main plugin;
    private String name, schematic;
    private HashMap<ChatColor, Team> teams = new HashMap<>();
    private Location lobby, spectator;
    private int teamSize, woolSize, min;

    public Game(Main plugin, String path){
        this.plugin = plugin;
        this.name = plugin.getArenas().get(path + ".name");
        this.schematic = plugin.getArenas().get(path + ".schematic");
        this.lobby = Utils.getStringLocation(plugin.getArenas().get(path + ".lobby"));
        this.spectator = Utils.getStringLocation(plugin.getArenas().get(path + ".spectator"));
        this.teamSize = plugin.getArenas().getInt(path + ".teamSize");
        this.woolSize = plugin.getArenas().getInt(path + ".woolSize");
        this.min = plugin.getArenas().getInt(path + ".min");
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".teams").getKeys(false)){
            teams.put(ChatColor.valueOf(c), new Team(plugin, this, path + ".teams." + c, teams.size()));
        }
    }

    public void addPlayer(Player p){
        p.teleport(lobby);
    }

    public void removePlayer(Player p){

    }

    public void addPlayerTeam(Player p, Team team) {
        team.addMember(p);
    }

    public void removePlayerTeam(Player p, Team team) {
        team.removeMember(p);
    }

    public Team getTeamByColor(ChatColor color) {
        return teams.get(color);
    }

    public void joinRandomTeam(Player p) {
        for (Team team : teams.values()) {
            if (team.getTeamSize() < teamSize) {
                addPlayerTeam(p, team);
                break;
            }
        }
    }

    public void removePlayerAllTeam(Player p) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(p)) {
                removePlayerTeam(p, team);
            }
        }
    }

    public Team getLastTeam() {
        for (Team team : teams.values()) {
            if (team.getTeamSize() > 0) {
                return team;
            }
        }
        return null;
    }

    public int getTeamAlive() {
        int c = 0;
        for (Team team : teams.values()) {
            if (team.getTeamSize() > 0) {
                c++;
            }
        }
        return c;
    }

    public Team getTeamPlayer(Player p) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(p)) {
                return team;
            }
        }
        return null;
    }

}
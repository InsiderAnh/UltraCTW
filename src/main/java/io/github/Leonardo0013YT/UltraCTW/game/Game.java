package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {

    private ArrayList<Team> teams = new ArrayList<>();
    private int teamSize;

    public Game(){

    }

    public void addPlayerTeam(Player p, Team team) {
        team.addMember(p);
    }

    public void removePlayerTeam(Player p, Team team) {
        team.removeMember(p);
    }

    public Team getTeamByID(int id) {
        for (Team team : teams) {
            if (team.getId() == id) {
                return team;
            }
        }
        return null;
    }

    public void joinRandomTeam(Player p) {
        for (Team team : teams) {
            if (team.getTeamSize() < teamSize) {
                addPlayerTeam(p, team);
                break;
            }
        }
    }

    public void removePlayerAllTeam(Player p) {
        for (Team team : teams) {
            if (team.getMembers().contains(p)) {
                removePlayerTeam(p, team);
            }
        }
    }

    public Team getLastTeam() {
        for (Team team : teams) {
            if (team.getTeamSize() > 0) {
                return team;
            }
        }
        return null;
    }

    public int getTeamAlive() {
        int c = 0;
        for (Team team : teams) {
            if (team.getTeamSize() > 0) {
                c++;
            }
        }
        return c;
    }

    public Team getTeamPlayer(Player p) {
        for (Team team : teams) {
            if (team.getMembers().contains(p)) {
                return team;
            }
        }
        return null;
    }

}
package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class Game {

    private Main plugin;
    private int id;
    private String name, schematic;
    private ArrayList<Player> cached = new ArrayList<>(), players = new ArrayList<>(), spectators = new ArrayList<>();
    private HashMap<ChatColor, Team> teams = new HashMap<>();
    private ArrayList<Squared> protection = new ArrayList<>();
    private Location lobby, spectator;
    private int teamSize, woolSize, min, starting;
    private State state;

    public Game(Main plugin, String path, int id){
        this.plugin = plugin;
        this.id = id;
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
        setState(State.WAITING);
        if (!plugin.getArenas().isSet(path + ".squareds")) return;
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".squareds").getKeys(false)) {
            String nowPath = path + ".squareds." + c;
            protection.add(new Squared(Utils.getStringLocation(plugin.getArenas().get(nowPath + ".min")), Utils.getStringLocation(plugin.getArenas().get(nowPath + ".max")), true, false));
        }
    }

    public void addPlayer(Player p){
        p.teleport(lobby);
        cached.add(p);
        players.add(p);
        givePlayerItems(p);
        Utils.updateSB(p);
    }

    public void removePlayer(Player p){
        cached.remove(p);
        players.remove(p);
        spectators.remove(p);
    }

    public void setSpect(Player p){
        p.teleport(spectator);
        players.remove(p);
        spectators.add(p);
    }

    public void setState(State state){
        this.state = state;
    }

    public boolean isState(State state){
        return this.state.equals(state);
    }

    public void addPlayerRandomTeam(Player p){
        Team t = Utils.getMinorPlayersTeam(this);
        addPlayerTeam(p, t);
        p.sendMessage(plugin.getLang().get("messages.randomTeam").replaceAll("<team>", t.getName()));
    }

    public void addPlayerTeam(Player p, Team team) {
        team.addMember(p);
        p.teleport(team.getSpawn());
        plugin.getKm().giveDefaultKit(p);
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

    public void givePlayerItems(Player p){
        p.getInventory().setItem(4, plugin.getIm().getTeams());
    }

}
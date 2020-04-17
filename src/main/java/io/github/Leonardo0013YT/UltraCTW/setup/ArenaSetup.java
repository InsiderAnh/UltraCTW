package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ArenaSetup {

    private Main plugin;
    private Player p;
    private String name;
    private String schematic;
    private Location lobby, spectator;
    private int min, teamSize;
    private HashMap<Integer, TeamSetup> teams = new HashMap<>();
    private ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
    private TeamSetup actual;

    public ArenaSetup(Main plugin, Player p, String name, String schematic){
        this.plugin = plugin;
        this.p = p;
        this.name = name;
        this.schematic = schematic;
        this.min = 10;
        this.teamSize = 5;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setActual(TeamSetup actual) {
        this.actual = actual;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setSpectator(Location spectator) {
        this.spectator = spectator;
    }

    public Location getSpectator() {
        return spectator;
    }

    public String getName() {
        return name;
    }

    public String getSchematic() {
        return schematic;
    }

    public int getMin() {
        return min;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void save(Player p){

    }

}
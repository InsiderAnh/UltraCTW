package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.utils.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaSetup {

    private Main plugin;
    private Player p;
    private String name;
    private String schematic;
    private Location lobby, spectator;
    private int min, teamSize, woolSize, amountTeams;
    private HashMap<Integer, TeamSetup> teams = new HashMap<>();
    private ArrayList<ChatColor> colors = new ArrayList<>();
    private TeamSetup actual;

    public ArenaSetup(Main plugin, Player p, String name, String schematic){
        this.plugin = plugin;
        this.p = p;
        this.name = name;
        this.schematic = schematic;
        this.min = 10;
        this.teamSize = 5;
        this.woolSize = 2;
        this.amountTeams = 2;
    }

    public ArrayList<ChatColor> getAvailableColors(){
        ArrayList<ChatColor> empty = new ArrayList<>();
        for (TeamSetup ts : teams.values()){
            for (ChatColor color : colors){
                if (ts.getColors().contains(color)) continue;
                empty.add(color);
            }
        }
        return empty;
    }

    public ArrayList<ChatColor> getWools(){
        if (!colors.isEmpty()){
            return colors;
        }
        int max = woolSize * amountTeams;
        int amount = 0;
        ArrayList<ChatColor> materials = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isFormat() || color.equals(ChatColor.RESET) || color.equals(ChatColor.DARK_RED) || color.equals(ChatColor.DARK_BLUE))
                continue;
            amount++;
            colors.add(color);
            if (amount >= max){
                break;
            }
        }
        return materials;
    }

    public HashMap<Integer, TeamSetup> getTeams() {
        return teams;
    }

    public void setAmountTeams(int amountTeams) {
        this.amountTeams = amountTeams;
    }

    public void setWoolSize(int woolSize) {
        this.woolSize = woolSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void setMin(int min) {
        this.min = min;
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

    public int getAmountTeams() {
        return amountTeams;
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

    public int getWoolSize() {
        return woolSize;
    }

    public void save(Player p){

    }

}
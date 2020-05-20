package io.github.Leonardo0013YT.UltraCTW.setup;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamSetup {

    private ChatColor color;
    private Location spawn;
    private HashMap<ChatColor, Location> spawners = new HashMap<>();
    private HashMap<ChatColor, Location> wools = new HashMap<>();
    private ArrayList<ChatColor> colors = new ArrayList<>();

    public TeamSetup(ChatColor color){
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public ArrayList<ChatColor> getColors() {
        return colors;
    }

    public HashMap<ChatColor, Location> getWools() {
        return wools;
    }

    public HashMap<ChatColor, Location> getSpawners() {
        return spawners;
    }
}
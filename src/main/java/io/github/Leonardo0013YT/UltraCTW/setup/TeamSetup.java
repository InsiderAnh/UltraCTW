package io.github.Leonardo0013YT.UltraCTW.setup;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;

public class TeamSetup {

    private ChatColor color;
    private Location spawn;
    private ArrayList<Location> spawners = new ArrayList<>();

    public TeamSetup(ChatColor color){
        this.color = color;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public ArrayList<Location> getSpawners() {
        return spawners;
    }
}
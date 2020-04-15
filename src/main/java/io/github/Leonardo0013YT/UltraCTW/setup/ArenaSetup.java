package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ArenaSetup {

    private Main plugin;
    private Player p;
    private Location lobby, spectator;
    private HashMap<Integer, TeamSetup> teams = new HashMap<>();
    private TeamSetup actual;

    public ArenaSetup(Main plugin, Player p){
        this.plugin = plugin;
        this.p = p;
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

    public void save(Player p){

    }

}
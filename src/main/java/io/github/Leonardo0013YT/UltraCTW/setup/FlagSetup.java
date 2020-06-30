package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.PhaseType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@Getter@Setter
public class FlagSetup {

    private Main plugin;
    private Player p;
    private String name, schematic;
    private Location lobby, spectator;
    private HashMap<Location, Material> mines = new HashMap<>();
    private ArrayList<FlagTeamSetup> teams = new ArrayList<>();
    private ArrayList<Location> upgradeShops = new ArrayList<>();
    private ArrayList<PhaseType> phases = new ArrayList<>();
    private FlagTeamSetup actual;
    private int pool, min, teamSize;

    public FlagSetup(Main plugin, Player p, String name, String schematic) {
        this.plugin = plugin;
        this.p = p;
        this.name = name;
        this.schematic = schematic;
    }

}
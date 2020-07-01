package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class GameFlag {

    private Main plugin;
    private int id, teamSize, starting, min, max, pool;
    private String name, schematic;
    private Location lobby, spectator;
    private ArrayList<Location> npcUpgrades = new ArrayList<>();
    private HashMap<ChatColor, FlagTeam> teams = new HashMap<>();
    private HashMap<Integer, ChatColor> teamsID = new HashMap<>();
    private State state;

    public GameFlag(Main plugin, String path, int id) {
        this.plugin = plugin;
        this.id = id;
        this.name = plugin.getArenas().get(path + ".name");
        plugin.getWc().createEmptyWorld(name);
        this.schematic = plugin.getArenas().get(path + ".schematic");
        this.lobby = Utils.getStringLocation(plugin.getArenas().get(path + ".lobby"));
        plugin.getWc().resetMap(new Location(lobby.getWorld(), 0, 75, 0), schematic);
        this.spectator = Utils.getStringLocation(plugin.getArenas().get(path + ".spectator"));
        this.teamSize = plugin.getArenas().getInt(path + ".teamSize");
        for (String s : plugin.getArenas().getListOrDefault(path + ".npcUpgrade", new ArrayList<>())) {
            npcUpgrades.add(Utils.getStringLocation(s));
        }
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".teams").getKeys(false)) {
            int tid = teams.size();
            ChatColor color = ChatColor.valueOf(c);
            teams.put(color, new FlagTeam(plugin, this, path + ".teams." + c, tid));
            teamsID.put(tid, color);
        }
        this.starting = plugin.getCm().getStarting();
        this.min = plugin.getArenas().getInt(path + ".min");
        this.max = teamSize * teams.size();
        this.pool = plugin.getArenas().getInt(path + ".pool");
        setState(State.WAITING);
        lobby.getWorld().getEntities().stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(Entity::remove);
    }

    public void addPlayer(Player p) {
        Utils.setCleanPlayer(p);
        p.teleport(lobby);
    }

    public boolean isState(State state) {
        return this.state.equals(state);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
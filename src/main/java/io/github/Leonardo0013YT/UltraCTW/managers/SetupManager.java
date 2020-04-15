package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetupManager {

    private Main plugin;
    private HashMap<Player, String> editName = new HashMap<>();
    private HashMap<Player, ArenaSetup> setup = new HashMap<>();

    public SetupManager(Main plugin) {
        this.plugin = plugin;
    }

    public void setSetupName(Player p, String a) {
        editName.put(p, a);
    }

    public String getSetupName(Player p) {
        return editName.get(p);
    }

    public boolean isSetupName(Player p) {
        return editName.containsKey(p);
    }

    public void removeName(Player p) {
        editName.remove(p);
    }

    public void setSetup(Player p, ArenaSetup a) {
        setup.put(p, a);
    }

    public ArenaSetup getSetup(Player p) {
        return setup.get(p);
    }

    public boolean isSetup(Player p) {
        return setup.containsKey(p);
    }

    public void remove(Player p) {
        setup.remove(p);
    }

}
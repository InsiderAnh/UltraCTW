package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.setup.ArenaSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.KitSetup;
import io.github.Leonardo0013YT.UltraCTW.setup.TeamSetup;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetupManager {

    private Main plugin;
    private HashMap<Player, UltraInventory> setupInventory = new HashMap<>();
    private HashMap<Player, String> editName = new HashMap<>();
    private HashMap<Player, ArenaSetup> setup = new HashMap<>();
    private HashMap<Player, TeamSetup> setupTeam = new HashMap<>();
    private HashMap<Player, KitSetup> setupKit = new HashMap<>();

    public SetupManager(Main plugin) {
        this.plugin = plugin;
    }

    public void setSetupKit(Player p, KitSetup ks){
        setupKit.put(p, ks);
    }

    public boolean isSetupKit(Player p){
        return setupKit.containsKey(p);
    }

    public KitSetup getSetupKit(Player p){
        return setupKit.get(p);
    }

    public void removeSetupKit(Player p){
        setupKit.remove(p);
    }

    public void setSetupName(Player p, String a) {
        editName.put(p, a);
    }

    public void setSetupInventory(Player p, UltraInventory a) {
        setupInventory.put(p, a);
    }

    public UltraInventory getSetupInventory(Player p) {
        return setupInventory.get(p);
    }

    public boolean isSetupInventory(Player p) {
        return setupInventory.containsKey(p);
    }

    public void removeInventory(Player p) {
        setupInventory.remove(p);
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

    public void setSetupTeam(Player p, TeamSetup a) {
        setupTeam.put(p, a);
    }

    public TeamSetup getSetupTeam(Player p) {
        return setupTeam.get(p);
    }

    public boolean isSetupTeam(Player p) {
        return setupTeam.containsKey(p);
    }

    public void removeTeam(Player p) {
        setupTeam.remove(p);
    }

}
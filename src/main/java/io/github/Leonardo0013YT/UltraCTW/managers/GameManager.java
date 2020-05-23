package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;

import java.util.HashMap;

public class GameManager {

    private HashMap<Integer, Game> games = new HashMap<>();
    private Main plugin;

    public GameManager(Main plugin){
        this.plugin = plugin;
        reload();
    }

    public void reload(){
        if (!plugin.getArenas().isSet("arenas")) return;
        for (String s : plugin.getArenas().getConfig().getConfigurationSection("arenas").getKeys(false)){
            games.put(games.size(), new Game(plugin, "arenas." + s));
            plugin.sendLogMessage("§aGame §e" + s + ". §aloaded correctly.");
        }
    }

}
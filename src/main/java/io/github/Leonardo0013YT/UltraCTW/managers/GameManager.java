package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    private HashMap<Integer, Game> games = new HashMap<>();
    private HashMap<String, Integer> gameNames = new HashMap<>();
    private HashMap<UUID, Integer> playerGame = new HashMap<>();
    private Main plugin;

    public GameManager(Main plugin){
        this.plugin = plugin;
        reload();
    }

    public void reload(){
        if (!plugin.getArenas().isSet("arenas")) return;
        for (String s : plugin.getArenas().getConfig().getConfigurationSection("arenas").getKeys(false)){
            int id = games.size();
            Game game = new Game(plugin, "arenas." + s, id);
            games.put(id, game);
            gameNames.put(game.getName(), id);
            plugin.sendLogMessage("§aGame §e" + s + ". §aloaded correctly.");
        }
    }

    public Game getGameByName(String name){
        return games.get(gameNames.get(name));
    }

    public Game getGameByPlayer(Player p){
        return games.get(playerGame.get(p.getUniqueId()));
    }

    public void addPlayerGame(Player p, int id){
        Game game = games.get(id);
        game.addPlayer(p);
        playerGame.put(p.getUniqueId(), id);
    }

    public void removePlayerGame(Player p){
        int id = playerGame.get(p.getUniqueId());
        Game game = games.get(id);
        if (game == null) return;
        game.removePlayer(p);
        p.teleport(plugin.getCm().getMainLobby());
    }

    public boolean isPlayerInGame(Player p){
        return playerGame.containsKey(p.getUniqueId());
    }

}
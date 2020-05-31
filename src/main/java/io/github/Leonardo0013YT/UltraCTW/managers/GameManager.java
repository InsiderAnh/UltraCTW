package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GameNoState;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            Game game = new GameNoState(plugin, "arenas." + s, id);
            games.put(id, game);
            gameNames.put(game.getName(), id);
            plugin.sendLogMessage("§aGame §e" + s + "§a loaded correctly.");
        }
    }

    public Game getGameByName(String name){
        return games.get(gameNames.get(name));
    }

    public Game getGameByPlayer(Player p){
        return games.get(playerGame.get(p.getUniqueId()));
    }

    public Game getRandomGame(){
        Game g = null;
        for (Game game : games.values()){
            if (game.isState(State.FINISH) || game.isState(State.RESTARTING)) continue;
            g = game;
        }
        return g;
    }

    public HashMap<Integer, Game> getGames() {
        return games;
    }

    public void addPlayerGame(Player p, int id){
        Game game = games.get(id);
        game.addPlayer(p);
        playerGame.put(p.getUniqueId(), id);
    }

    public void removePlayerGame(Player p, boolean toLobby){
        if (!playerGame.containsKey(p.getUniqueId())) return;
        int id = playerGame.get(p.getUniqueId());
        Game game = games.get(id);
        if (game == null) return;
        game.removePlayer(p);
        if (toLobby){
            p.teleport(plugin.getCm().getMainLobby());
        }
    }

    public boolean isPlayerInGame(Player p){
        return playerGame.containsKey(p.getUniqueId());
    }

}
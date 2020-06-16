package io.github.Leonardo0013YT.UltraCTW.managers;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.GameNoState;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class GameManager {

    private HashMap<Integer, Game> games = new HashMap<>();
    private HashMap<String, Integer> gameNames = new HashMap<>();
    private HashMap<UUID, Integer> playerGame = new HashMap<>();
    private Game selectedGame;
    private Main plugin;

    public GameManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        games.clear();
        gameNames.clear();
        if (!plugin.getArenas().isSet("arenas")) return;
        for (String s : plugin.getArenas().getConfig().getConfigurationSection("arenas").getKeys(false)) {
            int id = games.size();
            Game game = new GameNoState(plugin, "arenas." + s, id);
            games.put(id, game);
            gameNames.put(game.getName(), id);
            plugin.sendLogMessage("§aGame §e" + s + "§a loaded correctly.");
        }
        reset();
    }

    public void reset() {
        Game selectedGame = new ArrayList<>(games.values()).get(ThreadLocalRandom.current().nextInt(0, games.values().size()));
        setSelectedGame(selectedGame);
    }

    public void reset(Game game) {
        ArrayList<Game> back = new ArrayList<>(games.values());
        back.remove(game);
        Game selectedGame = new ArrayList<>(back).get(ThreadLocalRandom.current().nextInt(0, back.size()));
        setSelectedGame(selectedGame);
    }

    public Game getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    public Game getGameByName(String name) {
        return games.get(gameNames.get(name));
    }

    public Game getGameByPlayer(Player p) {
        return games.get(playerGame.get(p.getUniqueId()));
    }

    public HashMap<Integer, Game> getGames() {
        return games;
    }

    public void addPlayerGame(Player p, int id) {
        Game game = games.get(id);
        game.addPlayer(p);
        playerGame.put(p.getUniqueId(), id);
    }

    public void removePlayerGame(Player p, boolean toLobby) {
        if (!playerGame.containsKey(p.getUniqueId())) return;
        int id = playerGame.get(p.getUniqueId());
        Game game = games.get(id);
        if (game == null) return;
        game.removePlayer(p);
        NametagEdit.getApi().clearNametag(p);
        playerGame.remove(p.getUniqueId());
        Utils.updateSB(p);
        if (toLobby) {
            if (plugin.getCm().getMainLobby() != null) {
                if (plugin.getCm().getMainLobby().getWorld() != null) {
                    p.teleport(plugin.getCm().getMainLobby());
                }
            }
        }
    }

    public boolean isPlayerInGame(Player p) {
        return playerGame.containsKey(p.getUniqueId());
    }

}
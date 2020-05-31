package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public interface Game {


    void addPlayer(Player p);

    void removePlayer(Player p);

    void reset();

    void setSpect(Player p);

    void update();

    void win(Team team);

    void setState(State state);

    boolean isState(State state);

    void addPlayerRandomTeam(Player p);

    void addPlayerTeam(Player p, Team team);

    void addWinEffects(WinEffect e);

    void addWinDance(WinDance e);

    void addKillEffects(KillEffect e);

    void removePlayerTeam(Player p, Team team);

    GamePlayer getGamePlayer(Player p);

    Team getTeamByID(int id);

    Team getTeamByColor(ChatColor color);

    Team getTeamByWool(ChatColor color);

    void joinRandomTeam(Player p);

    void removePlayerAllTeam(Player p);

    Team getLastTeam();

    int getTeamAlive();

    Team getTeamPlayer(Player p);

    void givePlayerItems(Player p);

    Squared getPlayerSquared(Player p);

    Squared getPlayerSquared(Location loc);

    int getId();

    String getName();

    String getSchematic();

    ArrayList<Player> getCached();

    ArrayList<Player> getPlayers();

    ArrayList<Player> getSpectators();

    HashMap<ChatColor, Team> getTeams();

    HashMap<Integer, ChatColor> getTeamsID();

    HashMap<Player, GamePlayer> getGamePlayer();

    ArrayList<Squared> getProtection();

    ArrayList<WinEffect> getWinEffects();

    ArrayList<WinDance> getWinDances();

    ArrayList<KillEffect> getKillEffects();

    Location getLobby();

    Location getSpectator();

    int getTeamSize();

    int getWoolSize();

    int getMin();

    int getStarting();

    State getState();

    int getDefKit();

    HashMap<Location, ItemStack> getWools();
}
package io.github.Leonardo0013YT.UltraCTW.game;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Game {

    private Main plugin;
    private int id;
    private String name, schematic;
    private ArrayList<Player> cached = new ArrayList<>(), players = new ArrayList<>(), spectators = new ArrayList<>();
    private HashMap<ChatColor, Team> teams = new HashMap<>();
    private HashMap<Integer, ChatColor> teamsID = new HashMap<>();
    private HashMap<Player, GamePlayer> gamePlayer = new HashMap<>();
    private ArrayList<Squared> protection = new ArrayList<>();
    private ArrayList<WinEffect> winEffects = new ArrayList<>();
    private ArrayList<WinDance> winDances = new ArrayList<>();
    private ArrayList<KillEffect> killEffects = new ArrayList<>();
    private Location lobby, spectator;
    private int teamSize, woolSize, min, starting;
    private State state;

    public Game(Main plugin, String path, int id){
        this.plugin = plugin;
        this.id = id;
        this.name = plugin.getArenas().get(path + ".name");
        plugin.getWc().createEmptyWorld(name);
        this.schematic = plugin.getArenas().get(path + ".schematic");
        this.lobby = Utils.getStringLocation(plugin.getArenas().get(path + ".lobby"));
        plugin.getWc().resetMap(lobby, schematic);
        this.spectator = Utils.getStringLocation(plugin.getArenas().get(path + ".spectator"));
        this.teamSize = plugin.getArenas().getInt(path + ".teamSize");
        this.woolSize = plugin.getArenas().getInt(path + ".woolSize");
        this.min = plugin.getArenas().getInt(path + ".min");
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".teams").getKeys(false)){
            int tid = teams.size();
            ChatColor color = ChatColor.valueOf(c);
            teams.put(color, new Team(plugin, this, path + ".teams." + c, tid));
            teamsID.put(tid, color);
        }
        setState(State.WAITING);
        if (!plugin.getArenas().isSet(path + ".squareds")) return;
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".squareds").getKeys(false)) {
            String nowPath = path + ".squareds." + c;
            protection.add(new Squared(Utils.getStringLocation(plugin.getArenas().get(nowPath + ".min")), Utils.getStringLocation(plugin.getArenas().get(nowPath + ".max")), true, false));
        }
    }

    public void addPlayer(Player p){
        Utils.setCleanPlayer(p);
        p.teleport(lobby);
        cached.add(p);
        players.add(p);
        givePlayerItems(p);
        Utils.updateSB(p);
        gamePlayer.put(p, new GamePlayer(p));
    }

    public void removePlayer(Player p){
        removePlayerAllTeam(p);
        cached.remove(p);
        players.remove(p);
        spectators.remove(p);
        Utils.setCleanPlayer(p);
        gamePlayer.remove(p);
    }

    public void reset(){
        winDances.forEach(WinDance::stop);
        winEffects.forEach(WinEffect::stop);
        killEffects.forEach(KillEffect::stop);
        gamePlayer.clear();
        spectators.clear();
        cached.clear();
        players.clear();
        teams.values().forEach(Team::reset);
        plugin.getWc().resetMap(lobby, schematic);
    }

    public void setSpect(Player p){
        p.teleport(spectator);
        players.remove(p);
        spectators.add(p);
    }

    public void update(){
        Utils.updateSB(this);
        teams.values().forEach(Team::updateSpawner);
    }

    public void win(Team team){
        GameWin gw = new GameWin(this);
        gw.setTeamWin(team);
        List<String> top = gw.getTop();
        String[] s1 = top.get(0).split(":");
        String[] s2 = top.get(1).split(":");
        String[] s3 = top.get(2).split(":");
        for (Player on : cached){
            if (team.getMembers().contains(on)) continue;
            plugin.getVc().getNMS().sendTitle(on, plugin.getLang().get("titles.lose.title"), plugin.getLang().get("titles.lose.subtitle"), 0, 40, 0);
            for (String s : plugin.getLang().getList("messages.win")){
                on.sendMessage(s.replaceAll("&", "§").replaceAll("<winner>", gw.getWinner()).replaceAll("<number1>", s1[1]).replaceAll("<top1>", s1[0]).replaceAll("<color1>", "" + ChatColor.valueOf(s1[2])).replaceAll("<number2>", s2[1]).replaceAll("<top2>", s2[0]).replaceAll("<color2>", "" + ChatColor.valueOf(s2[2])).replaceAll("<number3>", s3[1]).replaceAll("<top3>", s3[0]).replaceAll("<color3>", "" + ChatColor.valueOf(s3[2])));
            }
        }
        for (Player w : team.getMembers()){
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(w);
            if (ctw == null) continue;
            plugin.getVc().getNMS().sendTitle(w, plugin.getLang().get("titles.win.title"), plugin.getLang().get("titles.win.subtitle"), 0, 40, 0);
            plugin.getWem().execute(this, w, ctw.getWinEffect());
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                ArrayList<Player> back = new ArrayList<>(cached);
                for (Player on : back) {
                    plugin.getGm().removePlayerGame(on, false);
                    Game g = plugin.getGm().getRandomGame();
                    plugin.getGm().addPlayerGame(on, g.getId());
                }
                reset();
            }
        }.runTaskLater(plugin, 20 * 15);
    }

    public void setState(State state){
        this.state = state;
    }

    public boolean isState(State state){
        return this.state.equals(state);
    }

    public void addPlayerRandomTeam(Player p){
        Team t = Utils.getMinorPlayersTeam(this);
        addPlayerTeam(p, t);
        p.sendMessage(plugin.getLang().get("messages.randomTeam").replaceAll("<team>", t.getName()));
    }

    public void addPlayerTeam(Player p, Team team) {
        p.getInventory().clear();
        team.addMember(p);
        p.teleport(team.getSpawn());
        plugin.getKm().giveDefaultKit(p, team);
        Utils.updateSB(p);
        NametagEdit.getApi().setNametag(p, team.getColor() + "", "");
    }

    public void addWinEffects(WinEffect e){
        winEffects.add(e);
    }

    public void addWinDance(WinDance e){
        winDances.add(e);
    }

    public void addKillEffects(KillEffect e){
        killEffects.add(e);
    }

    public void removePlayerTeam(Player p, Team team) {
        team.removeMember(p);
    }

    public GamePlayer getGamePlayer(Player p){
        return gamePlayer.getOrDefault(p, new GamePlayer(p));
    }

    public Team getTeamByID(int id){
        return teams.get(teamsID.get(id));
    }

    public Team getTeamByColor(ChatColor color) {
        return teams.get(color);
    }

    public Team getTeamByWool(ChatColor color) {
        Team t = null;
        for (Team tt : teams.values()){
            if (tt.getColors().contains(color)){
                return tt;
            }
        }
        return null;
    }

    public void joinRandomTeam(Player p) {
        for (Team team : teams.values()) {
            if (team.getTeamSize() < teamSize) {
                addPlayerTeam(p, team);
                break;
            }
        }
    }

    public void removePlayerAllTeam(Player p) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(p)) {
                removePlayerTeam(p, team);
            }
        }
    }

    public Team getLastTeam() {
        for (Team team : teams.values()) {
            if (team.getTeamSize() > 0) {
                return team;
            }
        }
        return null;
    }

    public int getTeamAlive() {
        int c = 0;
        for (Team team : teams.values()) {
            if (team.getTeamSize() > 0) {
                c++;
            }
        }
        return c;
    }

    public Team getTeamPlayer(Player p) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(p)) {
                return team;
            }
        }
        return null;
    }

    public void givePlayerItems(Player p){
        p.getInventory().setItem(4, plugin.getIm().getTeams());
    }

    public Squared getPlayerSquared(Player p){
        for (Squared s : protection){
            if (s.isInCuboid(p)) {
                return s;
            }
        }
        return null;
    }

    public Squared getPlayerSquared(Location loc){
        for (Squared s : protection){
            if (s.isInCuboid(loc)) {
                return s;
            }
        }
        return null;
    }

}
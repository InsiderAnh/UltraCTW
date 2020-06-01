package io.github.Leonardo0013YT.UltraCTW.game;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.interfaces.*;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameNoState implements Game {

    private Main plugin;
    private int id;
    private String name, schematic;
    private ArrayList<Player> cached = new ArrayList<>(), players = new ArrayList<>(), spectators = new ArrayList<>(), inLobby = new ArrayList<>(), inGame = new ArrayList<>();
    private HashMap<ChatColor, Team> teams = new HashMap<>();
    private HashMap<Integer, ChatColor> teamsID = new HashMap<>();
    private HashMap<Player, GamePlayer> gamePlayer = new HashMap<>();
    private ArrayList<Squared> protection = new ArrayList<>();
    private ArrayList<WinEffect> winEffects = new ArrayList<>();
    private ArrayList<WinDance> winDances = new ArrayList<>();
    private ArrayList<KillEffect> killEffects = new ArrayList<>();
    private HashMap<Location, ItemStack> wools = new HashMap<>();
    private Squared lobbyProtection;
    private Location lobby, spectator;
    private int teamSize, woolSize, min, starting, defKit;
    private State state;

    public GameNoState(Main plugin, String path, int id){
        this.plugin = plugin;
        this.id = id;
        this.name = plugin.getArenas().get(path + ".name");
        plugin.getWc().createEmptyWorld(name);
        this.schematic = plugin.getArenas().get(path + ".schematic");
        this.lobby = Utils.getStringLocation(plugin.getArenas().get(path + ".lobby"));
        if (plugin.getArenas().isSet(path + ".lobbyProtection.min")) {
            this.lobbyProtection = new Squared(Utils.getStringLocation(plugin.getArenas().get(path + ".lobbyProtection.max")), Utils.getStringLocation(plugin.getArenas().get(path + ".lobbyProtection.min")), false, true);
        }
        plugin.getWc().resetMap(lobby, schematic);
        this.spectator = Utils.getStringLocation(plugin.getArenas().get(path + ".spectator"));
        this.teamSize = plugin.getArenas().getInt(path + ".teamSize");
        this.woolSize = plugin.getArenas().getInt(path + ".woolSize");
        this.defKit = plugin.getArenas().getIntOrDefault(path + ".defKit", 0);
        this.starting = plugin.getCm().getStarting();
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

    @Override
    public void addPlayer(Player p){
        gamePlayer.put(p, new GamePlayer(p));
        p.teleport(lobby);
        Utils.setCleanPlayer(p);
        inLobby.add(p);
        cached.add(p);
        players.add(p);
        givePlayerItems(p);
        Utils.updateSB(p);
        checkStart();
    }

    @Override
    public void removePlayer(Player p){
        Utils.setCleanPlayer(p);
        removePlayerAllTeam(p);
        cached.remove(p);
        players.remove(p);
        spectators.remove(p);
        inLobby.remove(p);
        inGame.remove(p);
        if (gamePlayer.containsKey(p)){
            GamePlayer gp = gamePlayer.get(p);
            gp.reset();
            gamePlayer.remove(p);
        }
    }

    @Override
    public void checkStart(){
        if (isState(State.WAITING)){
            if (cached.size() >= min){
                setState(State.STARTING);
            }
        }
    }

    @Override
    public void reset(){
        wools.clear();
        inGame.clear();
        inLobby.clear();
        winDances.forEach(WinDance::stop);
        winEffects.forEach(WinEffect::stop);
        killEffects.forEach(KillEffect::stop);
        gamePlayer.clear();
        spectators.clear();
        cached.clear();
        players.clear();
        teams.values().forEach(Team::reset);
        plugin.getWc().resetMap(lobby, schematic);
        starting = plugin.getCm().getStarting();
        setState(State.WAITING);
    }

    @Override
    public void setSpect(Player p){
        p.teleport(spectator);
        players.remove(p);
        spectators.add(p);
    }

    @Override
    public void update(){
        Utils.updateSB(this);
        if (isState(State.STARTING)){
            if (starting == 30 || starting == 15 || starting == 10 || starting == 5 || starting == 4 || starting == 3 || starting == 2 || starting == 1) {
                sendGameTitle(plugin.getLang().get(null, "titles.starting.title").replaceAll("<time>", String.valueOf(starting)), plugin.getLang().get(null, "titles.starting.subtitle").replaceAll("<time>", String.valueOf(starting)), 0, 40, 0);
                sendGameMessage(plugin.getLang().get(null, "messages.starting").replaceAll("<starting>", String.valueOf(starting)).replaceAll("<s>", (starting > 1) ? "s" : ""));
                sendGameSound(XSound.BLOCK_NOTE_BLOCK_PLING.parseSound());
            }
            if (starting == 29 || starting == 14 || starting == 9 || starting == 0) {
                sendGameTitle("", "", 0, 1, 0);
            }
            if (starting == 0){
                setState(State.GAME);
                for (String s : plugin.getLang().getList("messages.start")){
                    sendGameMessage(s);
                }
                for (Player on : cached){
                    if (getTeamPlayer(on) == null){
                        addPlayerRandomTeam(on);
                    }
                }
            }
            starting--;
        }
        if (isState(State.GAME)){
            teams.values().forEach(Team::updateSpawner);
        }
    }

    @Override
    public void win(Team team){
        GameWin gw = new GameWin(this);
        gw.setTeamWin(team);
        List<String> top = gw.getTop();
        String[] s1 = top.get(0).split(":");
        String[] s2 = top.get(1).split(":");
        String[] s3 = top.get(2).split(":");
        for (Player on : cached){
            if (!team.getMembers().contains(on)) {
                plugin.getVc().getNMS().sendTitle(on, plugin.getLang().get("titles.lose.title"), plugin.getLang().get("titles.lose.subtitle"), 0, 40, 0);
                continue;
            }
            for (String s : plugin.getLang().getList("messages.win")){
                on.sendMessage(s.replaceAll("&", "§").replaceAll("<winner>", gw.getWinner()).replaceAll("<number1>", s1[1]).replaceAll("<top1>", s1[0]).replaceAll("<color1>", "" + ChatColor.valueOf(s1[2])).replaceAll("<number2>", s2[1]).replaceAll("<top2>", s2[0]).replaceAll("<color2>", "" + ChatColor.valueOf(s2[2])).replaceAll("<number3>", s3[1]).replaceAll("<top3>", s3[0]).replaceAll("<color3>", "" + ChatColor.valueOf(s3[2])));
            }
        }
        for (Player w : team.getMembers()){
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(w);
            if (ctw == null) continue;
            plugin.getVc().getNMS().sendTitle(w, plugin.getLang().get("titles.win.title").replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titles.win.subtitle"), 0, 40, 0);
            plugin.getWem().execute(this, w, ctw.getWinEffect());
        }
        new BukkitRunnable(){
            @Override
            public void run(){
                ArrayList<Player> back = new ArrayList<>(cached);
                for (Player on : back) {
                    plugin.getGm().removePlayerGame(on, false);
                    Game g = plugin.getGm().getRandomGame(GameNoState.this);
                    plugin.getGm().addPlayerGame(on, g.getId());
                }
                reset();
            }
        }.runTaskLater(plugin, 20 * 15);
    }

    @Override
    public void sendGameMessage(String msg) {
        for (Player p : cached) {
            p.sendMessage(msg);
        }
    }

    @Override
    public void sendGameTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player p : cached) {
            plugin.getVc().getNMS().sendTitle(p, title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    @Override
    public void sendGameSound(Sound sound) {
        for (Player p : cached) {
            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
        }
    }

    @Override
    public void setState(State state){
        this.state = state;
    }

    @Override
    public boolean isState(State state){
        return this.state.equals(state);
    }

    @Override
    public void addPlayerRandomTeam(Player p){
        Team t = Utils.getMinorPlayersTeam(this);
        addPlayerTeam(p, t);
        p.sendMessage(plugin.getLang().get("messages.randomTeam").replaceAll("<team>", t.getName()));
    }

    @Override
    public void addPlayerTeam(Player p, Team team) {
        p.getInventory().clear();
        team.addMember(p);
        p.teleport(team.getSpawn());
        plugin.getKm().giveDefaultKit(p, this, team);
        Utils.updateSB(p);
        inGame.add(p);
        inLobby.remove(p);
        NametagEdit.getApi().setNametag(p, team.getColor() + "", "");
    }

    @Override
    public void addWinEffects(WinEffect e){
        winEffects.add(e);
    }

    @Override
    public void addWinDance(WinDance e){
        winDances.add(e);
    }

    @Override
    public void addKillEffects(KillEffect e){
        killEffects.add(e);
    }

    @Override
    public void removePlayerTeam(Player p, Team team) {
        team.removeMember(p);
    }

    @Override
    public GamePlayer getGamePlayer(Player p){
        return gamePlayer.getOrDefault(p, new GamePlayer(p));
    }

    @Override
    public Team getTeamByID(int id){
        return teams.get(teamsID.get(id));
    }

    @Override
    public Team getTeamByColor(ChatColor color) {
        return teams.get(color);
    }

    @Override
    public Team getTeamByWool(ChatColor color) {
        for (Team tt : teams.values()){
            if (tt.getColors().contains(color)){
                return tt;
            }
        }
        return null;
    }

    @Override
    public void joinRandomTeam(Player p) {
        for (Team team : teams.values()) {
            if (team.getTeamSize() < teamSize) {
                addPlayerTeam(p, team);
                break;
            }
        }
    }

    @Override
    public void removePlayerAllTeam(Player p) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(p)) {
                removePlayerTeam(p, team);
            }
        }
    }

    @Override
    public Team getLastTeam() {
        for (Team team : teams.values()) {
            if (team.getTeamSize() > 0) {
                return team;
            }
        }
        return null;
    }

    @Override
    public int getTeamAlive() {
        int c = 0;
        for (Team team : teams.values()) {
            if (team.getTeamSize() > 0) {
                c++;
            }
        }
        return c;
    }

    @Override
    public Team getTeamPlayer(Player p) {
        for (Team team : teams.values()) {
            if (team.getMembers().contains(p)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public void givePlayerItems(Player p){
        p.getInventory().setItem(4, plugin.getIm().getTeams());
    }

    @Override
    public Squared getPlayerSquared(Player p){
        for (Squared s : protection){
            if (s.isInCuboid(p)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Squared getPlayerSquared(Location loc){
        for (Squared s : protection){
            if (s.isInCuboid(loc)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSchematic() {
        return schematic;
    }

    @Override
    public ArrayList<Player> getCached() {
        return cached;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public ArrayList<Player> getSpectators() {
        return spectators;
    }

    @Override
    public HashMap<ChatColor, Team> getTeams() {
        return teams;
    }

    @Override
    public HashMap<Integer, ChatColor> getTeamsID() {
        return teamsID;
    }

    @Override
    public HashMap<Player, GamePlayer> getGamePlayer() {
        return gamePlayer;
    }

    @Override
    public ArrayList<Squared> getProtection() {
        return protection;
    }

    @Override
    public ArrayList<WinEffect> getWinEffects() {
        return winEffects;
    }

    @Override
    public ArrayList<WinDance> getWinDances() {
        return winDances;
    }

    @Override
    public ArrayList<KillEffect> getKillEffects() {
        return killEffects;
    }

    @Override
    public Location getLobby() {
        return lobby;
    }

    @Override
    public Location getSpectator() {
        return spectator;
    }

    @Override
    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public int getWoolSize() {
        return woolSize;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getStarting() {
        return starting;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public int getDefKit() {
        return defKit;
    }

    @Override
    public HashMap<Location, ItemStack> getWools() {
        return wools;
    }

    @Override
    public ArrayList<Player> getInLobby() {
        return inLobby;
    }

    @Override
    public ArrayList<Player> getInGame() {
        return inGame;
    }

    @Override
    public Squared getLobbyProtection() {
        return lobbyProtection;
    }
}
package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.flag.Mine;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.objects.MineCountdown;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Getter
public class GameFlag {

    private Main plugin;
    private int id, teamSize, starting, min, max, pool, time = 0;
    private ArrayList<Player> cached = new ArrayList<>(), players = new ArrayList<>(), spectators = new ArrayList<>();
    private String name, schematic;
    private Location lobby, spectator;
    private ArrayList<Location> npcUpgrades = new ArrayList<>();
    private ArrayList<WinEffect> winEffects = new ArrayList<>();
    private ArrayList<WinDance> winDances = new ArrayList<>();
    private ArrayList<KillEffect> killEffects = new ArrayList<>();
    private HashMap<ChatColor, FlagTeam> teams = new HashMap<>();
    private HashMap<Integer, ChatColor> teamsID = new HashMap<>();
    private HashMap<Player, GamePlayer> gamePlayer = new HashMap<>();
    private HashMap<Location, Material> mines = new HashMap<>();
    private HashMap<Location, MineCountdown> countdowns = new HashMap<>();
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
        if (plugin.getArenas().isSet(path + ".mines")) {
            for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".mines").getKeys(false)) {
                Location loc = Utils.getStringLocation(plugin.getArenas().get(path + ".mines." + c + ".loc"));
                Material material = Material.valueOf(plugin.getArenas().get(path + ".mines." + c + ".material"));
                mines.put(loc, material);
            }
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
        gamePlayer.put(p, new GamePlayer(p));
        Utils.setCleanPlayer(p);
        cached.add(p);
        players.add(p);
        p.teleport(lobby);
        Utils.updateSB(p);
        checkStart();
    }

    public void removePlayer(Player p) {
        plugin.getNpc().removePlayer(p);
        Utils.setCleanPlayer(p);
        removePlayerAllTeam(p);
        cached.remove(p);
        players.remove(p);
        spectators.remove(p);
        if (gamePlayer.containsKey(p)) {
            GamePlayer gp = gamePlayer.get(p);
            gp.reset();
            gamePlayer.remove(p);
        }
        checkCancel();
        checkWin();
    }

    public void checkCancel() {
        if (isState(State.STARTING)) {
            if (min > players.size()) {
                cancel();
            }
        }
    }

    public void cancel() {
        this.starting = plugin.getCm().getStarting();
        setState(State.WAITING);
        sendGameMessage(plugin.getLang().get(null, "messages.cancelStart"));
        sendGameTitle(plugin.getLang().get(null, "titles.cancel.title"), plugin.getLang().get(null, "titles.cancel.subtitle"), 0, 40, 0);
        sendGameSound(plugin.getCm().getCancelStartSound());
    }

    public void checkStart() {
        if (isState(State.WAITING)) {
            if (players.size() >= min) {
                setState(State.STARTING);
            }
        }
    }

    public void reset() {
        winDances.forEach(WinDance::stop);
        winEffects.forEach(WinEffect::stop);
        killEffects.forEach(KillEffect::stop);
        gamePlayer.clear();
        spectators.clear();
        cached.clear();
        players.clear();
        teams.values().forEach(FlagTeam::reset);
        plugin.getWc().resetMap(new Location(lobby.getWorld(), 0, 75, 0), schematic);
        lobby.getWorld().setTime(500);
        lobby.getWorld().getEntities().stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(Entity::remove);
        starting = plugin.getCm().getStarting();
        setState(State.WAITING);
        time = 0;
    }

    public void update() {
        Utils.updateSB(this);
        if (isState(State.STARTING)) {
            if (starting == 30 || starting == 15 || starting == 10 || starting == 5 || starting == 4 || starting == 3 || starting == 2 || starting == 1) {
                sendGameTitle(plugin.getLang().get("titles.starting.title").replaceAll("<time>", String.valueOf(starting)), plugin.getLang().get("titles.starting.subtitle").replaceAll("<time>", String.valueOf(starting)), 0, 40, 0);
                sendGameMessage(plugin.getLang().get("messages.starting").replaceAll("<starting>", String.valueOf(starting)).replaceAll("<s>", (starting > 1) ? "s" : ""));
                sendGameSound(XSound.BLOCK_NOTE_BLOCK_PLING.parseSound());
            }
            if (starting == 29 || starting == 14 || starting == 9 || starting == 0) {
                sendGameTitle("", "", 0, 1, 0);
            }
            if (starting == 0) {
                setState(State.GAME);
                for (Player on : players) {
                    if (getTeamPlayer(on) == null) {
                        joinRandomTeam(on);
                    }
                }
                for (FlagTeam ft : teams.values()){
                    ft.fillLifes();
                }
            }
            starting--;
        }
        if (isState(State.GAME)) {
            time++;
            countdowns.values().forEach(MineCountdown::reduce);
            Iterator<Location> cools = countdowns.keySet().iterator();
            while (cools.hasNext()) {
                Location l = cools.next();
                MineCountdown mc = countdowns.get(l);
                if (mc.getTime() <= 0) {
                    Block b = l.getBlock();
                    Mine mine = plugin.getFm().getMines().get(mc.getMine());
                    b.setType(mine.getMaterial());
                    cools.remove();
                }
            }
        }
    }

    public void checkWin() {
        if (isState(State.GAME)) {
            int al = getTeamAlive();
            if (al == 1) {
                FlagTeam t = getLastTeam();
                win(t);
            } else if (al == 0) {
                reset();
            }
        }
    }

    public void win(FlagTeam team) {
        if (plugin.isStop()) return;
        setState(State.FINISH);
    }

    public void addPlayerTeam(Player p, FlagTeam team) {
        team.addMember(p);
        p.teleport(team.getSpawn());
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        for (Location s : npcUpgrades) {
            plugin.getSkm().spawnShopKeeper(p, s, ctw.getShopKeeper(), NPCType.UPGRADES);
        }
    }

    public GamePlayer getGamePlayer(Player p){
        return gamePlayer.get(p);
    }

    public boolean isGracePeriod(){
        return plugin.getCm().getGracePeriod() - time > 0;
    }

    public void removePlayerTeam(Player p, FlagTeam team) {
        team.removeMember(p);
    }

    public void joinRandomTeam(Player p) {
        for (FlagTeam team : teams.values()) {
            if (team.getTeamSize() < teamSize) {
                addPlayerTeam(p, team);
                break;
            }
        }
    }

    public void removePlayerAllTeam(Player p) {
        for (FlagTeam team : teams.values()) {
            if (team.getMembers().contains(p)) {
                removePlayerTeam(p, team);
            }
        }
    }

    public FlagTeam getLastTeam() {
        for (FlagTeam team : teams.values()) {
            if (team.getTeamSize() > 0) {
                return team;
            }
        }
        return null;
    }

    public int getTeamAlive() {
        int c = 0;
        for (FlagTeam team : teams.values()) {
            if (team.getTeamSize() > 0) {
                c++;
            }
        }
        return c;
    }

    public FlagTeam getTeamPlayer(Player p) {
        for (FlagTeam team : teams.values()) {
            if (team.getMembers().contains(p)) {
                return team;
            }
        }
        return null;
    }

    public void sendGameMessage(String msg) {
        for (Player p : cached) {
            p.sendMessage(msg);
        }
    }

    public void sendGameTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player p : cached) {
            plugin.getVc().getNMS().sendTitle(p, title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public void sendGameSound(Sound sound) {
        for (Player p : cached) {
            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
        }
    }

    public boolean isState(State state) {
        return this.state.equals(state);
    }

    public void setState(State state) {
        this.state = state;
    }

}
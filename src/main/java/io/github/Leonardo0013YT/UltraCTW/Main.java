package io.github.Leonardo0013YT.UltraCTW;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.Leonardo0013YT.UltraCTW.adapters.ICTWPlayerAdapter;
import io.github.Leonardo0013YT.UltraCTW.cmds.CTWCMD;
import io.github.Leonardo0013YT.UltraCTW.cmds.SetupCMD;
import io.github.Leonardo0013YT.UltraCTW.config.Settings;
import io.github.Leonardo0013YT.UltraCTW.controllers.VersionController;
import io.github.Leonardo0013YT.UltraCTW.controllers.WorldController;
import io.github.Leonardo0013YT.UltraCTW.database.MySQLDatabase;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.IDatabase;
import io.github.Leonardo0013YT.UltraCTW.listeners.MenuListener;
import io.github.Leonardo0013YT.UltraCTW.listeners.PlayerListener;
import io.github.Leonardo0013YT.UltraCTW.listeners.SetupListener;
import io.github.Leonardo0013YT.UltraCTW.listeners.WorldListener;
import io.github.Leonardo0013YT.UltraCTW.managers.*;
import io.github.Leonardo0013YT.UltraCTW.menus.GameMenu;
import io.github.Leonardo0013YT.UltraCTW.menus.SetupMenu;
import io.github.Leonardo0013YT.UltraCTW.menus.UltraInventoryMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class Main extends JavaPlugin {

    private static Main instance;
    private Gson ctw;
    private Settings arenas, lang, menus, kits, sources, windance, wineffect, killsound, taunt, trail, parting, killeffect;
    private boolean debugMode;
    private GameManager gm;
    private ConfigManager cm;
    private AddonManager adm;
    private SetupManager sm;
    private SetupMenu sem;
    private ItemManager im;
    private KitManager km;
    private ScoreboardManager sb;
    private WorldController wc;
    private UltraInventoryMenu uim;
    private GameMenu gem;
    private IDatabase db;
    private VersionController vc;
    private WinDancesManager wdm;
    private WinEffectsManager wem;
    private TrailsManager tlm;
    private TauntsManager tm;
    private PartingManager pm;
    private KillSoundManager ksm;
    private KillEffectsManager kem;
    private TaggedManager tgm;
    private ShopManager shm;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        vc = new VersionController(this);
        setupSounds();
        saveConfig();
        ctw = new GsonBuilder().registerTypeAdapter(CTWPlayer.class, new ICTWPlayerAdapter()).create();
        arenas = new Settings(this, "arenas", false, false);
        lang = new Settings(this, "lang", true, false);
        sources = new Settings(this, "sources", true, false);
        menus = new Settings(this, "menus", false, false);
        kits = new Settings(this, "kits", false, false);
        windance = new Settings(this, "windance", false, false);
        wineffect = new Settings(this, "wineffect", false, false);
        killsound = new Settings(this, "killsounds", false, false);
        taunt = new Settings(this, "taunts", false, false);
        trail = new Settings(this, "trails", false, false);
        parting = new Settings(this, "partings", false, false);
        killeffect = new Settings(this, "killeffect", false, false);
        debugMode = getConfig().getBoolean("debugMode");
        wc = new WorldController(this);
        db = new MySQLDatabase(this);
        cm = new ConfigManager(this);
        adm = new AddonManager(this);
        im = new ItemManager(this);
        sm = new SetupManager(this);
        sem = new SetupMenu(this);
        gm = new GameManager(this);
        uim = new UltraInventoryMenu(this);
        km = new KitManager(this);
        sb = new ScoreboardManager(this);
        gem = new GameMenu(this);
        wdm = new WinDancesManager(this);
        wdm.loadWinDances();
        wem = new WinEffectsManager(this);
        wem.loadWinEffects();
        tlm = new TrailsManager(this);
        tlm.loadTrails();
        tm = new TauntsManager(this);
        tm.loadTaunts();
        pm = new PartingManager(this);
        pm.loadPartings();
        ksm = new KillSoundManager(this);
        ksm.loadKillSounds();
        kem = new KillEffectsManager(this);
        kem.loadKillEffects();
        tgm = new TaggedManager(this);
        shm = new ShopManager(this);
        getCommand("ctws").setExecutor(new SetupCMD(this));
        getCommand("ctw").setExecutor(new CTWCMD(this));
        getServer().getPluginManager().registerEvents(new SetupListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldListener(this), this);
        new BukkitRunnable(){
            @Override
            public void run() {
                getGm().getGames().values().forEach(Game::update);
            }
        }.runTaskTimer(this, 20, 20);
    }

    @Override
    public void onDisable() {
        for (Player on : Bukkit.getOnlinePlayers()){
            if (gm.isPlayerInGame(on)){
                gm.removePlayerGame(on, false);
            }
        }
    }

    public void reload(){
        reloadConfig();
        cm.reload();
        adm.reload();
    }

    private void setupSounds() {
        if (vc.is1_9to15()) {
            getConfig().addDefault("sounds.wineffects.vulcanwool", "ENTITY_CHICKEN_EGG");
            getConfig().addDefault("sounds.wineffects.vulcanfire", "ENTITY_CREEPER_HURT");
            if (vc.getVersion().equals("v1_15_R1")) {
                getConfig().addDefault("sounds.wineffects.notes", "ENTITY_FIREWORK_ROCKET_LAUNCH");
                getConfig().addDefault("sounds.wineffects.chicken", "ENTITY_FIREWORK_ROCKET_LAUNCH");
            } else {
                getConfig().addDefault("sounds.wineffects.notes", "ENTITY_FIREWORK_LAUNCH");
                getConfig().addDefault("sounds.wineffects.chicken", "ENTITY_FIREWORK_LAUNCH");
            }
            getConfig().addDefault("sounds.killeffects.tnt", "ENTITY_GENERIC_EXPLODE");
            getConfig().addDefault("sounds.killeffects.squid", "ENTITY_ITEM_PICKUP");
        } else {
            getConfig().addDefault("sounds.wineffects.vulcanwool", "CHICKEN_EGG_POP");
            getConfig().addDefault("sounds.wineffects.vulcanfire", "FUSE");
            getConfig().addDefault("sounds.wineffects.notes", "FIREWORK_LAUNCH");
            getConfig().addDefault("sounds.wineffects.chicken", "FIREWORK_LAUNCH");
            getConfig().addDefault("sounds.killeffects.tnt", "EXPLODE");
            getConfig().addDefault("sounds.killeffects.squid", "ITEM_PICKUP");
        }
    }

    public static Main get() {
        return instance;
    }

    public void sendDebugMessage(String... s) {
        if (debugMode) {
            for (String st : s) {
                Bukkit.getConsoleSender().sendMessage("§b[CTW Debug] §e" + st);
            }
        }
    }

    public void sendLogMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage("§c§lUltraCTW §8| " + msg);
    }

    public void sendLogMessage(String... msg) {
        for (String m : msg) {
            Bukkit.getConsoleSender().sendMessage("§c§lUltraCTW §8| §e" + m);
        }
    }

    public String toStringCTWPlayer(CTWPlayer pd) {
        return ctw.toJson(pd, CTWPlayer.class);
    }

    public CTWPlayer fromStringCTWPlayer(String data) {
        return ctw.fromJson(data, CTWPlayer.class);
    }

}
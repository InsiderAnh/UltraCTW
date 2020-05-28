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
import io.github.Leonardo0013YT.UltraCTW.game.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.IDatabase;
import io.github.Leonardo0013YT.UltraCTW.listeners.MenuListener;
import io.github.Leonardo0013YT.UltraCTW.listeners.PlayerListener;
import io.github.Leonardo0013YT.UltraCTW.listeners.SetupListener;
import io.github.Leonardo0013YT.UltraCTW.managers.*;
import io.github.Leonardo0013YT.UltraCTW.menus.GameMenu;
import io.github.Leonardo0013YT.UltraCTW.menus.SetupMenu;
import io.github.Leonardo0013YT.UltraCTW.menus.UltraInventoryMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class Main extends JavaPlugin {

    private static Main instance;
    private Gson ctw;
    private Settings arenas, lang, menus, kits, sources;
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

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        ctw = new GsonBuilder().registerTypeAdapter(CTWPlayer.class, new ICTWPlayerAdapter()).create();
        arenas = new Settings(this, "arenas", false, false);
        lang = new Settings(this, "lang", true, false);
        sources = new Settings(this, "sources", true, false);
        menus = new Settings(this, "menus", false, false);
        kits = new Settings(this, "kits", false, false);
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
        vc = new VersionController(this);
        getCommand("ctws").setExecutor(new SetupCMD(this));
        getCommand("ctw").setExecutor(new CTWCMD(this));
        getServer().getPluginManager().registerEvents(new SetupListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        new BukkitRunnable(){
            @Override
            public void run() {
                getGm().getGames().values().forEach(Game::update);
            }
        }.runTaskTimer(this, 20, 20);
    }

    @Override
    public void onDisable() {

    }

    public void reload(){
        reloadConfig();
        cm.reload();
        adm.reload();
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
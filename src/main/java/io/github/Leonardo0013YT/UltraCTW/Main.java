package io.github.Leonardo0013YT.UltraCTW;

import io.github.Leonardo0013YT.UltraCTW.cmds.SetupCMD;
import io.github.Leonardo0013YT.UltraCTW.config.Settings;
import io.github.Leonardo0013YT.UltraCTW.controllers.WorldController;
import io.github.Leonardo0013YT.UltraCTW.managers.*;
import io.github.Leonardo0013YT.UltraCTW.menus.SetupMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private Settings arenas, lang;
    private boolean debugMode;
    private GameManager gm;
    private ConfigManager cm;
    private AddonManager adm;
    private SetupManager sm;
    private SetupMenu sem;
    private ItemManager im;
    private WorldController wc;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        arenas = new Settings(this, "arenas", false, false);
        lang = new Settings(this, "lang", true, false);
        debugMode = getConfig().getBoolean("debugMode");
        cm = new ConfigManager(this);
        adm = new AddonManager(this);
        im = new ItemManager(this);
        sm = new SetupManager(this);
        sem = new SetupMenu(this);
        gm = new GameManager(this);
        wc = new WorldController(this);
        getCommand("ctws").setExecutor(new SetupCMD(this));
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

    public WorldController getWc() {
        return wc;
    }

    public Settings getLang() {
        return lang;
    }

    public Settings getArenas() {
        return arenas;
    }

    public ItemManager getIm() {
        return im;
    }

    public SetupManager getSm() {
        return sm;
    }

    public SetupMenu getSem() {
        return sem;
    }

    public GameManager getGm() {
        return gm;
    }

    public ConfigManager getCm() {
        return cm;
    }

    public AddonManager getAdm() {
        return adm;
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

}
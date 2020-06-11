package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

@Getter
public class ConfigManager {

    private Main plugin;
    private boolean placeholdersAPI, redPanelInLocked;
    private Location mainLobby;
    private short redPanelData;
    private Material back, redPanelMaterial;
    private Sound upgradeSound, cancelStartSound, wineffectschicken, wineffectsvulcanfire, wineffectvulcanwool, wineffectnotes, killEffectTNT, killEffectSquid;
    private int coinsKill, coinsWin, coinsAssists, xpKill, xpWin, xpAssists, starting, progressBarAmount;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        this.coinsKill = plugin.getConfig().getInt("gameDefaults.coins.kill");
        this.coinsWin = plugin.getConfig().getInt("gameDefaults.coins.win");
        this.coinsAssists = plugin.getConfig().getInt("gameDefaults.coins.assists");
        this.xpKill = plugin.getConfig().getInt("gameDefaults.xp.kill");
        this.xpWin = plugin.getConfig().getInt("gameDefaults.xp.win");
        this.xpAssists = plugin.getConfig().getInt("gameDefaults.xp.assists");
        this.upgradeSound = Sound.valueOf(plugin.getConfig().getString("sounds.upgrade"));
        this.starting = plugin.getConfig().getInt("gameDefaults.starting");
        this.progressBarAmount = plugin.getConfig().getInt("progressBarAmount");
        this.placeholdersAPI = plugin.getConfig().getBoolean("addons.placeholdersAPI");
        this.mainLobby = Utils.getStringLocation(plugin.getConfig().getString("mainLobby"));
        this.cancelStartSound = Sound.valueOf(plugin.getConfig().getString("sounds.cancelStart"));
        this.wineffectschicken = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.chicken"));
        this.wineffectnotes = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.notes"));
        this.wineffectsvulcanfire = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.vulcanfire"));
        this.wineffectvulcanwool = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.vulcanwool"));
        this.redPanelData = (short) plugin.getConfig().getInt("redPanel.data");
        this.redPanelMaterial = Material.valueOf(plugin.getConfig().getString("redPanel.material"));
        this.redPanelInLocked = plugin.getConfig().getBoolean("redPanelInLocked");
        this.killEffectTNT = Sound.valueOf(plugin.getConfig().getString("sounds.killeffects.tnt"));
        this.killEffectSquid = Sound.valueOf(plugin.getConfig().getString("sounds.killeffects.squid"));
        this.back = Material.valueOf(plugin.getConfig().getString("materials.closeitem"));
    }
}
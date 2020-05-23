package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class ConfigManager {

    private Main plugin;
    private boolean placeholdersAPI;
    private Location mainLobby;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        this.placeholdersAPI = plugin.getConfig().getBoolean("addons.placeholdersAPI");
        this.mainLobby = Utils.getStringLocation(plugin.getConfig().getString("mainLobby"));
    }
}
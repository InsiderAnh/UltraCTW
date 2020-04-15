package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;

public class ConfigManager {

    private Main plugin;
    private boolean placeholdersAPI;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        this.placeholdersAPI = plugin.getConfig().getBoolean("addons.placeholdersAPI");
    }

    public boolean isPlaceholdersAPI() {
        return placeholdersAPI;
    }
}
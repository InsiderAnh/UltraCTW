package io.github.Leonardo0013YT.UltraSkyWars.addons;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderAPIAddon {

    public String parsePlaceholders(Player p, String value) {
        return PlaceholderAPI.setPlaceholders(p, value);
    }

}
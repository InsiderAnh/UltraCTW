package io.github.Leonardo0013YT.UltraCTW.flag;

import io.github.Leonardo0013YT.UltraCTW.Main;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public class Mine {

    private Material material;
    private int regenerate;
    private String key;

    public Mine(Main plugin, String path, String key) {
        this.key = key;
        this.material = Material.valueOf(plugin.getMines().get(path + ".material"));
        this.regenerate = plugin.getMines().getInt(path + ".regenerate");
    }

}
package io.github.Leonardo0013YT.UltraCTW.objects;

import io.github.Leonardo0013YT.UltraCTW.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Level {

    @Getter
    private int id, xp, level, levelUp;
    @Getter
    private String prefix;
    private List<String> rewards;

    public Level(Main plugin, String path, int id) {
        this.id = id;
        this.level = plugin.getLevels().getInt(path + ".level");
        this.xp = plugin.getLevels().getInt(path + ".xp");
        this.levelUp = plugin.getLevels().getInt(path + ".levelUp");
        this.prefix = plugin.getLevels().get(null, path + ".prefix");
        this.rewards = plugin.getLevels().getList(path + ".rewards");
    }

    public void execute(Player p) {
        for (String r : rewards) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), r.replaceAll("<player>", p.getName()));
        }
    }

}
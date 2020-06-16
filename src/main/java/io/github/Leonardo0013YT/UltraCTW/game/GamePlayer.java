package io.github.Leonardo0013YT.UltraCTW.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class GamePlayer {

    private Player p;
    private int kills = 0, deaths = 0;
    private double coins = 0;
    private ItemStack[] inv, armor;
    private boolean reset;

    public GamePlayer(Player p) {
        this.p = p;
        this.inv = p.getInventory().getContents();
        this.armor = p.getInventory().getArmorContents();
        this.reset = false;
    }

    public void reset() {
        if (reset) {
            return;
        }
        p.getInventory().setContents(inv);
        p.getInventory().setArmorContents(armor);
        reset = true;
    }

    public void addCoins(double amount) {
        coins += amount;
    }

}
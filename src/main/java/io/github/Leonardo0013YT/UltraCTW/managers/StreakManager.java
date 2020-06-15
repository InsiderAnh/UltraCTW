package io.github.Leonardo0013YT.UltraCTW.managers;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.streak.Streak;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class StreakManager {

    private Main plugin;
    private HashMap<Player, Streak> streaks = new HashMap<>();

    public StreakManager(Main plugin) {
        this.plugin = plugin;
    }

    public void resetStreak(Player p){
        streaks.put(p, new Streak(0, 0, false, 0, System.currentTimeMillis()));
        NametagEdit.getApi().setSuffix(p, "");
        Utils.updateSB(p);
    }

    public void addKill(Player p, Game game){
        Streak streak = get(p);
        streak.setStreak(streak.getStreak() + 1);
        if (streak.getLastKill() > System.currentTimeMillis() - (plugin.getCm().getTimeToKill() * 1000)){
            streak.setKills(streak.getKills() + 1);
            streak.setLastKill(System.currentTimeMillis());
        } else {
            streak.setKills(0);
        }
        int amount = getStreak(p);
        if (amount % 5 == 0){
            game.sendGameMessage(plugin.getLang().get("messages.streak").replaceAll("<kills>", String.valueOf(amount)).replaceAll("<nameStreak>", p.getName()));
            for (Entity ent : p.getNearbyEntities(5, 5, 5)){
                if (!(ent instanceof Player)) continue;
                Player ar = (Player) ent;
                ar.playSound(ar.getLocation(), XSound.ENTITY_ENDER_DRAGON_GROWL.parseSound(), 1.0f, 1.0f);
            }
        }
        if (amount == 10){
            streak.setBounty(true);
            streak.setPrice(getBounty(amount));
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            ctw.setBounty(streak.getPrice());
            game.sendGameMessage(plugin.getLang().get("messages.bounty").replaceAll("<name>", p.getName()).replaceAll("<coins>", Utils.format(streak.getPrice())));
            NametagEdit.getApi().setSuffix(p, " §6§l" + Utils.format(streak.getPrice()) + "g");
            return;
        }
        if (amount >= 10){
            streak.setPrice(getBounty(amount));
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            ctw.setBounty(streak.getPrice());
            if (amount % 5 == 0){
                game.sendGameMessage(plugin.getLang().get("messages.bounty").replaceAll("<name>", p.getName()).replaceAll("<coins>", Utils.format(streak.getPrice())));
            }
            NametagEdit.getApi().setSuffix(p, " §6§l" + Utils.format(streak.getPrice()) + "g");
        }
    }

    public int getStreak(Player p){
        return get(p).getStreak();
    }

    public int getKills(Player p){
        return get(p).getKills();
    }

    public double getBounty(int streak){
        double random = plugin.getCm().getBountyMin() + (plugin.getCm().getBountyMax() - plugin.getCm().getBountyMin()) * ThreadLocalRandom.current().nextDouble();
        double perStreak = (random * plugin.getCm().getBountyPerKill()) * streak;
        return random + perStreak;
    }

    public String getPrefix(Player p){
        int amount = getKills(p);
        if (amount == 2){
            p.playSound(p.getLocation(), plugin.getCm().getStreak2(), 1.0f, 1.0f);
            return plugin.getLang().get("streaks.double");
        }
        if (amount == 3){
            p.playSound(p.getLocation(), plugin.getCm().getStreak3(), 1.0f, 1.0f);
            return plugin.getLang().get("streaks.three");
        }
        if (amount == 4){
            p.playSound(p.getLocation(), plugin.getCm().getStreak4(), 1.0f, 1.0f);
            return plugin.getLang().get("streaks.quarter");
        }
        if (amount == 5){
            p.playSound(p.getLocation(), plugin.getCm().getStreak5(), 1.0f, 1.0f);
            return plugin.getLang().get("streaks.penta");
        }
        return "§a§l";
    }

    public Streak get(Player p){
        streaks.putIfAbsent(p, new Streak(0, 0, false, 0, System.currentTimeMillis()));
        return streaks.get(p);
    }

}
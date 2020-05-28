package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerListener implements Listener {

    private Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        plugin.getDb().loadPlayer(p);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) return;
        Squared s1 = g.getPlayerSquared(e.getBlock().getLocation());
        if (s1 != null){
            e.setCancelled(s1.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noBreak"));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) return;
        Squared s1 = g.getPlayerSquared(e.getBlockPlaced().getLocation());
        if (s1 != null){
            e.setCancelled(s1.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noPlace"));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) return;
        Squared s2 = team.getPlayerSquared(e.getTo());
        if (s2 != null){
            e.setCancelled(s2.isNoEntry());
            p.teleport(e.getFrom());
            p.setVelocity(p.getVelocity().multiply(-1));
            p.sendMessage(plugin.getLang().get("messages.noEntry"));
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g == null) return;
            e.setCancelled(true);
            e.setFoodLevel(40);
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e){
        Item i = e.getEntity();
        if (i.hasMetadata("DROPPED")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        Item i = e.getItem();
        if (i.hasMetadata("DROPPED")) {
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g == null) return;
            Team team = g.getTeamPlayer(p);
            if (team == null) return;
            ChatColor c = ChatColor.valueOf(i.getMetadata("DROPPED").get(0).asString());
            Team other = g.getTeamByColor(c);
            other.getDropped().remove(c);
            team.getInProgress().get(c).add(p.getUniqueId());
            team.sendTitle(plugin.getLang().get("titles.teampick.title").replaceAll("<color>", c + ""), plugin.getLang().get("titles.teampick.subtitle").replaceAll("<wool>", c + plugin.getLang().get("scoreboards.wools.captured")), 0, 30, 10);
            other.sendTitle(plugin.getLang().get("titles.otherpick.title").replaceAll("<color>", c + ""), plugin.getLang().get("titles.otherpick.subtitle").replaceAll("<wool>", c + plugin.getLang().get("scoreboards.wools.captured")), 0, 30, 10);
            team.playSound(XSound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.0f);
            other.playSound(XSound.ENTITY_WITHER_HURT, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g == null) return;
            Team team = g.getTeamPlayer(p);
            if (team == null) return;
            if (e.getFinalDamage() >= p.getHealth()){
                e.setCancelled(true);
                p.setNoDamageTicks(40);
                p.teleport(team.getSpawn());
                p.setHealth(p.getMaxHealth());
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g == null) return;
            Team team = g.getTeamPlayer(p);
            if (team == null) return;
            if (e.getFinalDamage() >= p.getHealth()){
                e.setCancelled(true);
                p.setNoDamageTicks(40);
                p.teleport(team.getSpawn());
                p.setHealth(p.getMaxHealth());
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
            return;
        }
        ItemStack item = p.getItemInHand();
        if (item.equals(plugin.getIm().getTeams())){
            Game game = plugin.getGm().getGameByPlayer(p);
            plugin.getGem().createTeamsMenu(p, game);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        plugin.getDb().savePlayer(p.getUniqueId(), false);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        Player p = e.getPlayer();
        plugin.getDb().savePlayer(p.getUniqueId(), false);
    }

}
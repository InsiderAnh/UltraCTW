package io.github.Leonardo0013YT.UltraCTW.listeners;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
import java.util.stream.Collectors;

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
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        plugin.getDb().savePlayer(p.getUniqueId(), false);
        plugin.getGm().removePlayerGame(p, true);
        NametagEdit.getApi().clearNametag(p);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        Player p = e.getPlayer();
        plugin.getDb().savePlayer(p.getUniqueId(), false);
        plugin.getGm().removePlayerGame(p, true);
        NametagEdit.getApi().clearNametag(p);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        if (g.getInLobby().contains(p)){
            String msg = formatLobby(p, e.getMessage());
            g.getInLobby().forEach(o -> o.sendMessage(msg));
        } else {
            Team t = g.getTeamPlayer(p);
            if (e.getMessage().startsWith("!")){
                String msg = formatGame(p, t, e.getMessage());
                g.getCached().forEach(o -> o.sendMessage(msg));
            } else {
                String msg = formatTeam(p, t, e.getMessage());
                t.getMembers().forEach(o -> o.sendMessage(msg));
            }
        }
        e.setCancelled(true);
    }

    private String formatLobby(Player p, String msg){
        return plugin.getLang().get("chat.lobby").replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatTeam(Player p, Team team, String msg){
        return plugin.getLang().get("chat.team").replaceAll("<team>", team.getName()).replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatGame(Player p, Team team, String msg){
        return plugin.getLang().get("chat.global").replaceAll("<team>", team.getName()).replaceAll("<player>", p.getName()).replaceAll("<msg>", msg.replaceFirst("!", ""));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) {
            e.setCancelled(true);
            return;
        }
        Squared s1 = g.getPlayerSquared(e.getBlock().getLocation());
        Squared s2 = team.getPlayerSquared(e.getBlock().getLocation());
        Block b = e.getBlock();
        Location l = b.getLocation();
        if (g.getWools().containsKey(l)){
            e.setCancelled(true);
            b.setType(Material.AIR);
            l.getWorld().dropItemNaturally(l, g.getWools().get(l));
            g.getWools().remove(l);
            return;
        }
        if (s1 != null){
            e.setCancelled(s1.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noBreak"));
        }
        if (s2 != null){
            e.setCancelled(s2.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noBreak"));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) {
            e.setCancelled(true);
            return;
        }
        Location l = e.getBlockPlaced().getLocation();
        ItemStack item = p.getItemInHand();
        if (team.getWools().containsKey(l)){
            if (item == null || item.getType().equals(Material.AIR)) return;
            String co = NBTEditor.getString(item, "TEAM", "WOOL", "CAPTURE");
            if (co == null) {
                e.setCancelled(true);
                return;
            }
            ChatColor c = ChatColor.valueOf(co);
            ChatColor to = team.getWools().get(l);
            if (!to.equals(c)){
                e.setCancelled(true);
                p.sendMessage(plugin.getLang().get("messages.incorrectPlace").replaceAll("<wool>", c + plugin.getLang().get("scoreboards.wools.captured")));
                return;
            }
            p.sendMessage(plugin.getLang().get("messages.placeTeam").replaceAll("<place>", c + plugin.getLang().get("scoreboards.wools.captured")));
            team.getCaptured().add(c);
            team.sendTitle(plugin.getLang().get("titles.captured.title").replaceAll("<color>", c + ""), plugin.getLang().get("titles.captured.subtitle").replaceAll("<wool>", c + plugin.getLang().get("scoreboards.wools.captured")), 0, 30, 10);
            team.playSound(XSound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            if (team.checkWools()){
                g.win(team);
            }
            return;
        } else {
            if (item != null) {
                String co = NBTEditor.getString(item, "TEAM", "WOOL", "CAPTURE");
                if (co != null) {
                    ItemStack i = item.clone();
                    i.setAmount(1);
                    g.getWools().put(e.getBlockPlaced().getLocation(), i);
                }
            }
        }
        Squared s1 = g.getPlayerSquared(l);
        Squared s2 = team.getPlayerSquared(l);
        if (s1 != null){
            e.setCancelled(s1.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noPlace"));
        }
        if (s2 != null){
            e.setCancelled(s2.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noPlace"));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) {
            if (g.getLobbyProtection() != null){
                Squared s = g.getLobbyProtection();
                if (!s.isInCuboid(p)){
                    e.setCancelled(true);
                    p.teleport(g.getLobby());
                }
            }
            return;
        }
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
            ArrayList<Team> others = g.getTeams().values().stream().filter(t -> t.getId() != team.getId()).collect(Collectors.toCollection(ArrayList::new));
            team.getDropped().remove(c);
            if (!team.getInProgress().get(c).contains(p.getUniqueId())){
                team.getInProgress().get(c).add(p.getUniqueId());
                team.sendTitle(plugin.getLang().get("titles.teampick.title").replaceAll("<color>", c + ""), plugin.getLang().get("titles.teampick.subtitle").replaceAll("<wool>", c + plugin.getLang().get("scoreboards.wools.captured")), 0, 30, 10);
                others.forEach(t -> t.sendTitle(plugin.getLang().get("titles.otherpick.title").replaceAll("<color>", c + ""), plugin.getLang().get("titles.otherpick.subtitle").replaceAll("<wool>", c + plugin.getLang().get("scoreboards.wools.captured")), 0, 30, 10));
                team.playSound(XSound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.0f);
                others.forEach(t -> t.playSound(XSound.ENTITY_WITHER_HURT, 1.0f, 1.0f));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g == null) return;
            Team team = g.getTeamPlayer(p);
            if (team == null) {
                e.setCancelled(true);
                return;
            }
            if (e.getFinalDamage() >= p.getHealth()){
                e.setCancelled(true);
                respawn(team, g, p);
                if (plugin.getTgm().hasTag(p)){
                    Player d = plugin.getTgm().getTagged(p).getLast();
                    CTWPlayer sk = plugin.getDb().getCTWPlayer(d);
                    EntityDamageEvent.DamageCause cause = e.getCause();
                    if (sk != null){
                        plugin.getTm().execute(p, cause, g, sk.getTaunt());
                    } else {
                        plugin.getTm().execute(p, cause, g, 0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if (e.getDamager() instanceof Player){
                Player d = (Player) e.getDamager();
                Game g = plugin.getGm().getGameByPlayer(p);
                if (g == null) return;
                Team tp = g.getTeamPlayer(p);
                Team td = g.getTeamPlayer(d);
                if (tp == null || td == null) {
                    e.setCancelled(true);
                    return;
                }
                if (tp.getId() == td.getId()){
                    e.setCancelled(true);
                    return;
                }
                if (e.getFinalDamage() >= p.getHealth()){
                    e.setCancelled(true);
                    respawn(tp, g, p);
                    CTWPlayer sk = plugin.getDb().getCTWPlayer(d);
                    if (p.getLastDamageCause() == null || p.getLastDamageCause().getCause() == null) {
                        EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.CONTACT;
                        if (sk != null) {
                            plugin.getTm().execute(p, cause, g, sk.getTaunt());
                        } else {
                            plugin.getTm().execute(p, cause, g, 0);
                        }
                    } else {
                        EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();
                        if (sk != null) {
                            plugin.getTm().execute(p, cause, g, sk.getTaunt());
                        } else {
                            plugin.getTm().execute(p, cause, g, 0);
                        }
                    }
                }
                double damage = e.getFinalDamage();
                plugin.getTgm().setTag(d, p, damage, g);
            }
            if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player){
                Player d = (Player) ((Projectile) e.getDamager()).getShooter();
                Game g = plugin.getGm().getGameByPlayer(p);
                if (g == null) return;
                Team tp = g.getTeamPlayer(p);
                Team td = g.getTeamPlayer(d);
                if (tp == null || td == null) {
                    e.setCancelled(true);
                    return;
                }
                if (tp.getId() == td.getId()){
                    e.setCancelled(true);
                    return;
                }
                if (e.getFinalDamage() >= p.getHealth()){
                    e.setCancelled(true);
                    CTWPlayer sk = plugin.getDb().getCTWPlayer(d);
                    respawn(tp, g, p);
                    if (p.getLastDamageCause() == null || p.getLastDamageCause().getCause() == null) {
                        EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.CONTACT;
                        if (sk != null) {
                            plugin.getTm().execute(p, cause, g, sk.getTaunt());
                        } else {
                            plugin.getTm().execute(p, cause, g, 0);
                        }
                    } else {
                        EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();
                        if (sk != null) {
                            plugin.getTm().execute(p, cause, g, sk.getTaunt());
                        } else {
                            plugin.getTm().execute(p, cause, g, 0);
                        }
                    }
                }
                double damage = e.getFinalDamage();
                plugin.getTgm().setTag(d, p, damage, g);
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



    private void respawn(Team team, Game g, Player p){
        for (ChatColor c : team.getColors()){
            if (team.getInProgress().get(c).isEmpty()) continue;
            team.getInProgress().get(c).remove(p.getUniqueId());
        }
        p.getInventory().clear();
        p.setNoDamageTicks(40);
        p.teleport(team.getSpawn());
        p.setHealth(p.getMaxHealth());
        plugin.getKm().giveDefaultKit(p, g, team);
        p.updateInventory();
        //Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getKm().giveDefaultKit(p, g, team), 2L);
    }

}
package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.flag.Mine;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.MineCountdown;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FlagListener implements Listener {

    private Main plugin;

    public FlagListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        Block b = e.getBlock();
        Material material = b.getType();
        Location loc = b.getLocation();
        if (g.getMines().containsKey(loc)) {
            Mine mine = plugin.getFm().getMine(material);
            if (g.getCountdowns().containsKey(loc)) {
                e.setCancelled(true);
            } else {
                g.getCountdowns().put(loc, new MineCountdown(mine.getKey(), loc, mine.getRegenerate()));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.setType(Material.COBBLESTONE);
                    }
                }.runTaskLater(plugin, 1L);
            }
        }
        FlagTeam ft = g.getTeamByLoc(loc);
        FlagTeam yt = g.getTeamPlayer(p);
        if (ft == null) return;
        if (ft.equals(yt)) {
            e.setCancelled(true);
            p.sendMessage(plugin.getLang().get("messages.noBreakFlag"));
            return;
        }
        e.setCancelled(true);
        b.setType(Material.AIR);
        yt.setCapturing(p, ft.getColor());
        p.getInventory().setHelmet(ft.getFlagItem());
        ft.sendMessage(plugin.getLang().get("messages.breakFlag").replace("<color>", ft.getColor() + "").replace("<player>", p.getName()));
        yt.sendMessage(plugin.getLang().get("messages.stealFlag").replace("<team>", ft.getName()).replace("<color>", ft.getColor() + "").replace("<player>", p.getName()));
        ft.playSound(XSound.ENTITY_WITHER_HURT, 1.0f, 1.0f);
        yt.playSound(XSound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.0f);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        FlagTeam team = g.getTeamPlayer(p);
        if (team == null) return;
        e.setDeathMessage(null);
        team.removeLife();
        p.spigot().respawn();
        g.checkWin();
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        if (plugin.getTgm().hasTag(p)) {
            Player k = plugin.getTgm().getTagged(p).getLast();
            if (k != null) {
                CTWPlayer sk = plugin.getDb().getCTWPlayer(k);
                GamePlayer gp = g.getGamePlayer().get(k);
                if (gp != null) {
                    gp.addCoins(plugin.getCm().getCoinsKill());
                    gp.addKill();
                }
                if (sk != null) {
                    plugin.getKem().execute(g, k, p, p.getLocation(), sk.getKillEffect());
                    plugin.getKsm().execute(k, p, sk.getKillSound());
                    sk.setKills(sk.getKills() + 1);
                }
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
                plugin.getTgm().executeRewards(p, p.getMaxHealth());
                return;
            }
        }
        if (sw != null) {
            plugin.getTm().execute(p, g, sw.getTaunt());
        }
        if (!g.isState(State.FINISH)){
            new BukkitRunnable(){
                @Override
                public void run() {
                    p.teleport(team.getSpawn());
                    GamePlayer gp = g.getGamePlayer(p);
                    gp.addDeath();
                }
            }.runTaskLater(plugin, 3L);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        FlagTeam team = g.getTeamPlayer(p);
        if (team == null) return;
        if (!team.getFlag().getWorld().getName().equals(p.getLocation().getWorld().getName())) return;
        if (team.getFlag().distance(p.getLocation()) < 2) {
            if (team.isCapturing(p)) {
                p.getInventory().setHelmet(null);
                ChatColor color = team.getCapturing(p);
                team.removeCapturing(p);
                FlagTeam ft = g.getTeamByColor(color);
                ft.removeLife();
                Block b = ft.getFlag().getBlock();
                b.setType(XMaterial.WHITE_BANNER.parseMaterial());
                Banner banner = (Banner) b.getState();
                banner.setBaseColor(Utils.getDyeColorByChatColor(ft.getColor()));
                banner.update(true, true);
                team.playSound(XSound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                team.sendTitle(plugin.getLang().get("titles.capturedFlag.title").replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<color>", team.getColor() + ""), plugin.getLang().get("titles.capturedFlag.subtitle").replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<color>", team.getColor() + ""), 0, 30, 0);
                ft.sendTitle(plugin.getLang().get("titles.otherCapturedFlag.title").replace("<player>", p.getName()).replace("<name>", team.getName()).replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<color>", team.getColor() + ""), plugin.getLang().get("titles.otherCapturedFlag.subtitle").replace("<player>", p.getName()).replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<name>", team.getName()).replace("<color>", team.getColor() + ""), 0, 30, 0);
                ft.playSound(XSound.ENTITY_WITHER_HURT, 1.0f, 1.0f);
                g.checkWin();
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR) && e.getSlot() == 39) {
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType().name().endsWith("BANNER")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        if (g.isGracePeriod()) {
            Block b = e.getBlock();
            boolean isVoid = isVoidBlock(b.getLocation().clone());
            e.setCancelled(isVoid);
            if (isVoid) {
                p.sendMessage(plugin.getLang().get("messages.noPlaceInGrace"));
            }
        }
    }

    public boolean isVoidBlock(Location loc) {
        int airBlocks = 0;
        for (int i = 1; i < 30; i++) {
            if (!loc.clone().subtract(0, i, 0).getBlock().getType().equals(Material.AIR)) {
                break;
            }
            airBlocks++;
        }
        return airBlocks > 27;
    }

}
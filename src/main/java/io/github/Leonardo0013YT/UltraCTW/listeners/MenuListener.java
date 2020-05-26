package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.game.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    private Main plugin;

    public MenuListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e){
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE) || e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)){
            return;
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teams.title"))){
            ItemStack item = e.getCurrentItem();
            Player p = (Player) e.getWhoClicked();
            if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()){
                return;
            }
            Game game = plugin.getGm().getGameByPlayer(p);
            String d = item.getItemMeta().getDisplayName();
            if (d.equals(plugin.getLang().get("menus.teams.random.nameItem"))){
                game.addPlayerRandomTeam(p);
                return;
            }
            String co = NBTEditor.getString(item, "SELECTOR", "TEAM", "COLOR");
            if (co == null) return;
            ChatColor c = ChatColor.valueOf(co);
            Team team = game.getTeams().get(c);
            game.addPlayerTeam(p, team);
            p.sendMessage(plugin.getLang().get("messages.joinTeam").replaceAll("<team>", team.getName()));
        }
    }

}
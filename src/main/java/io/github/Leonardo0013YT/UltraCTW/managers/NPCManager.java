package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NPCManager {

    private HashMap<Player, ArrayList<NPC>> npcs = new HashMap<>();
    private Main plugin;

    public NPCManager(Main plugin) {
        this.plugin = plugin;
        startUpdate();
    }

    public void startUpdate() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player on : npcs.keySet()) {
                for (NPC npc : npcs.get(on)) {
                    if (npc.toHide(on.getLocation())) {
                        if (npc.isShowing()) {
                            npc.destroy();
                        }
                    } else {
                        if (!npc.isShowing()) {
                            npc.spawn();
                        }
                    }
                }
            }
        }, 100, 100);
    }

    public NPC getNPC(Player p, UUID uuid) {
        if (npcs.containsKey(p)) {
            for (NPC npc : npcs.get(p)) {
                if (npc.getBukkitEntity() == null) continue;
                if (npc.getBukkitEntity().getUniqueId().equals(uuid)) {
                    return npc;
                }
            }
        }
        return null;
    }

    public void addNPC(Player p, NPC npc) {
        npcs.putIfAbsent(p, new ArrayList<>());
        npcs.get(p).add(npc);
    }

    public void removePlayer(Player p) {
        if (npcs.containsKey(p)) {
            for (NPC npc : npcs.get(p)) {
                npc.destroy();
            }
        }
    }

}
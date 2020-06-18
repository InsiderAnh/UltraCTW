package io.github.Leonardo0013YT.UltraCTW.objects;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.api.events.CTWNPCInteractEvent;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ProtocolLib {

    private static ProtocolManager protocolManager;
    private Main plugin;

    public ProtocolLib(Main plugin) {
        this.plugin = plugin;
        protocolManager = ProtocolLibrary.getProtocolManager();
        register();
    }

    public void register(){
        protocolManager.addPacketListener(new PacketAdapter(Main.get(), ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event){
                if(event.getPacketType() == PacketType.Play.Client.USE_ENTITY){
                    Player p = event.getPlayer();
                    if (!Main.get().getNpc().getNpcs().containsKey(p)) return;
                    try {
                        PacketContainer packet = event.getPacket();
                        int id = packet.getIntegers().read(0);
                        for (NPC npc : Main.get().getNpc().getNpcs().get(p)){
                            if (npc.getEntityID() == id){
                                CTWNPCInteractEvent interactevent = new CTWNPCInteractEvent(p, npc);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.get(), () -> Bukkit.getPluginManager().callEvent(interactevent));
                                break;
                            }
                        }
                    } catch (Exception ignored){}
                }
            }
        });
    }

}
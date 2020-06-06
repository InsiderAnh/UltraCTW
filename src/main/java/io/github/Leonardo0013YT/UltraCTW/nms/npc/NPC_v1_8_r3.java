package io.github.Leonardo0013YT.UltraCTW.nms.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.KeeperData;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC_v1_8_r3 implements NPC {

    private Entity entity;

    @Override
    public void spawn(Player p, Location loc, EntityType type, KeeperData kd) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        if (type.equals(EntityType.PLAYER)) {
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
            gameProfile.getProperties().put("textures", new Property("textures", kd.getValue(), kd.getSignature()));
            EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
            npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() * 256.0F / 360.0F, loc.getPitch() * 256.0F / 360.0F);
            npc.setCustomNameVisible(false);
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            new PlayerConnection(nmsServer, new NetworkManager(EnumProtocolDirection.SERVERBOUND), npc);
            ((CraftWorld) loc.getWorld()).getHandle().addEntity(npc);
            entity = npc;
        } else if (type.equals(EntityType.VILLAGER)) {
            EntityVillager ev = new EntityVillager(nmsWorld);
            ev.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() * 256.0F / 360.0F, loc.getPitch());
            ev.setCustomNameVisible(false);
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutSpawnEntityLiving(ev));
            nmsWorld.addEntity(ev);
            entity = ev;
        } else if (type.equals(EntityType.ZOMBIE)) {
            EntityZombie ev = new EntityZombie(nmsWorld);
            ev.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw() * 256.0F / 360.0F, loc.getPitch());
            ev.setCustomNameVisible(false);
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutSpawnEntityLiving(ev));
            nmsWorld.addEntity(ev);
            entity = ev;
        }
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        return entity.getBukkitEntity();
    }
}
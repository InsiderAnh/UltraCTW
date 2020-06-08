package io.github.Leonardo0013YT.UltraCTW.nms.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.KeeperData;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC_v1_15_r1 implements NPC {

    private Entity entity;
    private Player p;

    @Override
    public void spawn(Player p, Location loc, EntityType type, KeeperData kd) {
        this.p = p;
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        if (type.equals(EntityType.PLAYER)) {
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
            changeSkin(gameProfile, kd.getValue(), kd.getSignature());
            EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
            npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), newDirection(loc.getYaw()), newDirection(loc.getPitch()));
            npc.setCustomNameVisible(false);
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            new PlayerConnection(nmsServer, new NetworkManager(EnumProtocolDirection.SERVERBOUND), npc);
            ((CraftWorld) loc.getWorld()).getHandle().addEntity(npc);
            entity = npc;
        } else {
            EntityLiving ev = getEntityByType(type, nmsWorld);
            ev.setLocation(loc.getX(), loc.getY(), loc.getZ(), newDirection(loc.getYaw()), newDirection(loc.getPitch()));
            ev.setCustomNameVisible(false);
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutSpawnEntityLiving(ev));
            nmsWorld.addEntity(ev);
            entity = ev;
        }
    }

    @Override
    public void destroy(){
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        return entity.getBukkitEntity();
    }

    private EntityLiving getEntityByType(EntityType type, WorldServer nmsWorld){
        if (type.equals(EntityType.ZOMBIE)){
            return new EntityZombie(nmsWorld);
        } else if (type.equals(EntityType.VILLAGER)){
            return new EntityVillager(EntityTypes.VILLAGER, nmsWorld);
        } else if (type.equals(EntityType.CHICKEN)){
            return new EntityChicken(EntityTypes.CHICKEN, nmsWorld);
        } else if (type.equals(EntityType.RABBIT)){
            return new EntityRabbit(EntityTypes.RABBIT, nmsWorld);
        } else if (type.equals(EntityType.BLAZE)){
            return new EntityBlaze(EntityTypes.BLAZE, nmsWorld);
        } else if (type.equals(EntityType.CREEPER)){
            return new EntityCreeper(EntityTypes.CREEPER, nmsWorld);
        } else if (type.equals(EntityType.CAVE_SPIDER)){
            return new EntityCaveSpider(EntityTypes.CAVE_SPIDER, nmsWorld);
        } else if (type.equals(EntityType.COW)){
            return new EntityCow(EntityTypes.COW, nmsWorld);
        }
        return new EntityZombie(nmsWorld);
    }

    private float newDirection(float loc){
        return loc * 256.0F / 360.0F;
    }

    private void changeSkin(GameProfile profile, String value, String signature) {
        profile.getProperties().put("textures", new Property("textures", value, signature));
    }
}
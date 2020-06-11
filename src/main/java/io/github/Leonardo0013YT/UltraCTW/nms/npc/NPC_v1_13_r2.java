package io.github.Leonardo0013YT.UltraCTW.nms.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.KeeperData;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class NPC_v1_13_r2 implements NPC {

    private ArrayList<EntityLiving> armors = new ArrayList<>();
    private EntityLiving entity;
    private Player p;
    private Location loc;
    private EntityType type;
    private KeeperData kd;
    private Main plugin;
    private NPCType npcType;
    private MinecraftServer nmsServer;
    private double up = 0.8;
    private PacketPlayOutScoreboardTeam packet;
    private GameProfile gameProfile;
    private WorldServer nmsWorld;
    private boolean showing;

    public NPC_v1_13_r2(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void create(Player p, Location loc, EntityType type, KeeperData kd, NPCType npcType) {
        this.p = p;
        this.loc = loc;
        this.type = type;
        this.kd = kd;
        this.nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        this.gameProfile = new GameProfile(UUID.randomUUID(), "");
        this.nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        this.packet = new PacketPlayOutScoreboardTeam();
        String name = UUID.randomUUID().toString().substring(0, 12);
        this.setField("i", 0);
        this.setField("b", new ChatComponentText(name));
        this.setField("a", name);
        this.setField("c", new ChatComponentText(name));
        this.setField("e", "never");
        this.setField("j", 1);
        this.showing = false;
        this.npcType = npcType;
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    @Override
    public boolean toHide(Location loc) {
        return this.loc.distance(loc) > 30;
    }

    @Override
    public void spawn() {
        this.showing = true;
        if (type.equals(EntityType.PLAYER)) {
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
            try {
                Field f = packet.getClass().getDeclaredField("h");
                f.setAccessible(true);
                ((Collection) f.get(packet)).add(gameProfile.getName());
                connection.sendPacket(packet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        spawnHologram();
    }

    @Override
    public void spawnHologram() {
        armors.clear();
        Location start = loc.clone().add(0, up, 0);
        WorldServer nmsWorld = ((CraftWorld) start.getWorld()).getHandle();
        ArrayList<String> reverse = new ArrayList<>(plugin.getLang().getList("holograms." + npcType.name().toLowerCase()));
        Collections.reverse(reverse);
        for (String s : reverse) {
            EntityArmorStand eas = new EntityArmorStand(nmsWorld);
            eas.setLocation(start.getX(), start.getY(), start.getZ(), 0, 0);
            eas.setNoGravity(true);
            eas.setInvisible(true);
            eas.setBasePlate(false);
            eas.setSmall(true);
            eas.setArms(false);
            eas.setCustomNameVisible(true);
            eas.setCustomName(CraftChatMessage.fromStringOrNull(s));
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutSpawnEntityLiving(eas));
            armors.add(eas);
            start.add(0, 0.35, 0);
        }
    }

    @Override
    public Location getLoc() {
        return loc;
    }

    @Override
    public NPCType getNpcType() {
        return npcType;
    }

    @Override
    public void destroy() {
        this.showing = false;
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
        for (Entity e : armors) {
            connection.sendPacket(new PacketPlayOutEntityDestroy(e.getId()));
        }
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        return entity.getBukkitEntity();
    }

    private void setField(String field, Object value) {
        try {
            Field f = packet.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(packet, value);
            f.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private EntityLiving getEntityByType(EntityType type, WorldServer nmsWorld) {
        if (type.equals(EntityType.ZOMBIE)) {
            return new EntityZombie(nmsWorld);
        } else if (type.equals(EntityType.VILLAGER)) {
            return new EntityVillager(nmsWorld);
        } else if (type.equals(EntityType.CHICKEN)) {
            return new EntityChicken(nmsWorld);
        } else if (type.equals(EntityType.RABBIT)) {
            return new EntityRabbit(nmsWorld);
        } else if (type.equals(EntityType.BLAZE)) {
            return new EntityBlaze(nmsWorld);
        } else if (type.equals(EntityType.CREEPER)) {
            return new EntityCreeper(nmsWorld);
        } else if (type.equals(EntityType.CAVE_SPIDER)) {
            return new EntityCaveSpider(nmsWorld);
        } else if (type.equals(EntityType.COW)) {
            return new EntityCow(nmsWorld);
        }
        return new EntityZombie(nmsWorld);
    }

    private float newDirection(float loc) {
        return loc * 256.0F / 360.0F;
    }

    private void changeSkin(GameProfile profile, String value, String signature) {
        profile.getProperties().put("textures", new Property("textures", value, signature));
    }
}
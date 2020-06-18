package io.github.Leonardo0013YT.UltraCTW.nms.npc;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Collections;

public class NPC_v1_14_r1 implements NPC {

    private ArrayList<EntityLiving> armors = new ArrayList<>();
    private EntityLiving entity;
    private Player p;
    private Location loc;
    private EntityType type;
    private Main plugin;
    private NPCType npcType;
    private double up = 0.8;
    private WorldServer nmsWorld;
    private boolean showing;

    public NPC_v1_14_r1(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void create(Player p, Location loc, EntityType type, NPCType npcType) {
        this.p = p;
        this.loc = loc;
        this.type = type;
        this.nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        this.showing = false;
        this.npcType = npcType;
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    @Override
    public boolean toHide(Location loc) {
        if (!this.loc.getWorld().getName().equals(loc.getWorld().getName())) return true;
        return this.loc.distance(loc) > 30;
    }

    @Override
    public void spawn() {
        this.showing = true;
        EntityLiving ev = getEntityByType(type, nmsWorld);
        ev.setLocation(loc.getX(), loc.getY(), loc.getZ(), newDirection(loc.getYaw()), newDirection(loc.getPitch()));
        ev.setCustomNameVisible(false);
        NBTTagCompound compound = new NBTTagCompound();
        ev.c(compound);
        compound.setByte("NoAI", (byte) 1);
        ev.f(compound);
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutSpawnEntityLiving(ev));
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(ev, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entity = ev;
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
            EntityArmorStand eas = new EntityArmorStand(EntityTypes.ARMOR_STAND, nmsWorld);
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
        if (connection == null) return;
        if (entity != null) {
            connection.sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
        }
        for (Entity e : armors) {
            if (e == null) continue;
            connection.sendPacket(new PacketPlayOutEntityDestroy(e.getId()));
        }
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (entity == null) return null;
        return entity.getBukkitEntity();
    }

    @Override
    public int getEntityID(){
        if (entity == null) return -5000;
        return entity.getId();
    }

    private EntityLiving getEntityByType(EntityType type, WorldServer nmsWorld) {
        if (type.equals(EntityType.ZOMBIE)) {
            return new EntityZombie(nmsWorld);
        } else if (type.equals(EntityType.VILLAGER)) {
            return new EntityVillager(EntityTypes.VILLAGER, nmsWorld);
        } else if (type.equals(EntityType.CHICKEN)) {
            return new EntityChicken(EntityTypes.CHICKEN, nmsWorld);
        } else if (type.equals(EntityType.RABBIT)) {
            return new EntityRabbit(EntityTypes.RABBIT, nmsWorld);
        } else if (type.equals(EntityType.BLAZE)) {
            return new EntityBlaze(EntityTypes.BLAZE, nmsWorld);
        } else if (type.equals(EntityType.CREEPER)) {
            return new EntityCreeper(EntityTypes.CREEPER, nmsWorld);
        } else if (type.equals(EntityType.CAVE_SPIDER)) {
            return new EntityCaveSpider(EntityTypes.CAVE_SPIDER, nmsWorld);
        } else if (type.equals(EntityType.COW)) {
            return new EntityCow(EntityTypes.COW, nmsWorld);
        }
        return new EntityZombie(nmsWorld);
    }

    private float newDirection(float loc) {
        return loc * 256.0F / 360.0F;
    }

}
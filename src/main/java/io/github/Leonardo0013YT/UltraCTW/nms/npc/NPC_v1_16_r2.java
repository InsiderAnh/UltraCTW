package io.github.Leonardo0013YT.UltraCTW.nms.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Collections;

public class NPC_v1_16_r2 implements NPC {

    private ArrayList<EntityLiving> armors = new ArrayList<>();
    private EntityLiving entity;
    private Player p;
    private Location loc;
    private EntityType type;
    private UltraCTW plugin;
    private NPCType npcType;
    private double up = 0.8;
    private WorldServer nmsWorld;
    private boolean showing;

    public NPC_v1_16_r2(UltraCTW plugin) {
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
        if (type.equals(EntityType.IRON_GOLEM) || type.equals(EntityType.WITHER)) {
            up += 0.75;
        }
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
        ev.a_(compound);
        compound.setByte("NoAI", (byte) 1);
        ev.load(compound);
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
    public int getEntityID() {
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
        } else if (type.equals(EntityType.HORSE)) {
            return new EntityHorse(EntityTypes.HORSE, nmsWorld);
        } else if (type.equals(EntityType.IRON_GOLEM)) {
            return new EntityIronGolem(EntityTypes.IRON_GOLEM, nmsWorld);
        } else if (type.equals(EntityType.MAGMA_CUBE)) {
            return new EntityMagmaCube(EntityTypes.MAGMA_CUBE, nmsWorld);
        } else if (type.equals(EntityType.SLIME)) {
            return new EntitySlime(EntityTypes.SLIME, nmsWorld);
        } else if (type.equals(EntityType.PIG_ZOMBIE)) {
            return new EntityPigZombie(EntityTypes.ZOMBIFIED_PIGLIN, nmsWorld);
        } else if (type.equals(EntityType.SKELETON)) {
            return new EntitySkeleton(EntityTypes.SKELETON, nmsWorld);
        } else if (type.equals(EntityType.SNOWMAN)) {
            return new EntitySnowman(EntityTypes.SNOW_GOLEM, nmsWorld);
        } else if (type.equals(EntityType.WITCH)) {
            return new EntityWitch(EntityTypes.WITCH, nmsWorld);
        } else if (type.equals(EntityType.SHEEP)) {
            return new EntitySheep(EntityTypes.SHEEP, nmsWorld);
        } else if (type.equals(EntityType.WITHER)) {
            return new EntityWither(EntityTypes.WITHER, nmsWorld);
        } else if (type.equals(EntityType.PIG)) {
            return new EntityPig(EntityTypes.PIG, nmsWorld);
        } else if (type.equals(EntityType.WOLF)) {
            return new EntityWolf(EntityTypes.WOLF, nmsWorld);
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
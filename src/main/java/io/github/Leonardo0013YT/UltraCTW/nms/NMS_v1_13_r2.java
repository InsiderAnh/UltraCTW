package io.github.Leonardo0013YT.UltraCTW.nms;

import io.github.Leonardo0013YT.UltraCTW.interfaces.NMS;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class NMS_v1_13_r2 implements NMS {

    @Override
    public void sendActionBar(Player p, String msg) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(msg).create());
    }

    @Override
    public String[] getDamageCauses() {
        return new String[]{"CONTACT", "ENTITY_ATTACK", "PROJECTILE", "SUFFOCATION", "FALL", "FIRE", "FIRE_TICK",
                "MELTING", "LAVA", "DROWNING", "BLOCK_EXPLOSION", "ENTITY_EXPLOSION", "VOID", "LIGHTNING",
                "SUICIDE", "STARVATION", "POISON", "MAGIC", "WITHER", "FALLING_BLOCK", "THORNS", "CUSTOM",
                "FLY_INTO_WALL", "HOT_FLOOR", "CRAMMING", "ENTITY_SWEEP_ATTACK", "DRAGON_BREATH"};
    }

    @Override
    public void followPlayer(Player player, LivingEntity entity, double d) {
        float f = (float) d;
        ((EntityInsentient) ((CraftEntity) entity).getHandle()).getNavigation().a(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), f);
    }

    @Override
    public void displayParticle(Player p, Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount) {
        if (location.getWorld() == null) return;
        location.getWorld().spawnParticle(Particle.valueOf(enumParticle), location, amount, offsetX, offsetY, offsetZ, speed);
    }

    @Override
    public void broadcastParticle(Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount, double range) {
        if (location.getWorld() == null) return;
        location.getWorld().spawnParticle(Particle.valueOf(enumParticle), location, amount, offsetX, offsetY, offsetZ, speed);
    }

    @Override
    public boolean isParticle(String particle) {
        try {
            Particle.valueOf(particle);
        } catch (EnumConstantNotPresentException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public void freezeEntity(Entity en) {
        net.minecraft.server.v1_13_R2.Entity nmsEn = ((CraftEntity) en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        PlayerConnection pConn = ((CraftPlayer) p).getHandle().playerConnection;
        PacketPlayOutTitle pTitleInfo = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        pConn.sendPacket(pTitleInfo);
        if (subtitle != null) {
            IChatBaseComponent iComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle pSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iComp);
            pConn.sendPacket(pSubtitle);
        }
        if (title != null) {
            IChatBaseComponent iComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle pTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iComp);
            pConn.sendPacket(pTitle);
        }
    }

}
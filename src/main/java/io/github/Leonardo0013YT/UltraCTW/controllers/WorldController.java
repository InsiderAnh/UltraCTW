package io.github.Leonardo0013YT.UltraCTW.controllers;

import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldController {

    private String clear;

    public WorldController(Main plugin) {
        clear = plugin.getConfig().getString("schemaToClearLobby");
    }

    public World createEmptyWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(World.Environment.NORMAL);
        wc.generateStructures(false);
        wc.generator(getChunkGenerator());
        World w = wc.createWorld();
        w.setDifficulty(Difficulty.NORMAL);
        w.setSpawnFlags(true, true);
        w.setPVP(true);
        w.setStorm(false);
        w.setThundering(false);
        w.setWeatherDuration(Integer.MAX_VALUE);
        w.setKeepSpawnInMemory(false);
        w.setTicksPerAnimalSpawns(1);
        w.setTicksPerMonsterSpawns(1);
        w.setAutoSave(false);
        w.setGameRuleValue("mobGriefing", "true");
        w.setGameRuleValue("doFireTick", "false");
        w.setGameRuleValue("showDeathMessages", "false");
        w.setGameRuleValue("doDaylightCycle", "false");
        w.setSpawnLocation(0, 75, 0);
        return w;
    }

    public Schematic resetMap(Location spawn, String schematic) {
        Vector to = new Vector(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
        BukkitWorld w = new BukkitWorld(spawn.getWorld());
        File file = new File(Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics", schematic);
        try {
            ClipboardFormat cf = ClipboardFormat.findByFile(file);
            if (cf != null) {
                Schematic sh = cf.load(file);
                EditSession editSession = sh.paste(w, to, false, true, null);
                editSession.flushQueue();
                return sh;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Schematic clearLobby(Location lobby) {
        Vector to = new Vector(lobby.getBlockX(), lobby.getBlockY(), lobby.getBlockZ());
        BukkitWorld w = new BukkitWorld(lobby.getWorld());
        File file = new File(Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics", clear);
        try {
            ClipboardFormat cf = ClipboardFormat.findByFile(file);
            if (cf != null) {
                Schematic sh = cf.load(file);
                EditSession editSession = sh.paste(w, to, false, true, null);
                editSession.flushQueue();
                return sh;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetMap(Location spawn, Schematic sh) {
        Vector to = new Vector(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
        BukkitWorld w = new BukkitWorld(spawn.getWorld());
        EditSession editSession = sh.paste(w, to, false, true, null);
        editSession.flushQueue();
    }

    public void clearLobby(Location lobby, Schematic sh) {
        Vector to = new Vector(lobby.getBlockX(), lobby.getBlockY(), lobby.getBlockZ());
        BukkitWorld w = new BukkitWorld(lobby.getWorld());
        EditSession editSession = sh.paste(w, to, false, true, null);
        editSession.flushQueue();
    }

    public ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            @Override
            public List<BlockPopulator> getDefaultPopulators(World world) {
                return Collections.emptyList();
            }

            @Override
            public boolean canSpawn(World world, int x, int z) {
                return true;
            }

            public byte[] generate(World world, Random random, int x, int z) {
                return new byte[32768];
            }

            @Override
            public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 75, 0.0D);
            }
        };
    }

}
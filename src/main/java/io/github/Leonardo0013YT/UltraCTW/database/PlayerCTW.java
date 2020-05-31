package io.github.Leonardo0013YT.UltraCTW.database;

import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class PlayerCTW implements CTWPlayer {

    private ArrayList<Integer> partings = new ArrayList<>(), killeffects = new ArrayList<>(), wineffects = new ArrayList<>(), windances = new ArrayList<>(), killsounds = new ArrayList<>(), taunts = new ArrayList<>(), trails = new ArrayList<>();
    private double coins;
    private int kills, winDance = 999999, winEffect = 0, killEffect = 999999, taunt = 999999, trail = 999999, parting = 999999, killSound = 999999, assists = 0, kill5 = 0, kill25 = 0, kill50 = 0;

    @Override
    public void addAssists(int assists) {
        this.assists += assists;
    }

    @Override
    public void addKill5(int kill5) {
        this.kill5 += kill5;
    }

    @Override
    public void addKill25(int kill25) {
        this.kill25 = kill25;
    }

    @Override
    public void addKill50(int kill50) {
        this.kill50 += kill50;
    }

    @Override
    public void addKillEffects(int id) {
        this.killeffects.add(id);
    }

    @Override
    public void addKillSounds(int id) {
        this.killsounds.add(id);
    }

    @Override
    public void addPartings(int id) {
        this.partings.add(id);
    }

    @Override
    public void addTaunts(int id) {
        this.taunts.add(id);
    }

    @Override
    public void addTrails(int id) {
        this.trails.add(id);
    }

    @Override
    public void addWinDances(int id) {
        this.windances.add(id);
    }

    @Override
    public void addWinEffects(int id) {
        this.wineffects.add(id);
    }

}
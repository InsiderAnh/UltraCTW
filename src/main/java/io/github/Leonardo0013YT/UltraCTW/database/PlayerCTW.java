package io.github.Leonardo0013YT.UltraCTW.database;

import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;

import java.util.ArrayList;

public class PlayerCTW implements CTWPlayer {

    private ArrayList<Integer> partings = new ArrayList<>(), killeffects = new ArrayList<>(), wineffects = new ArrayList<>(), windances = new ArrayList<>(), killsounds = new ArrayList<>(), taunts = new ArrayList<>(), trails = new ArrayList<>();
    private double coins = 0.0;
    private int kills = 0, winDance = 999999, winEffect = 0, killEffect = 999999, taunt = 0, trail = 999999, parting = 999999, killSound = 999999, assists = 0, kill5 = 0, kill25 = 0, kill50 = 0;

    @Override
    public ArrayList<Integer> getPartings() {
        return partings;
    }

    @Override
    public void setPartings(ArrayList<Integer> partings) {
        this.partings = partings;
    }

    @Override
    public ArrayList<Integer> getKilleffects() {
        return killeffects;
    }

    @Override
    public void setKilleffects(ArrayList<Integer> killeffects) {
        this.killeffects = killeffects;
    }

    @Override
    public ArrayList<Integer> getWineffects() {
        return wineffects;
    }

    @Override
    public void setWineffects(ArrayList<Integer> wineffects) {
        this.wineffects = wineffects;
    }

    @Override
    public ArrayList<Integer> getWindances() {
        return windances;
    }

    @Override
    public void setWindances(ArrayList<Integer> windances) {
        this.windances = windances;
    }

    @Override
    public ArrayList<Integer> getKillsounds() {
        return killsounds;
    }

    @Override
    public void setKillsounds(ArrayList<Integer> killsounds) {
        this.killsounds = killsounds;
    }

    @Override
    public ArrayList<Integer> getTaunts() {
        return taunts;
    }

    @Override
    public void setTaunts(ArrayList<Integer> taunts) {
        this.taunts = taunts;
    }

    @Override
    public ArrayList<Integer> getTrails() {
        return trails;
    }

    @Override
    public void setTrails(ArrayList<Integer> trails) {
        this.trails = trails;
    }

    @Override
    public double getCoins() {
        return coins;
    }

    @Override
    public void setCoins(double coins) {
        this.coins = coins;
    }

    @Override
    public int getKills() {
        return kills;
    }

    @Override
    public void setKills(int kills) {
        this.kills = kills;
    }

    @Override
    public int getWinDance() {
        return winDance;
    }

    @Override
    public void setWinDance(int winDance) {
        this.winDance = winDance;
    }

    @Override
    public int getWinEffect() {
        return winEffect;
    }

    @Override
    public void setWinEffect(int winEffect) {
        this.winEffect = winEffect;
    }

    @Override
    public int getKillEffect() {
        return killEffect;
    }

    @Override
    public void setKillEffect(int killEffect) {
        this.killEffect = killEffect;
    }

    @Override
    public int getTaunt() {
        return taunt;
    }

    @Override
    public void setTaunt(int taunt) {
        this.taunt = taunt;
    }

    @Override
    public int getTrail() {
        return trail;
    }

    @Override
    public void setTrail(int trail) {
        this.trail = trail;
    }

    @Override
    public int getParting() {
        return parting;
    }

    @Override
    public void setParting(int parting) {
        this.parting = parting;
    }

    @Override
    public int getKillSound() {
        return killSound;
    }

    @Override
    public void setKillSound(int killSound) {
        this.killSound = killSound;
    }

    @Override
    public int getAssists() {
        return assists;
    }

    @Override
    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Override
    public int getKill5() {
        return kill5;
    }

    @Override
    public void setKill5(int kill5) {
        this.kill5 = kill5;
    }

    @Override
    public int getKill25() {
        return kill25;
    }

    @Override
    public void setKill25(int kill25) {
        this.kill25 = kill25;
    }

    @Override
    public int getKill50() {
        return kill50;
    }

    @Override
    public void setKill50(int kill50) {
        this.kill50 = kill50;
    }

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
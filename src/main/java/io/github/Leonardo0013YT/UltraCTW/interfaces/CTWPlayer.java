package io.github.Leonardo0013YT.UltraCTW.interfaces;

import java.util.ArrayList;

public interface CTWPlayer {

    void addCoins(double coins);

    void removeCoins(double coins);

    int getKills();

    void setKills(int kills);

    int getWinDance();

    void setWinDance(int winDance);

    int getWinEffect();

    void setWinEffect(int winEffect);

    ArrayList<Integer> getWineffects();

    void setWineffects(ArrayList<Integer> wineffects);

    ArrayList<Integer> getWindances();

    void setWindances(ArrayList<Integer> windances);

    double getCoins();

    void setCoins(double coins);

    int getKillEffect();

    void setKillEffect(int killEffect);

    int getTaunt();

    void setTaunt(int taunt);

    int getTrail();

    void setTrail(int trail);

    int getParting();

    void setParting(int parting);

    int getKillSound();

    void setKillSound(int killSound);

    ArrayList<Integer> getKillsounds();

    void setKillsounds(ArrayList<Integer> killsounds);

    ArrayList<Integer> getTaunts();

    void setTaunts(ArrayList<Integer> taunts);

    ArrayList<Integer> getTrails();

    void setTrails(ArrayList<Integer> trails);

    int getAssists();

    void setAssists(int assists);

    int getShopKeeper();

    void setShopKeeper(int shopKeeper);

    ArrayList<Integer> getPartings();

    void setPartings(ArrayList<Integer> partings);

    void addAssists(int assists);

    int getKill5();

    void setKill5(int kill5);

    void addKill5(int kill5);

    int getKill25();

    void setKill25(int kill25);

    void addKill25(int kill25);

    int getKill50();

    void setKill50(int kill50);

    void addKill50(int kill50);

    ArrayList<Integer> getKilleffects();

    void setKilleffects(ArrayList<Integer> killeffects);

    void addKillEffects(int id);

    void addKillSounds(int id);

    void addPartings(int id);

    void addTaunts(int id);

    void addTrails(int id);

    void addWinDances(int id);

    void addWinEffects(int id);
}
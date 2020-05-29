package io.github.Leonardo0013YT.UltraCTW.interfaces;

import java.util.ArrayList;

public interface CTWPlayer {

    void setKills(int kills);

    int getKills();

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
}
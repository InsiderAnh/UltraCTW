package io.github.Leonardo0013YT.UltraCTW.database;

import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class PlayerCTW implements CTWPlayer {

    private ArrayList<Integer> wineffects = new ArrayList<>(), windances = new ArrayList<>();
    private double coins;
    private int kills, winDance = 999999, winEffect = 999999;

}
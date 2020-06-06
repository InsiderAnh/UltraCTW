package io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers;

import lombok.Getter;

@Getter
public class KeeperData {

    private String signature, value;

    public KeeperData(String signature, String value) {
        this.signature = signature;
        this.value = value;
    }
}
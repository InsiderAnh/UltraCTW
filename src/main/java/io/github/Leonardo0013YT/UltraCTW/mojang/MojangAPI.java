package io.github.Leonardo0013YT.UltraCTW.mojang;

import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.KeeperData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;

public class MojangAPI {

    private static final String skinurl = "https://sessionserver.mojang.com/session/minecraft/profile/";

    public static KeeperData getSkinProperty(UUID uuid) {
        try {
            URL url = new URL(skinurl + uuid.toString().replaceAll("-", "") + "?unsigned=false");
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
            for (Object o : properties) {
                try {
                    JSONObject property = (JSONObject) o;
                    String value = (String) property.get("value");
                    String signature = property.containsKey("signature") ? (String) property.get("signature") : "none";
                    return new KeeperData(signature, value);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return new KeeperData("none", "none");
    }

}
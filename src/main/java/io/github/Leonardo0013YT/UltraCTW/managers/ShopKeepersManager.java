package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.KeeperData;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.ShopKeeper;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import io.github.Leonardo0013YT.UltraCTW.mojang.MojangAPI;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ShopKeepersManager {

    private HashMap<Integer, ShopKeeper> shopkeepers = new HashMap<>();
    private HashMap<Integer, KeeperData> datas = new HashMap<>();
    private FileConfiguration cache;
    private File fcache;
    private Main plugin;

    public ShopKeepersManager(Main plugin) {
        this.plugin = plugin;
        this.fcache = new File(plugin.getDataFolder(), "cache.yml");
        this.cache = YamlConfiguration.loadConfiguration(fcache);
        loadCacheds();
    }

    public void loadShopKeepers() {
        shopkeepers.clear();
        if (!plugin.getShopkeepers().isSet("shopkeepers")) return;
        for (String s : plugin.getShopkeepers().getConfig().getConfigurationSection("shopkeepers").getKeys(false)) {
            int id = plugin.getShopkeepers().getInt("shopkeepers." + s + ".id");
            shopkeepers.put(id, new ShopKeeper(plugin, "shopkeepers." + s));
        }
    }

    public void loadCacheds() {
        datas.clear();
        if (!cache.isSet("datas")) return;
        for (String s : cache.getConfigurationSection("datas").getKeys(false)) {
            int id = Integer.parseInt(s);
            String signature = cache.getString("datas." + s + ".signature");
            String value = cache.getString("datas." + s + ".value");
            datas.put(id, new KeeperData(signature, value));
        }
    }

    public void addKeeperData(int id, UUID uuid) {
        KeeperData kd = MojangAPI.getSkinProperty(uuid);
        cache.set("datas." + id + ".signature", kd.getSignature());
        cache.set("datas." + id + ".value", kd.getValue());
        try {
            cache.save(fcache);
        } catch (IOException ignored) {
        }
        datas.put(id, kd);
    }

    public ShopKeeper getShopKeeper(int id) {
        return shopkeepers.get(id);
    }

    public KeeperData getKeeperData(int id) {
        if (!datas.containsKey(id)) {
            addKeeperData(id, shopkeepers.get(id).getUuid());
        }
        return datas.get(id);
    }

    public NPC spawnShopKeeper(Player p, Location loc, int id) {
        ShopKeeper sk = getShopKeeper(id);
        NPC npc;
        if (sk.getType().equals("skin")) {
            KeeperData kd = getKeeperData(id);
            npc = plugin.getVc().createNewNPC();
            npc.spawn(p, loc, EntityType.PLAYER, kd);
        } else {
            EntityType type = EntityType.valueOf(sk.getEntityType());
            npc = plugin.getVc().createNewNPC();
            npc.spawn(p, loc, EntityType.PLAYER, null);
        }
        return npc;
    }

}
package io.github.Leonardo0013YT.UltraCTW.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    public static ItemStack parse(ItemStack i, String[]... t) {
        String display = (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) ? i.getItemMeta().getDisplayName() : "";
        ItemMeta im = i.getItemMeta();
        for (String[] s : t) {
            String s1 = s[0];
            String s2 = s[1];
            String s3 = s[2];
            if (display.equals(s1)) {
                im.setDisplayName(display.replace(s1, s2));
                im.setLore(s3.isEmpty() ? new ArrayList<>() : Arrays.asList(s3.split("\\n")));
                break;
            }
        }
        addItemFlags(im);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack parseVariables(Player p, ItemStack i, String[]... t) {
        String d = i.getItemMeta().getDisplayName();
        List<String> lore = (i.hasItemMeta() && i.getItemMeta().hasLore()) ? i.getItemMeta().getLore() : Collections.emptyList();
        for (String[] s : t) {
            String s1 = s[0];
            String s2 = s[1];
            for (int k = 0; k < lore.size(); k++) {
                String value = lore.get(k);
                if (value.contains(s1)) {
                    String newValue = value.replace(s1, s2);
                    lore.set(k, Main.get().getAdm().parsePlaceholders(p, newValue));
                } else {
                    lore.set(k, Main.get().getAdm().parsePlaceholders(p, value));
                }
            }
            d = d.replaceAll(s1, s2);
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(d);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack item(XMaterial material, String displayName, String s) {
        ItemStack itemStack = new ItemStack(material.parseMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(ItemStack item, String displayName, List<String> s) {
        ItemStack itemStack = item.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            itemMeta.getLore().clear();
        }
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s);
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(XMaterial material, int n, String displayName, String s) {
        ItemStack itemStack = new ItemStack(material.parseMaterial(), n, material.getData());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(XMaterial material, int n, String displayName, List<String> s) {
        ItemStack itemStack = new ItemStack(material.parseMaterial(), n, material.getData());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s);
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack skull(XMaterial material, int n, String displayName, String s, String owner) {
        ItemStack itemStack = new ItemStack(material.parseMaterial(), n, material.getData());
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(skullMeta);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public static ItemStack createSkull(String displayName, String lore, String url) {
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
        if (url.isEmpty()) return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(displayName);
        headMeta.setLore(lore.isEmpty() ? new ArrayList<>() : Arrays.asList(lore.split("\\n")));
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack createSkull(String displayName, List<String> lore, String url) {
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
        if (url.isEmpty()) return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(displayName);
        headMeta.setLore(lore);
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static void addItemFlags(ItemMeta itemMeta) {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
    }

}

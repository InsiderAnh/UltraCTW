package io.github.Leonardo0013YT.UltraCTW.utils;

import io.github.Leonardo0013YT.UltraCTW.Main;
import org.bukkit.*;

import java.io.File;
import java.text.DecimalFormat;

public class Utils {

    private static DecimalFormat df = new DecimalFormat("##.#");

    public static String parseBoolean(boolean bool) {
        return (bool) ? Main.get().getLang().get(null, "activated") : Main.get().getLang().get(null, "deactivated");
    }

    public static boolean existsFile(String schematic) {
        File file = new File(Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics", schematic);
        return file.exists();
    }

    public static String getFormatedLocation(Location loc) {
        if (loc == null) {
            return "§cNot set!";
        }
        return loc.getWorld().getName() + ", " + df.format(loc.getX()) + ", " + df.format(loc.getY()) + ", " + df.format(loc.getZ());
    }

    public static Location getStringLocation(String location) {
        if (location == null) return null;
        String[] l = location.split(";");
        if (l.length < 5) return null;
        World world = Bukkit.getWorld(l[0]);
        double x = Double.parseDouble(l[1]);
        double y = Double.parseDouble(l[2]);
        double z = Double.parseDouble(l[3]);
        float yaw = Float.parseFloat(l[4]);
        float pitch = Float.parseFloat(l[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String getLocationString(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    public static ChatColor getChatColorByString(String color) {
        if (color.contains("§b")) {
            return ChatColor.AQUA;
        }
        if (color.contains("§1")) {
            return ChatColor.DARK_BLUE;
        }
        if (color.contains("§0")) {
            return ChatColor.BLACK;
        }
        if (color.contains("§9")) {
            return ChatColor.BLUE;
        }
        if (color.contains("§3")) {
            return ChatColor.DARK_AQUA;
        }
        if (color.contains("§c")) {
            return ChatColor.RED;
        }
        if ( color.contains("§4")) {
            return ChatColor.DARK_RED;
        }
        if (color.contains("§7")) {
            return ChatColor.GRAY;
        }
        if (color.contains("§8")) {
            return ChatColor.DARK_GRAY;
        }
        if (color.contains("§2")) {
            return ChatColor.DARK_GREEN;
        }
        if (color.contains("§a")) {
            return ChatColor.GREEN;
        }
        if (color.contains("§6")) {
            return ChatColor.GOLD;
        }
        if (color.contains("§d")) {
            return ChatColor.LIGHT_PURPLE;
        }
        if (color.contains("§5")) {
            return ChatColor.DARK_PURPLE;
        }
        if (color.contains("§f")) {
            return ChatColor.WHITE;
        }
        if (color.contains("§e")) {
            return ChatColor.YELLOW;
        }
        return ChatColor.WHITE;
    }

    public static int getDataByChatColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA)) {
            return 3;
        }
        if (color.equals(ChatColor.DARK_BLUE)) {
            return 11;
        }
        if (color.equals(ChatColor.BLACK)) {
            return 15;
        }
        if (color.equals(ChatColor.BLUE)) {
            return 11;
        }
        if (color.equals(ChatColor.DARK_AQUA)) {
            return 9;
        }
        if (color.equals(ChatColor.RED)) {
            return 6;
        }
        if (color.equals(ChatColor.GOLD)) {
            return 1;
        }
        if ( color.equals(ChatColor.DARK_RED)) {
            return 14;
        }
        if (color.equals(ChatColor.DARK_GRAY)) {
            return 7;
        }
        if (color.equals(ChatColor.GRAY)) {
            return 8;
        }
        if (color.equals(ChatColor.DARK_GREEN)) {
            return 13;
        }
        if (color.equals(ChatColor.GREEN)) {
            return 5;
        }
        if (color.equals(ChatColor.LIGHT_PURPLE)) {
            return 2;
        }
        if (color.equals(ChatColor.DARK_PURPLE)) {
            return 10;
        }
        if (color.equals(ChatColor.WHITE)) {
            return 0;
        }
        if (color.equals(ChatColor.YELLOW)) {
            return 4;
        }
        return 0;
    }

    public static Color getColorByChatColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA)) {
            return Color.AQUA;
        }
        if (color.equals(ChatColor.DARK_BLUE)) {
            return Color.NAVY;
        }
        if (color.equals(ChatColor.BLACK)) {
            return Color.BLACK;
        }
        if (color.equals(ChatColor.BLUE)) {
            return Color.BLUE;
        }
        if (color.equals(ChatColor.DARK_AQUA)) {
            return Color.TEAL;
        }
        if (color.equals(ChatColor.RED) || color.equals(ChatColor.DARK_RED)) {
            return Color.RED;
        }
        if (color.equals(ChatColor.DARK_GRAY) || color.equals(ChatColor.GRAY)) {
            return Color.GRAY;
        }
        if (color.equals(ChatColor.DARK_GREEN)) {
            return Color.GREEN;
        }
        if (color.equals(ChatColor.GREEN)) {
            return Color.LIME;
        }
        if (color.equals(ChatColor.LIGHT_PURPLE)) {
            return Color.FUCHSIA;
        }
        if (color.equals(ChatColor.DARK_PURPLE)) {
            return Color.PURPLE;
        }
        if (color.equals(ChatColor.WHITE)) {
            return Color.WHITE;
        }
        if (color.equals(ChatColor.YELLOW)) {
            return Color.YELLOW;
        }
        return null;
    }

    public static XMaterial getXMaterialByColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA)) {
            return XMaterial.LIGHT_BLUE_WOOL;
        }
        if (color.equals(ChatColor.BLACK)) {
            return XMaterial.BLACK_WOOL;
        }
        if (color.equals(ChatColor.BLUE)) {
            return XMaterial.BLUE_WOOL;
        }
        if (color.equals(ChatColor.DARK_AQUA)) {
            return XMaterial.CYAN_WOOL;
        }
        if (color.equals(ChatColor.RED)){
            return XMaterial.RED_WOOL;
        }
        if (color.equals(ChatColor.GOLD)) {
            return XMaterial.ORANGE_WOOL;
        }
        if (color.equals(ChatColor.DARK_GRAY)) {
            return XMaterial.GRAY_WOOL;
        }
        if (color.equals(ChatColor.GRAY)) {
            return XMaterial.LIGHT_GRAY_WOOL;
        }
        if (color.equals(ChatColor.DARK_GREEN)) {
            return XMaterial.GREEN_WOOL;
        }
        if (color.equals(ChatColor.GREEN)) {
            return XMaterial.LIME_WOOL;
        }
        if (color.equals(ChatColor.LIGHT_PURPLE)) {
            return XMaterial.PINK_WOOL;
        }
        if (color.equals(ChatColor.DARK_PURPLE)) {
            return XMaterial.PURPLE_WOOL;
        }
        if (color.equals(ChatColor.WHITE)) {
            return XMaterial.WHITE_WOOL;
        }
        if (color.equals(ChatColor.YELLOW)) {
            return XMaterial.YELLOW_WOOL;
        }
        return XMaterial.AIR;
    }

    public static ChatColor getColorByXMaterial(XMaterial material) {
        if (material.equals(XMaterial.LIGHT_BLUE_WOOL)) {
            return ChatColor.AQUA;
        }
        if (material.equals(XMaterial.BLACK_WOOL)) {
            return ChatColor.BLACK;
        }
        if (material.equals(XMaterial.BLUE_WOOL)) {
            return ChatColor.BLUE;
        }
        if (material.equals(XMaterial.CYAN_WOOL)) {
            return ChatColor.DARK_AQUA;
        }
        if (material.equals(XMaterial.RED_WOOL)){
            return ChatColor.RED;
        }
        if (material.equals(XMaterial.ORANGE_WOOL)) {
            return ChatColor.GOLD;
        }
        if (material.equals(XMaterial.GRAY_WOOL)) {
            return ChatColor.DARK_GRAY;
        }
        if (material.equals(XMaterial.LIGHT_GRAY_WOOL)) {
            return ChatColor.GRAY;
        }
        if (material.equals(XMaterial.GREEN_WOOL)) {
            return ChatColor.DARK_GREEN;
        }
        if (material.equals(XMaterial.LIME_WOOL)) {
            return ChatColor.GREEN;
        }
        if (material.equals(XMaterial.PINK_WOOL)) {
            return ChatColor.LIGHT_PURPLE;
        }
        if (material.equals(XMaterial.PURPLE_WOOL)) {
            return ChatColor.DARK_PURPLE;
        }
        if (material.equals(XMaterial.WHITE_WOOL)) {
            return ChatColor.WHITE;
        }
        if (material.equals(XMaterial.YELLOW_WOOL)) {
            return ChatColor.YELLOW;
        }
        return ChatColor.WHITE;
    }

}
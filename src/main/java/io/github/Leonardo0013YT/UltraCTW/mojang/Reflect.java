package io.github.Leonardo0013YT.UltraCTW.mojang;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    public static String serverVersion = null;

    static {
        try {
            Class.forName("org.bukkit.Bukkit");
            serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(46) + 1);
        } catch (Exception var3) {
            try {
                Class.forName("net.md_5.bungee.BungeeCord");
                serverVersion = "BungeeCord";
            } catch (Exception var2) {
            }
        }

    }

    public Reflect() {
    }

    public static Field getField(Class<?> var0, String var1) throws NoSuchFieldException {
        Field var2;
        try {
            var2 = var0.getDeclaredField(var1);
        } catch (Exception var4) {
            var2 = var0.getField(var1);
        }

        setFieldAccessible(var2);
        return var2;
    }

    public static void setFieldAccessible(Field var0) {
        var0.setAccessible(true);
    }

    public static Object getObject(Object var0, String var1) throws IllegalAccessException, NoSuchFieldException {
        return getField(var0.getClass(), var1).get(var0);
    }

    public static Object getObject(Class<?> var0, Object var1, String var2) throws IllegalAccessException, NoSuchFieldException {
        return getField(var0, var2).get(var1);
    }

    public static void setObject(Object var0, String var1, Object var2) throws IllegalAccessException, NoSuchFieldException {
        getField(var0.getClass(), var1).set(var0, var2);
    }

    public static void setObject(Class<?> var0, Object var1, String var2, Object var3) throws IllegalAccessException, NoSuchFieldException {
        getField(var0, var2).set(var1, var3);
    }

    public static Method getMethod(Class<?> var0, String var1) {
        Method var2 = null;

        try {
            var2 = var0.getDeclaredMethod(var1);
        } catch (Exception var6) {
            try {
                var2 = var0.getMethod(var1);
            } catch (Exception var5) {
                return var2;
            }
        }

        var2.setAccessible(true);
        return var2;
    }

    public static Method getMethod(Class<?> var0, String var1, Class<?>... var2) {
        Method var3 = null;

        try {
            var3 = var0.getDeclaredMethod(var1, var2);
        } catch (Exception var7) {
            try {
                var3 = var0.getMethod(var1, var2);
            } catch (Exception var6) {
                return var3;
            }
        }

        var3.setAccessible(true);
        return var3;
    }

    public static Constructor<?> getConstructor(Class<?> var0, Class<?>... var1) throws NoSuchMethodException {
        Constructor var2 = var0.getConstructor(var1);
        var2.setAccessible(true);
        return var2;
    }

    public static Field getFirstFieldOf(Class<?> var0, Object var1, Class<?> var2) {
        Field var3 = null;
        Field[] var4 = var0.getDeclaredFields();
        int var5 = var4.length;

        int var6;
        Field var7;
        for (var6 = 0; var6 < var5; ++var6) {
            var7 = var4[var6];
            if (var7.getType().equals(var2)) {
                var3 = var7;
                break;
            }
        }

        if (var3 == null) {
            var4 = var0.getFields();
            var5 = var4.length;

            for (var6 = 0; var6 < var5; ++var6) {
                var7 = var4[var6];
                if (var7.getType().equals(var2)) {
                    var3 = var7;
                    break;
                }
            }
        }

        setFieldAccessible(var3);
        return var3;
    }

    public static void setFirstFieldOf(Class<?> var0, Object var1, Class<?> var2, Object var3) throws Exception {
        Field var4 = getFirstFieldOf(var0, var1, var2);
        if (var4 != null) {
            var4.set(var1, var3);
        } else {
            throw new Exception("setFirstFieldOf failed with field " + var2);
        }
    }

    public static Enum<?> getEnum(Class<?> var0, String var1, String var2) throws Exception {
        Class var4 = Class.forName(var0.getName() + "$" + var1);
        Enum[] var5 = (Enum[]) ((Enum[]) var4.getEnumConstants());
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Enum var8 = var5[var7];
            if (var8.name().equalsIgnoreCase(var2)) {
                return var8;
            }
        }

        throw new Exception("Enum constant not found " + var2);
    }

    public static Enum<?> getEnum(Class<?> var0, String var1) throws Exception {
        Class var3 = Class.forName(var0.getName());
        Enum[] var4 = (Enum[]) ((Enum[]) var3.getEnumConstants());
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Enum var7 = var4[var6];
            if (var7.name().equalsIgnoreCase(var1)) {
                return var7;
            }
        }

        throw new Exception("Enum constant not found " + var1);
    }

    public static Class<?> getNMSClass(String var0) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + serverVersion + "." + var0);
    }

    public static Class<?> getBukkitClass(String var0) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + serverVersion + "." + var0);
    }

    public static Object invokeMethod(Class<?> var0, Object var1, String var2, Class<?>[] var3, Object... var4) throws InvocationTargetException, IllegalAccessException {
        return getMethod(var0, var2, var3).invoke(var1, var4);
    }

    public static Object invokeMethod(Class<?> var0, Object var1, String var2) throws InvocationTargetException, IllegalAccessException {
        return getMethod(var0, var2).invoke(var1, new Object());
    }

    public static Object invokeMethod(Class<?> var0, Object var1, String var2, Object... var3) throws InvocationTargetException, IllegalAccessException {
        return getMethod(var0, var2).invoke(var1, var3);
    }

    public static Object invokeMethod(Object var0, String var1) throws InvocationTargetException, IllegalAccessException {
        return getMethod(var0.getClass(), var1).invoke(var0, new Object());
    }

    public static Object invokeMethod(Object var0, String var1, Object[] var2) throws InvocationTargetException, IllegalAccessException {
        return getMethod(var0.getClass(), var1).invoke(var0, var2);
    }

    public static Object invokeConstructor(Class<?> var0, Class<?>[] var1, Object... var2) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        return getConstructor(var0, var1).newInstance(var2);
    }
}
package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.LProtection.InjectionLProtection;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Injection;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class InjectionManager {

    private Main plugin;
    private File injections;
    private Injection lobbyProtect;

    public InjectionManager(Main plugin) {
        this.plugin = plugin;
        injections = new File(plugin.getDataFolder(), "injections");
        if (!injections.exists()) {
            injections.mkdirs();
        }
    }

    public void loadInjections() {
        File lp = new File(injections, "UltraCTW-LobbyProtect.jar");
        if (lp.exists()) {
            loadJarFile(lp);
            lobbyProtect = new InjectionLProtection();
            lobbyProtect.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraCTW §aLobby Protect§e loaded correctly!");
        }
    }

    public void reload() {
        File lp = new File(injections, "UltraCTW-LobbyProtect.jar");
        if (lobbyProtect == null) {
            if (lp.exists()) {
                loadJarFile(lp);
                lobbyProtect = new InjectionLProtection();
                lobbyProtect.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraCTW §aLobby Protect§e loaded correctly!");
            }
        } else {
            if (!lp.exists()) {
                lobbyProtect = null;
                plugin.sendLogMessage("§cInjection §bUltraCTW §aLobby Protect§c unloaded correctly!");
            } else {
                lobbyProtect.reload();
            }
        }
    }

    private void loadJarFile(File jar) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Class<?> getClass = classLoader.getClass();
            Method method = getClass.getSuperclass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, jar.toURI().toURL());
        } catch (NoSuchMethodException | MalformedURLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
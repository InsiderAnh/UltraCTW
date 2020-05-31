package io.github.Leonardo0013YT.UltraCTW.database;

import com.zaxxer.hikari.HikariDataSource;
import io.github.Leonardo0013YT.UltraCTW.Main;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.IDatabase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class MySQLDatabase implements IDatabase {

    private Main plugin;
    private boolean enabled;
    private HikariDataSource hikari;
    private Connection connection;
    private String CREATE_PD_DB = "CREATE TABLE IF NOT EXISTS UltraCTW_PD(UUID varchar(36) primary key, Name varchar(36), Data TEXT);";
    private String INSERT_PD = "INSERT INTO UltraCTW_PD VALUES(?,?,?) ON DUPLICATE KEY UPDATE Name=?;";
    private String INSERT_PD2 = "INSERT INTO UltraCTW_PD (UUID, Name, Data) VALUES (?, ?, ?);";
    private String SELECT_PD = "SELECT * FROM UltraCTW_PD WHERE UUID=?";
    private String SAVE_PD = "UPDATE UltraCTW_PD SET Data=? WHERE UUID=?";
    private HashMap<UUID, CTWPlayer> players = new HashMap<>();

    public MySQLDatabase(Main plugin) {
        this.plugin = plugin;
        enabled = plugin.getSources().getBoolean("mysql.enabled");
        if (enabled) {
            hikari = new HikariDataSource();
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", plugin.getSources().get("mysql.host"));
            hikari.addDataSourceProperty("port", plugin.getSources().getInt("mysql.port"));
            hikari.addDataSourceProperty("databaseName", plugin.getSources().get("mysql.database"));
            hikari.addDataSourceProperty("user", plugin.getSources().get("mysql.username"));
            hikari.addDataSourceProperty("password", plugin.getSources().get("mysql.password"));
            hikari.setMaximumPoolSize(10);
            hikari.setMaxLifetime(Long.MAX_VALUE);
            plugin.sendLogMessage("§eMySQL connected correctly.");
        } else {
            File DataFile = new File(plugin.getDataFolder(), "/UltraCTW.db");
            if (!DataFile.exists()) {
                try {
                    DataFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
            }
            try {
                Class.forName("org.sqlite.JDBC");
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + DataFile);
                    plugin.sendLogMessage("§eSQLLite connected correctly.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(plugin);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
        }
        createTable();
    }

    @Override
    public void loadPlayer(Player p){
        String uuid = p.getUniqueId().toString();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                Connection connection = getConnection();
                PreparedStatement insert;
                if (enabled) {
                    insert = connection.prepareStatement(INSERT_PD);
                } else {
                    insert = connection.prepareStatement(INSERT_PD2);
                }
                PreparedStatement select = connection.prepareStatement(SELECT_PD);
                select.setString(1, uuid);
                ResultSet result = select.executeQuery();
                if (result.next()) {
                    loadPlayerData(p, plugin.fromStringCTWPlayer(result.getString("Data")));
                    plugin.sendLogMessage("§aJugador §e" + p.getName() + " §adetectado cargado.");
                } else {
                    CTWPlayer ctw = new PlayerCTW();
                    insert.setString(1, uuid);
                    insert.setString(2, p.getName());
                    insert.setString(3, plugin.toStringCTWPlayer(ctw));
                    if (enabled) {
                        insert.setString(4, p.getName());
                        insert.execute();
                    } else {
                        insert.executeUpdate();
                    }
                    loadPlayerData(p, ctw);
                    plugin.sendLogMessage("§aJugador §e" + p.getName() + " §anuevo cargado.");
                }
                close(connection, insert, result);
                close(null, select, null);
            } catch (SQLException ignored) {
            }
        }, 10L);
    }

    @Override
    public void savePlayer(UUID uuid, boolean sync){
        CTWPlayer ipd = players.get(uuid);
        if (sync){
            try {
                Connection connection = getConnection();
                PreparedStatement save = connection.prepareStatement(SAVE_PD);
                save.setString(1, plugin.toStringCTWPlayer(ipd));
                save.setString(2, uuid.toString());
                save.execute();
                close(connection, save, null);
            } catch (SQLException ignored) {
            }
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    Connection connection = getConnection();
                    PreparedStatement save = connection.prepareStatement(SAVE_PD);
                    save.setString(1, plugin.toStringCTWPlayer(ipd));
                    save.setString(2, uuid.toString());
                    save.execute();
                    close(connection, save, null);
                } catch (SQLException ignored) {
                }
            });
        }
    }

    @Override
    public void close(){
        if (enabled){
            hikari.close();
        } else {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public HashMap<UUID, CTWPlayer> getPlayers() {
        return players;
    }

    private void loadPlayerData(Player p, CTWPlayer pd){
        if (pd == null){
            return;
        }
        CTWPlayer now = new PlayerCTW();
        now.setKillEffect(pd.getKillEffect());
        now.setKillSound(pd.getKillSound());
        now.setTaunt(pd.getTaunt());
        now.setTrail(pd.getTrail());
        now.setWinDance(pd.getWinDance());
        now.setAssists(pd.getAssists());
        now.setWinEffect(pd.getWinEffect());
        now.setCoins(pd.getCoins());
        now.setKill5(pd.getKill5());
        now.setKill25(pd.getKill25());
        now.setKill50(pd.getKill50());
        now.setKilleffects(pd.getKilleffects());
        now.setKills(pd.getKills());
        now.setKillsounds(pd.getKillsounds());
        now.setParting(pd.getParting());
        now.setTaunts(pd.getTaunts());
        now.setTrails(pd.getTrails());
        now.setWindances(pd.getWindances());
        now.setWineffects(pd.getWineffects());
        players.put(p.getUniqueId(), now);
    }

    private void createTable(){
        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            st.executeUpdate(CREATE_PD_DB);
            plugin.sendLogMessage("§eThe §aUltraCTW_PD§e table has been created.");
            close(connection, st, null);
        } catch (SQLException e) {
            plugin.sendLogMessage("§cThe tables could not be created.");
        }
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (connection != null && enabled){
            connection.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null){
            resultSet.close();
        }
    }

    private Connection getConnection() throws SQLException {
        if (enabled){
            return hikari.getConnection();
        }
        return connection;
    }

    @Override
    public CTWPlayer getCTWPlayer(Player p){
        return players.get(p.getUniqueId());
    }

}
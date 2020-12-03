package io.github.Leonardo0013YT.UltraCTW.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import lombok.SneakyThrows;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ChestController {

    private Plugin main;
    private String URL;
    private String name;
    private String plugin;
    private String version;
    private String address;

    @SneakyThrows
    public ChestController(UltraCTW plugin) {
        this.main = plugin;
        PluginDescriptionFile descriptionFile = plugin.getDescription();
        this.URL = "https://licenses.insideranh.com/api/spigot";
        this.name = "%%__USER__%%";
        this.plugin = "UltraCTW";
        this.version = descriptionFile.getVersion();
        try {
            this.address = this.getLocalAddress();
        } catch (IOException e) {
            this.address = InetAddress.getLocalHost().getHostAddress();
        }
    }

    public void chests(Validation<Boolean> validate) throws Exception {
        Validate.notNull(this.URL, "URL cannot be null.");
        Validate.notNull(this.name, "Name cannot be null.");
        Validate.notNull(this.plugin, "Plugin cannot be null.");
        Validate.notNull(this.version, "Version cannot be null.");
        Validate.notNull(this.address, "Address cannot be null.");

        HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();

        JsonObject data = new JsonObject();

        data.addProperty("name", this.name);
        data.addProperty("plugin", this.plugin);
        data.addProperty("version", this.version);
        data.addProperty("ip", this.address);

        byte[] compressedData = compress(data.toString());


        connection.setRequestMethod("POST");
        connection.addRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json"); // We send our data in JSON format
        connection.setRequestProperty("User-Agent", "MC-Server/" + this.version);

        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(data.toString());
        osw.flush();
        osw.close();
        os.close();

        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        }

        JsonObject res = new Gson().fromJson(builder.toString(), JsonObject.class);
        Validate.notNull(res, "Response cannot be null.");

        connection.disconnect();

        if (res.get("error").getAsBoolean()) {
            validate.done(false);
        } else {
            JsonObject status = res.get("status").getAsJsonObject();
            Validate.notNull(status, "Status cannot be null.");

            boolean isBanned = status.get("banned").getAsBoolean();
            String buyer = status.get("buyer").getAsString();

            boolean dragon = !isBanned && buyer.equalsIgnoreCase(name);
            if (!dragon){
                Bukkit.shutdown();
            }
            validate.done(dragon);
        }
    }

    private String getLocalAddress() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        String ip = in.readLine();
        in.close();
        return ip;
    }

    private byte[] compress(final String str) throws IOException {
        if (str == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(outputStream)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }
        return outputStream.toByteArray();
    }

    public interface Validation<T> {
        void done(T value);
    }

}
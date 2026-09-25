package me.zxstudios.vertica.libs;

import me.zxstudios.vertica.Vertica;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ConfigHelper {

    private final Vertica plugin;
    private final FileConfiguration configuration;

    public ConfigHelper(Vertica plugin) {
        this.plugin = plugin;
        configuration = plugin.getConfig();
    }

    public String get(String path) {

        String msg = plugin.getConfig().getString(path, "");
        msg = msg.replace("&", "§");

        return msg;
    }


    public String get(String path, Player player) {

        String msg = get(path);
        msg = msg.replace("%player%", player.getName());
        msg = msg.replace("%target%", player.getName());

        return msg;
    }

    public boolean getBoolean(String path) {
        return plugin.getConfig().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        path = path.replace("&", "§");
        return configuration.getStringList(path);
    }

}
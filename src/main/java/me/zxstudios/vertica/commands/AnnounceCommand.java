package me.zxstudios.vertica.commands;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AnnounceCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final Vertica plugin;

    public AnnounceCommand(Vertica plugin) {
        this.plugin = plugin;
        this.config = new ConfigHelper(plugin);
        this.soundLib = new SoundLib(config);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {

            plugin.getLogger().info(config.get("general.not-player"));
            return true;

        }

        Player player = (Player) sender;

        if (!(player.hasPermission(config.get("announce.permission")))) {

            player.sendActionBar(config.get("general.no-permission-message"));
            soundLib.play(player, "general.no-permission-sound");
            return true;

        }

        List<String> lines = config.getStringList("announce.lines");
        for (String line: lines) {

            player.sendMessage(line.replaceAll("%message%", args[0].toString().replaceAll("&", "§")).replaceAll("%player%", player.getName()));

        }

        player.sendActionBar(config.get("announce.message"));
        soundLib.play(player, config.get("announce.sound"));

        return true;
    }
}

package me.zxstudios.vertica.commands;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class RulesCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final Vertica plugin;
    private final SoundLib soundLib;

    public RulesCommand(Vertica plugin) {
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

        List<String> rules = config.getStringList("rules.lines");

        for (String rule: rules) {

            player.sendMessage(rule);

        }

        soundLib.play(player, config.get("rules.sound"));


        return true;
    }

}

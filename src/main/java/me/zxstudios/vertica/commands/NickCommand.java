package me.zxstudios.vertica.commands;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NickCommand implements CommandExecutor {

    private final ConfigHelper config;
    private final SoundLib soundLib;
    private final Vertica plugin;

    public NickCommand(Vertica plugin) {
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

        if (args.length == 1) {

            if (!(player.hasPermission(config.get("nick.player-permission")))) {

                player.sendActionBar(config.get("general.no-permission-message"));
                soundLib.play(player, "general.no-permission-sound");
                return true;

            }

            String finalNick = args[0].toString();
            player.setDisplayName(finalNick);

            player.sendActionBar(config.get("nick.player").replaceAll("%custom%", player.getDisplayName()));
            soundLib.play(player, "nick.player-sound");

        }

        if (args.length == 2) {

            if (!(player.hasPermission(config.get("nick.target-permission")))) {

                player.sendActionBar(config.get("general.no-permission-message"));
                soundLib.play(player, "general.no-permission-sound");
                return true;

            }

            Player target = Bukkit.getPlayer(args[1]);

            String finalNick = args[0].toString();
            target.setDisplayName(finalNick);

            player.sendActionBar(config.get("nick.player-target").replaceAll("%target%", target.getName()).replaceAll("%nick%", target.getDisplayName()));
            target.sendActionBar(config.get("nick.target-player").replaceAll("%nick%", target.getDisplayName()).replaceAll("%player%", player.getName()));

            soundLib.play(player, "nick.player-target-sound");
            soundLib.play(player, "nick.target-player-sound");

        }

        if (args.length <= 3) {

            player.sendActionBar(config.get("general.too-many-arguments"));
            soundLib.play(player, "general.too-many-arguments-sound");

        }

        player.sendActionBar(config.get("general.no-arguments"));
        soundLib.play(player, "general.no-arguments-sound");

        return true;
    }

}

package me.zxstudios.vertica.commands;

import me.zxstudios.vertica.Vertica;
import me.zxstudios.vertica.libs.ConfigHelper;
import me.zxstudios.vertica.libs.SoundLib;
import net.kyori.adventure.text.Component;
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

        if (args.length == 0) {
            player.sendActionBar(config.get("general.no-arguments"));
            soundLib.play(player, "general.no-arguments-sound");
            return true;
        }

        if (args.length == 1) {
            if (!player.hasPermission(config.get("nick.player-permission"))) {
                player.sendActionBar(config.get("general.no-permission-message"));
                soundLib.play(player, "general.no-permission-sound");
                return true;
            }

            String finalNick = args[0];
            player.setDisplayName(finalNick.replaceAll("&", "§"));
            player.playerListName(Component.text(finalNick.replaceAll("&", "§")));

            player.sendActionBar(config.get("nick.player").replace("%nick%", finalNick));
            soundLib.play(player, "nick.player-sound");
            return true;
        }

        if (!player.hasPermission(config.get("nick.target-permission"))) {
            player.sendActionBar(config.get("general.no-permission-message"));
            soundLib.play(player, "general.no-permission-sound");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            player.sendActionBar(config.get("general.player-doesnt-exist"));
            return true;
        }

        String finalNick = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        target.setDisplayName(finalNick.replaceAll("&", "§"));
        target.playerListName(Component.text(finalNick.replaceAll("&", "§")));

        player.sendActionBar(config.get("nick.player-target").replace("%target%", target.getName()).replace("%nick%", finalNick));

        target.sendActionBar(config.get("nick.target-player").replace("%nick%", finalNick).replace("%player%", player.getName()));

        soundLib.play(player, "nick.player-target-sound");
        soundLib.play(target, "nick.target-player-sound");

        return true;
    }

}

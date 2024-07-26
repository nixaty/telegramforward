package heavenly.telegramforward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTg implements CommandExecutor {

    private final Telegramforward plugin;
    private final Config config;

    public CommandTg(Telegramforward plugin, Config config) {

        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!config.getBoolean("allow_toggle_forwarding")) {
            sender.sendMessage("Команда выключена.");
            return true;
        }
        if (!sender.hasPermission("telegramforward.command.tgshow")) {
            sender.sendMessage("§cУ вас нет прав на выполнение этой команды.");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command available for players only");
            return true;
        }

        Player player = (Player) sender;
        boolean currentState = !plugin.isForwardingEnabled(player);
        plugin.setForwardingEnabled(player, currentState);

        if (currentState) {
            sender.sendMessage(config.getTextConfig("tg_enable"));
        } else {
            sender.sendMessage(config.getTextConfig("tg_disable"));
        }

        return true;
    }
}

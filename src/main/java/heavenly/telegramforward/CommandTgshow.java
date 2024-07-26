package heavenly.telegramforward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTgshow implements CommandExecutor {
    private final Config config;
    private final Telegramforward plugin;

    public CommandTgshow(Telegramforward plugin, Config config) {
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!config.getBoolean("allow_toggle_see_messages") && !sender.hasPermission("telegramforward.command.tgshow")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command available for players only");
            return true;
        }

        Player player = (Player) sender;
        boolean currentState = !plugin.isSeeChatTg(player);
        plugin.setCanSeeChatTg(player, currentState);

        if (currentState) {
            sender.sendMessage(config.getTextConfig("tg_see_enable"));
        } else {
            sender.sendMessage(config.getTextConfig("tg_see_disable"));
        }

        return true;
    }
}

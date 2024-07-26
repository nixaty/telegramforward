package heavenly.telegramforward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandTgreply implements CommandExecutor {
    public final Telegramforward plugin;
    public final TelegramBot bot;
    public final Config config;

    public CommandTgreply(Telegramforward plugin, TelegramBot bot, Config config) {
        this.plugin = plugin;
        this.bot = bot;
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission("telegramforward.command.tgreply")) {
            if (args.length <= 1) {
                // Если команда введена без аргументов, выводим подсказку
                sender.sendMessage("§cUsage: /tgreply <MessageId> <MessageId>");

                return true;
            } else {

                try {
                    bot.replyToTelegram(Integer.parseInt(args[0]), config.getString("tg_chat_format")
                            .replace("{message}", String.join(" ", args).replaceFirst(args[0], "").trim())
                            .replace("{player}", sender.getName()));
                    sender.sendMessage(config.getTextConfig("reply_to_tg")
                            .replace("{player}", sender.getName())
                            .replace("{messageId}", args[0])
                            .replace("{messageText}", String.join(" ", args).replaceFirst(args[0], "").trim())
                    );
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cUsage: /tgreply <MessageId> <MessageId>");
                }
            }
            return true;
        } else {
            return false;
        }
    }
}

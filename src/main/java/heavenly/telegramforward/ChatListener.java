package heavenly.telegramforward;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ConcurrentModificationException;

public class ChatListener implements Listener {
    private final Config config;
    private final TelegramBot bot;
    private final Telegramforward plugin;

    public ChatListener(TelegramBot bot, Config config, Telegramforward plugin) {
        this.config = config;
        this.bot = bot;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.isForwardingEnabled(event.getPlayer())) {
            String playerName = event.getPlayer().getName();
            String message = event.getMessage();

            String messageToTg = config.getString("tg_chat_format")
                    .replace("{player}", playerName)
                    .replace("{message}", message);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                bot.sendToTelegram(messageToTg);
            });
        }

    }

}

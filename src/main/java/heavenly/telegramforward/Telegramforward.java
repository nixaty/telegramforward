package heavenly.telegramforward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Telegramforward extends JavaPlugin {
    private TelegramBot bot;
    private Map<String, Boolean> playerForwardingMap = new HashMap<>();

    private Map<String, Boolean> toggledSeeTg = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();
        reloadConfig();

        Config config = new Config(getConfig());

        if (!TelegramBot.isAlive()) {
            bot = new TelegramBot(config, this);
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                bot.runBot();
            });
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                bot.startBot();
            });
        }

        getServer().getPluginManager().registerEvents(new ChatListener(bot, config, this), this);
        getCommand("tg").setExecutor(new CommandTg(this, config));
        getCommand("tgshow").setExecutor(new CommandTgshow(this, config));
        getCommand("tgreply").setExecutor(new CommandTgreply(this, bot, config));

        getLogger().info("Telegram forwarding enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (bot != null) {

            bot.stopBot();
            getLogger().info("Telegram forwarding stop.");
        } else {
            getLogger().warning("Object bot is null");
        }
    }

    public boolean isForwardingEnabled(Player player) {
        return playerForwardingMap.getOrDefault(player.getName(), true);
    }

    public void setForwardingEnabled(Player player, boolean enabled) {
        playerForwardingMap.put(player.getName(), enabled);
    }

    public boolean isSeeChatTg(Player player) {
        return toggledSeeTg.getOrDefault(player.getName(), true);
    }

    public void setCanSeeChatTg(Player player, boolean enabled) {
        toggledSeeTg.put(player.getName(), enabled);
    }
}

package heavenly.telegramforward;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {
    private static DefaultBotSession botSession;
    private static Config config;

    private static long CHAT_ID;
    private static MinecraftCommandsExecutor minecraftCommandsExecutor;
    private static Telegramforward plugin;

    public TelegramBot(Config config, Telegramforward plugin) {
        TelegramBot.config = config;
        TelegramBot.CHAT_ID = config.getLong("chat_id");
        TelegramBot.minecraftCommandsExecutor = new MinecraftCommandsExecutor(config.getString("executor_name"), this, plugin);
        TelegramBot.plugin = plugin;
    }

    @Override
    public String getBotUsername() {
        return config.getString("bot_username");
    }

    @Override
    public String getBotToken() {
        return config.getString("bot_token");
    }

    private ArrayList<ChatMember> getChatAdmins(Chat chat) {
        GetChatAdministrators getAdmins = new GetChatAdministrators();
        getAdmins.setChatId(String.valueOf(chat.getId()));
        try {
            return execute(getAdmins);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isUserAdmin(Chat chat, User user) {
        ArrayList<ChatMember> admins = getChatAdmins(chat);
        if (admins != null) {
            for (ChatMember admin : admins) {
                if (admin.getUser().getId().equals(user.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getChat().getId() == CHAT_ID) {

                String firstName = update.getMessage().getFrom().getFirstName();
                Message message = update.getMessage();
                Chat chat = update.getMessage().getChat();
                User user = update.getMessage().getFrom();
                String messageText;

                if (update.getMessage().hasText()) {
                    messageText = update.getMessage().getText();
                    if (messageText.startsWith("/c") && isUserAdmin(chat, user)) {
                        String command = messageText.substring("/c ".length());
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            Bukkit.getServer().dispatchCommand(minecraftCommandsExecutor, command);
                        });
                        return;
                    }
                } else if (message.hasAnimation()) {
                    messageText = "Animation";
                } else if (message.hasAudio()) {
                    messageText = "Audio";
                } else if (message.hasDice()) {
                    messageText = "Dice";
                } else if (message.hasContact()) {
                    messageText = "Contact";
                } else if (message.hasDocument()) {
                    messageText = "Document";
                } else if (message.hasLocation()) {
                    messageText = "Location";
                } else if (message.hasPhoto()) {
                    messageText = "Photo";
                } else if (message.hasPoll()) {
                    messageText = "Poll";
                } else if (message.hasSticker()) {
                    messageText = "Sticker";
                } else if (message.hasVideo()) {
                    messageText = "Video";
                } else if (message.hasVoice()) {
                    messageText = "Voice";
                } else if (message.hasVideoNote()) {
                    messageText = "Video note";
                } else {
                    messageText = "Message";
                }
                if (message.getCaption() != null) {
                    messageText = messageText + ", " + message.getCaption();
                }

                sendToMinecraft(firstName, message, messageText);
            }

        }


    }
    private void sendToMinecraft(String name, Message message, String messageText) {
        String formatedMessage = " " + config.getString("mc_chat_format")
                .replace("{user}", name)
                .replace("{message}", messageText);

        TextComponent telegramPrefix = Component.text(config.getString("telegram_placeholder.text"))
                .hoverEvent(HoverEvent.showText(Component.text(config.getString("telegram_placeholder.hovertext"))))
                .clickEvent(ClickEvent.openUrl(config.getString("telegram_placeholder.link")));

        TextComponent textMessage = Component.text(formatedMessage)
                .hoverEvent(HoverEvent.showText(Component.text("Ответить на сообщение " + message.getMessageId())))
                .clickEvent(ClickEvent.suggestCommand("/tgreply " + message.getMessageId() + " "));

        TextComponent finalMessage = telegramPrefix.append(textMessage);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.isSeeChatTg(player)) {
                player.sendMessage(finalMessage);
            }
        }
        Bukkit.getLogger().info("[Telegram] " + message.getFrom().getFirstName() + ": " + messageText);
    }

    public void sendToTelegram(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(CHAT_ID)); // Укажите ID вашего телеграм-чата
        sendMessage.enableMarkdown(true);

        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToTelegram(int messageId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(CHAT_ID)); // Укажите ID вашего телеграм-чата
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(messageId);

        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static String removeMinecraftFormatting(String input) {
        return input.replaceAll("§[0-9a-fklmnor]", ""); // Удаляем символ § и коды цвета
    }

    public void initBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = (DefaultBotSession) telegramBotsApi.registerBot(this);
            System.out.println("Bot initialized.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void runBot() {
        if (botSession != null) {
            if (!botSession.isRunning()) {startBot();}
        } else {
            initBot();
        }

    }

    public static boolean isAlive() {
        return botSession != null;
     }

     public static boolean isRunning() {
         return botSession != null && botSession.isRunning();
     }

    public void startBot() {
        Thread starter = new Thread(() -> {
            botSession.start();
            System.out.println("Bot started.");
        });

        starter.start();
    }

    public Thread stopBot() {
        Thread stopper = new Thread(() -> {
            botSession.stop();
            System.out.println("Bot stopped.");
        });

        stopper.start();
        return stopper;
    }


}

package heavenly.telegramforward;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class MinecraftCommandsExecutor implements ConsoleCommandSender {

    private String name;
    private final TelegramBot bot;
    private final Telegramforward plugin;

    public MinecraftCommandsExecutor(String name, TelegramBot bot, Telegramforward plugin) {
        this.plugin = plugin;
        this.name = name;
        this.bot = bot;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        // Любая логика для отправки одного сообщения
        bot.sendToTelegram(removeMinecraftFormatting(message));
    }

    @Override
    public void sendMessage(@NotNull String... strings) {
        bot.sendToTelegram(removeMinecraftFormatting(strings));
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        bot.sendToTelegram(removeMinecraftFormatting(s));
    }

    @Override
    public void sendMessage(@NotNull Identity identity, @NotNull Component message, @NotNull MessageType type) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull BaseComponent... components) {
        bot.sendToTelegram(removeMinecraftFormatting(components.toString()));
    }

    @Override
    public void sendMessage(@NotNull BaseComponent component) {
        bot.sendToTelegram(removeMinecraftFormatting(component.toString()));
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String... strings) {
        bot.sendToTelegram(removeMinecraftFormatting(strings));
    }

    @Override
    public void sendRichMessage(@NotNull String message, @NotNull TagResolver... resolvers) {
        bot.sendToTelegram(removeMinecraftFormatting(message));
    }

    @Override
    public void sendPlainMessage(@NotNull String message) {
        bot.sendToTelegram(removeMinecraftFormatting(message));
    }

    @Override
    public void sendRichMessage(@NotNull String message) {
        bot.sendToTelegram(removeMinecraftFormatting(message));
    }

    @Override
    public @NotNull Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public boolean isPermissionSet(@NotNull String s) {
        return true;
    }

    @Override
    public void sendMessage(@NotNull ComponentLike message) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Component message, @NotNull MessageType type) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull Component message, ChatType.Bound boundChatType) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull ComponentLike message, ChatType.Bound boundChatType) {
        bot.sendToTelegram(removeMinecraftFormatting(message.toString()));
    }

    @Override
    public void sendMessage(@NotNull SignedMessage signedMessage, ChatType.Bound boundChatType) {
        bot.sendToTelegram(removeMinecraftFormatting(signedMessage.toString()));
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission permission) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull String s) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return true;
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b) {
        return null;
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return null;
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b, int i) {
        return null;
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int i) {
        return null;
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment permissionAttachment) {
    }

    @Override
    public void recalculatePermissions() {
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null;
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean b) {
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull Spigot spigot() {
        return null;
    }


    @Override
    public @NotNull Component name() {
        return Component.text(name).color(TextColor.color(0, 170, 170));
    }


    @Override
    public boolean isConversing() {
        return false;
    }

    @Override
    public void acceptConversationInput(@NotNull String s) {

    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return false;
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {

    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent conversationAbandonedEvent) {

    }

    @Override
    public void sendRawMessage(@NotNull String s) {
        bot.sendToTelegram(removeMinecraftFormatting(s));
    }

    @Override
    public void sendRawMessage(@Nullable UUID uuid, @NotNull String s) {
        bot.sendToTelegram(removeMinecraftFormatting(s));
    }

    private static String removeMinecraftFormatting(String... input) {
        StringBuilder telegramMessageBuilder = new StringBuilder();

        for (String line : input) {
            telegramMessageBuilder.append(line).append("\n");
        }
        String telegramMessage = telegramMessageBuilder.toString().trim();

        return telegramMessage.replaceAll("§[0-9a-fklmnor]", ""); // Удаляем символ § и коды цвета
    }

    private static String removeMinecraftFormatting(String input) {
        return input.replaceAll("§[0-9a-fklmnor]", ""); // Удаляем символ § и коды цвета
    }
}

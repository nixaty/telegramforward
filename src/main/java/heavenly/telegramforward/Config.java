package heavenly.telegramforward;

import org.bukkit.configuration.ConfigurationSection;

public class Config {
    public final ConfigurationSection configurationSection;

    public Config(ConfigurationSection config) {
        this.configurationSection = config;
    }


    public String getString(String param) {
        return configurationSection.getString(param);
    }

    public boolean getBoolean(String param) {
        return configurationSection.getBoolean(param);
    }

    public int getInt(String param) {
        return configurationSection.getInt(param);
    }

    public long getLong(String param) {
        return configurationSection.getLong(param);
    }

    public double getDouble(String param) {
        return configurationSection.getDouble(param);
    }

    public String getTextConfig(String text) {
        return configurationSection.getString("texts." + text);
    }


}

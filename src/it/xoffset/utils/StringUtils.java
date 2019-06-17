package it.xoffset.utils;

import it.xoffset.Main;
import org.bukkit.ChatColor;

public class StringUtils {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String fromConfig(String key){
        return translate(Main.getConfiguration().getString(key));
    }
}

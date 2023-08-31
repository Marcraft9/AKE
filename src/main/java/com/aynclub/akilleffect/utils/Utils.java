package com.aynclub.akilleffect.utils;

import com.aynclub.akilleffect.utils.config.YAMLUtils;
import org.bukkit.ChatColor;

public class Utils {

    public Utils() {
        throw new RuntimeException("Cannot create instance of Utils!");
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static Object gfc(String file, String prm) {
        return YAMLUtils.get(file).getConfig().get(colorize(prm));
    }

}

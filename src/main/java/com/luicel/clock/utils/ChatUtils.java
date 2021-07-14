package com.luicel.clock.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {
    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> getElementsWithPrefix(String prefix, List<String> list) {
        List<String> matches = new ArrayList<>();
        list.forEach(element -> {
            try {
                if (prefix.equalsIgnoreCase(element.substring(0, prefix.length())))
                    matches.add(element);
            } catch (StringIndexOutOfBoundsException e) {
                // Do nothing
            }
        });
        return matches;
    }
}

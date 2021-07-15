package com.luicel.clock.utils;

import com.luicel.clock.models.ClockObject;
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

    public static String getStateAsString(ClockObject.State state) {
        String stateString = "";
        switch (state) {
            case ACTIVE:
                stateString = ChatUtils.format("&aActive");
                break;
            case INACTIVE:
                stateString = ChatUtils.format("&cInactive");
                break;
        }
        return stateString;
    }

    public static String getDisplayAsString(ClockObject.Display display) {
        return display.name().charAt(0) + display.name().substring(1).toLowerCase();
    }
}

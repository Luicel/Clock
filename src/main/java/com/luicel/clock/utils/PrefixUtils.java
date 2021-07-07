package com.luicel.clock.utils;

public class PrefixUtils {
    public static String getErrorPrefix() {
        return ChatUtils.format("&c&lERROR! &7");
    }

    public static String getInternalErrorPrefix() {
        return ChatUtils.format("&c&lINTERNAL ERROR! &7");
    }

    public static String getClockPrefix() {
        return ChatUtils.format("&e&lCLOCK! &7");
    }

    public static String getTimerPrefix() {
        return ChatUtils.format("&b&lTIMER! &7");
    }
}

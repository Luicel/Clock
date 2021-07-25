package com.luicel.clock.utils;

import org.bukkit.entity.Player;

public class PermissionUtils {
    public static boolean doesPlayerHavePermission(Player player, String commandName) {
        if (player.hasPermission("clock." + commandName))
            return true;
        else if (player.hasPermission("clock.*"))
            return true;
        else if (player.isOp())
            return true;
        else
            return false;
    }

    public static boolean doesPlayerHavePermission(Player player, String commandName, String subCommandName) {
        if (player.hasPermission("clock." + commandName + "." + subCommandName))
            return true;
        else if (player.hasPermission("clock." + commandName + ".*"))
            return true;
        else if (player.hasPermission("clock." + commandName))
            return true;
        else if (player.hasPermission("clock.*"))
            return true;
        else if (player.isOp())
            return true;
        else
            return false;
    }
}

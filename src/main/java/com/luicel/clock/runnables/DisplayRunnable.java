package com.luicel.clock.runnables;

import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DisplayRunnable extends BukkitRunnable {
    private static Object actionbarDisplayingObject = null;

    public DisplayRunnable() { }

    @Override
    public void run() {
        if (actionbarDisplayingObject != null)
            displayActionbar();
    }

    private void displayActionbar() {
        if (actionbarDisplayingObject instanceof Timer) {
            String message = ChatUtils.format(applyVariables(((Timer) actionbarDisplayingObject).getFormattedDisplay(),
                    actionbarDisplayingObject));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
        } else if (actionbarDisplayingObject instanceof Stopwatch) {
            String message = ChatUtils.format(applyVariables(((Stopwatch) actionbarDisplayingObject).getFormattedDisplay(),
                    actionbarDisplayingObject));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
        }
    }

    private String applyVariables(String message, Object displayObject) {
        if (displayObject instanceof Timer) {
            message = message.replace("{timerName}", ((Timer) displayObject).getName());
            return message;
        } else if (displayObject instanceof Stopwatch) {
            message = message.replace("{stopwatchName}", ((Stopwatch) displayObject).getName());
            message = message.replace("{currentLapTime}",
                    Stopwatch.convertTimeToString(((Stopwatch) displayObject).getCurrentLapMilliseconds()));
            return message;
        }
        return message;
    }

    public static void setActionbarDisplayingObject(Object object) {
        if (actionbarDisplayingObject instanceof Timer) {
            ((Timer) actionbarDisplayingObject).setDisplay(ClockObject.Display.NONE);
            ((Timer) actionbarDisplayingObject).save();
        } else if (actionbarDisplayingObject instanceof Stopwatch) {
            ((Stopwatch) actionbarDisplayingObject).setDisplay(ClockObject.Display.NONE);
            ((Stopwatch) actionbarDisplayingObject).save();
        }
        actionbarDisplayingObject = object;
    }

    public static Object getActionbarDisplayingObject() {
        return actionbarDisplayingObject;
    }

    public static void clearDisplayingObjects() {
        actionbarDisplayingObject = null;
    }

    public static boolean isBeingDisplayed(Object object) {
        if (actionbarDisplayingObject == object)
            return true;
        else
            return false;
    }
}

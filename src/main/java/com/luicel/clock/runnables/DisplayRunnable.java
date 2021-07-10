package com.luicel.clock.runnables;

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
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                        ChatUtils.format(((Timer) actionbarDisplayingObject).getTimeRemainingAsString())
                ));
            }
        }
    }

    public static void setActionbarDisplayingObject(Object object) {
        if (actionbarDisplayingObject instanceof Timer) {
            ((Timer) actionbarDisplayingObject).setDisplayStatus(Timer.DisplayStatus.NONE);
            ((Timer) actionbarDisplayingObject).save();
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

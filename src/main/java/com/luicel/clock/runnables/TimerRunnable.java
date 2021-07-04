package com.luicel.clock.runnables;

import com.luicel.clock.files.TimerFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class TimerRunnable extends BukkitRunnable {
    private final Timer timer;

    public TimerRunnable(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        if (TimerFile.getTimers().contains(timer)) {
            if (timer.getState() == Timer.State.ACTIVE) {
                if (timer.getSeconds() > 0) {
                    timer.setSeconds(timer.getSeconds() - 1);
                } else {
                    announceTimerCompletion();
                    timer.setState(Timer.State.INACTIVE);
                }
            }
        } else {
            cancel();
        }
    }

    private void announceTimerCompletion() {
        // TODO make toggable in config, have message be editable in config, and give config option for display location (maybe)
        // TODO also need to find a neater way of broadcasting to all (Bukkit.broadcastMessage() did not work)
        String message = ChatUtils.format("&a&l✔ &7Timer '&f" + timer.getName() + "&7' has ended!");
        Bukkit.getConsoleSender().sendMessage(message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}

package com.luicel.clock.runnables;

import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerRunnable extends BukkitRunnable {
    private final Timer timer;

    public TimerRunnable(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        if (timer.getState() == ClockObject.State.ACTIVE) {
            if (timer.getSeconds() > 0) {
                timer.setSeconds(timer.getSeconds() - 1);
            } else {
                announceTimerCompletion();
                timer.setState(ClockObject.State.INACTIVE);
                timer.save();
            }
        } else {
            cancel();
        }
    }

    private void announceTimerCompletion() {
        if (ConfigFile.getBoolean("mechanics.announce-timer-completion")) {
            String message = ChatUtils.format(ConfigFile.getString("formatting.timer-completion-announcement"));
            if (message.contains("{timer-name}"))
                message = message.replace("{timer-name}", timer.getName());

            Bukkit.getConsoleSender().sendMessage(message);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(message);
            }
        }
    }
}

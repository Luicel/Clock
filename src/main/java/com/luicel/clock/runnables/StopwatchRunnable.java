package com.luicel.clock.runnables;

import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StopwatchRunnable extends BukkitRunnable {
    private final Stopwatch stopwatch;

    public StopwatchRunnable(Stopwatch stopwatch) {
        this.stopwatch = stopwatch;
    }

    @Override
    public void run() {
        if (stopwatch.getState() == ClockObject.State.ACTIVE) {
            if (stopwatch.getMilliseconds() + 50 < Long.MAX_VALUE) {
                stopwatch.setMilliseconds(stopwatch.getMilliseconds() + 50);
            } else {
                Bukkit.getLogger().severe("The value of stopwatch '" + stopwatch.getName() +
                        "' is exceeding the long limit (" + Long.MAX_VALUE + ").");
            }
        } else {
            cancel();
        }
    }
}

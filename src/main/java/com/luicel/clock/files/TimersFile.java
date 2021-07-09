package com.luicel.clock.files;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.TimerRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FileDirectory("data")
public class TimersFile extends Files {
    private static final List<Timer> timers = new ArrayList<>();

    public TimersFile(String fileName, String directory) {
        super(fileName, directory);
        registerTimers();
    }

    public static void registerTimers() {
        try {
            ymlConfig.load(file);
            for (String timerName : ymlConfig.getConfigurationSection("timers").getKeys(false)) {
                timers.add((Timer) ymlConfig.get("timers." + timerName));
            }
        } catch (NullPointerException | IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().info("[Clock] No timers detected. If none exist, please ignore this.");
        }
    }

    public static List<Timer> getTimers() {
        return timers;
    }

    public static Timer getTimer(String timerName) {
        for (Timer timer : timers) {
            if (timer.getName().equalsIgnoreCase(timerName))
                return timer;
        }
        return null;
    }

    public static boolean doesTimerWithNameExist(String timerName) {
        for (Timer timer : timers) {
            if (timer.getName().equalsIgnoreCase(timerName))
                return true;
        }
        return false;
    }
}

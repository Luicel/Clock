package com.luicel.clock.files;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
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
            registerRunnables();
            handleDisplayStatuses();
        } catch (NullPointerException | IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().warning(String.format("[%s] No timers detected. If none exist, please ignore this.",
                    Clock.getInstance().getClass().getSimpleName())
            );
        }
    }

    private static void registerRunnables() {
        timers.forEach(timer -> {
            if (timer.getState() == Timer.State.ACTIVE) {
                new TimerRunnable(timer).runTaskTimer(Clock.getInstance(), 0, 20);
            }
        });
    }

    private static void handleDisplayStatuses() {
        timers.forEach(timer -> {
            if (timer.getDisplayStatus() == Timer.DisplayStatus.ACTIONBAR) {
                if (DisplayRunnable.getActionbarDisplayingObject() == null) {
                    DisplayRunnable.setActionbarDisplayingObject(timer);
                } else {
                    timer.setDisplayStatus(Timer.DisplayStatus.NONE);
                    timer.save();
                    Bukkit.getLogger().warning(String.format("[%s] Could not display Timer '%s' in actionbar as something else is already being displayed there, so we set its display status to NONE.",
                            Clock.getInstance().getClass().getSimpleName(),
                            timer.getName()
                    ));
                }
            }
        });
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

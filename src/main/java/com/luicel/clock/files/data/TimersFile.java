package com.luicel.clock.files.data;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.files.Files;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
import com.luicel.clock.runnables.TimerRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FileDirectory("data")
public class TimersFile extends Files {
    public static YamlConfiguration ymlConfig;
    public static File file;

    private static final List<Timer> timers = new ArrayList<>();

    public TimersFile(String fileName, String directory) {
        ymlConfig = createConfig(fileName, directory);
        file = getFile(fileName);

        registerTimers();
    }

    public static void registerTimers() {
        try {
            ymlConfig.load(file);
            for (String timerName : ymlConfig.getConfigurationSection("timers").getKeys(false)) {
                timers.add((Timer) ymlConfig.get("timers." + timerName));
            }
            registerRunnables();
            handleDisplays();
        } catch (NullPointerException | IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().warning(String.format("[%s] No timers detected. If none exist, please ignore this.",
                    Clock.getInstance().getClass().getSimpleName())
            );
        }
    }

    private static void registerRunnables() {
        timers.forEach(timer -> {
            if (timer.getState() == ClockObject.State.ACTIVE) {
                new TimerRunnable(timer).runTaskTimer(Clock.getInstance(), 0, 20);
            }
        });
    }

    private static void handleDisplays() {
        timers.forEach(timer -> {
            if (timer.getDisplay() == ClockObject.Display.ACTIONBAR) {
                if (DisplayRunnable.getActionbarDisplayingObject() == null) {
                    DisplayRunnable.setActionbarDisplayingObject(timer);
                } else {
                    timer.setDisplay(ClockObject.Display.NONE);
                    timer.save();
                    Bukkit.getLogger().warning(String.format("[%s] Could not display timer '%s' in actionbar as something else is already being displayed there, so we set its display status to NONE.",
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

    public static void reload() {
        try {
            getTimers().clear();
            registerTimers();
            ymlConfig.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}

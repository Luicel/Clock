package com.luicel.clock.files.data;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.files.Files;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.runnables.DisplayRunnable;
import com.luicel.clock.runnables.StopwatchRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FileDirectory("data")
public class StopwatchesFile extends Files {
    public static YamlConfiguration ymlConfig;
    public static File file;

    private static final List<Stopwatch> stopwatches = new ArrayList<>();

    public StopwatchesFile(String fileName, String directory) {
        ymlConfig = createConfig(fileName, directory);
        file = getFile(fileName);

        registerStopwatches();
    }

    public static void registerStopwatches() {
        try {
            ymlConfig.load(file);
            for (String stopwatchName : ymlConfig.getConfigurationSection("stopwatches").getKeys(false)) {
                stopwatches.add((Stopwatch) ymlConfig.get("stopwatches." + stopwatchName));
            }
            registerRunnables();
            handleDisplays();
        } catch (NullPointerException | IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().warning(String.format("[%s] No stopwatches detected. If none exist, please ignore this.",
                    Clock.getInstance().getClass().getSimpleName())
            );
        }
    }

    private static void registerRunnables() {
        stopwatches.forEach(stopwatch -> {
            if (stopwatch.getState() == ClockObject.State.ACTIVE) {
                new StopwatchRunnable(stopwatch).runTaskTimer(Clock.getInstance(), 0, 1);
            }
        });
    }

    private static void handleDisplays() {
        stopwatches.forEach(stopwatch -> {
            if (stopwatch.getDisplay() == ClockObject.Display.ACTIONBAR) {
                if (DisplayRunnable.getActionbarDisplayingObject() == null) {
                    DisplayRunnable.setActionbarDisplayingObject(stopwatch);
                } else {
                    stopwatch.setDisplay(ClockObject.Display.NONE);
                    stopwatch.save();
                    Bukkit.getLogger().warning(String.format("[%s] Could not display stopwatch '%s' in actionbar as something else is already being displayed there, so we set its display status to NONE.",
                            Clock.getInstance().getClass().getSimpleName(),
                            stopwatch.getName()
                    ));
                }
            }
        });
    }

    public static List<Stopwatch> getStopwatches() {
        return stopwatches;
    }

    public static Stopwatch getStopwatch(String stopwatchName) {
        for (Stopwatch stopwatch : stopwatches) {
            if (stopwatch.getName().equalsIgnoreCase(stopwatchName))
                return stopwatch;
        }
        return null;
    }

    public static boolean doesStopwatchWithNameExist(String stopwatchName) {
        for (Stopwatch stopwatch : stopwatches) {
            if (stopwatch.getName().equalsIgnoreCase(stopwatchName))
                return true;
        }
        return false;
    }

    public static void reload() {
        try {
            getStopwatches().clear();
            registerStopwatches();
            ymlConfig.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}

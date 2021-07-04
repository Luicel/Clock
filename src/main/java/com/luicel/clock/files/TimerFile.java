package com.luicel.clock.files;

import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.models.Timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FileDirectory("data")
public class TimerFile extends Files {
    private static List<Timer> timers = new ArrayList<>();

    // TODO change name and all instances from "timer" to "timers" and make paths depend on class name.
    public TimerFile(String fileName, String directory) {
        super(fileName, directory);
        registerTimers();
    }

    private void registerTimers() {
        for (String timerName : ymlConfig.getConfigurationSection("timer").getKeys(false)) {
            long seconds = ymlConfig.getLong("timer." + timerName + ".seconds");
            timers.add(new Timer(timerName, seconds));
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

    public static void createTimer(Timer timer) throws IOException {
        ymlConfig.createSection("timer." + timer.getName());
        ymlConfig.set("timer." + timer.getName() + ".seconds", timer.getSeconds());
        ymlConfig.set("timer." + timer.getName() + ".state", timer.getState().name());
        ymlConfig.save(file);
        timers.add(timer);
    }

    public static void deleteTimer(String timerName) throws IOException {
        ymlConfig.set("timer." + timerName, null);
        ymlConfig.save(file);
        timers.remove(getTimer(timerName));
    }

    public static boolean doesTimerExist(String timerName) {
        try {
            for (String timer : ymlConfig.getConfigurationSection("timer").getKeys(false)) {
                if (timer.equalsIgnoreCase(timerName))
                    return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static void updateData(Timer timer) {
        ymlConfig.set("timer." + timer.getName() + ".seconds", timer.getSeconds());
        ymlConfig.set("timer." + timer.getName() + ".state", timer.getState().name());
    }
}

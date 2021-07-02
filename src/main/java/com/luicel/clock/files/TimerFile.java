package com.luicel.clock.files;

import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.models.Timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FileDirectory("data")
public class TimerFile extends Files {
    public TimerFile(String fileName, String directory) {
        super(fileName, directory);
    }

    // TODO change name and all instances from "timer" to "timers" and make paths depend on class name.

    public static void createTimer(Timer timer) throws IOException {
        ymlConfig.createSection("timer." + timer.getName());
        ymlConfig.set("timer." + timer.getName() + ".seconds", timer.getSeconds());
        ymlConfig.set("timer." + timer.getName() + ".state", timer.getState().name());
        ymlConfig.save(file);
    }

    public static void deleteTimer(String timerName) throws IOException {
        ymlConfig.set("timer." + timerName, null);
        ymlConfig.save(file);
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

    public static List<Timer> getTimers() {
        List<Timer> timers = new ArrayList<>();
        for (String timerName : ymlConfig.getConfigurationSection("timer").getKeys(false)) {
            // TODO grab seconds from config
            timers.add(new Timer(timerName, 0));
        }
        return timers;
    }
}

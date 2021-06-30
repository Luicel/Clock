package com.luicel.clock.files;

import com.luicel.clock.annotations.FileDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@FileDirectory("data")
public class TimerFile extends Files {
    public TimerFile(String fileName, String directory) {
        super(fileName, directory);
    }

    // TODO change name and all instances from "timer" to "timers" and make paths depend on class name.

    public static void createTimer(String timerName) throws IOException {
        ymlConfig.createSection("timer." + timerName);
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

    // TODO Once timer model is done, replace String with Timer
    public static List<String> getTimers() {
        return new ArrayList<String>(ymlConfig.getConfigurationSection("timer").getKeys(false));
    }
}

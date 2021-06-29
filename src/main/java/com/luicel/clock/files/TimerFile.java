package com.luicel.clock.files;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TimerFile extends Files {
    private static final String directory = "data";

    public TimerFile(String fileName) {
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
            for (String timerID : ymlConfig.getConfigurationSection("timer").getKeys(false)) {
                if (timerID.equalsIgnoreCase(timerName))
                    return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}

package com.luicel.clock.files;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.TimerRunnable;
import org.bukkit.Bukkit;

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
        try {
            for (String timerName : ymlConfig.getConfigurationSection("timer").getKeys(false)) {
                int seconds = ymlConfig.getInt("timer." + timerName + ".seconds");
                Timer timer = new Timer(timerName, seconds);
                timers.add(timer);
            }
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage("[Clock] No timers detected. If none exist, please ignore this.");
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
        try {
            ymlConfig.set("timer." + timer.getName() + ".seconds", timer.getSeconds());
            ymlConfig.set("timer." + timer.getName() + ".state", timer.getState().name());
            ymlConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

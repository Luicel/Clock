package com.luicel.clock.models;

import com.luicel.clock.files.TimersFile;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("Timer")
public class Timer implements ConfigurationSerializable {
    private String name = "";
    private int seconds = 0;

    public enum State { ACTIVE, INACTIVE, PAUSED }
    private State state = State.INACTIVE;

    public Timer(String name, int seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public Timer(Map<String, Object> map) {
        this.name = map.getOrDefault("name", name).toString();
        this.seconds = Integer.parseInt(map.getOrDefault("seconds", seconds).toString());
        this.state = State.valueOf(map.getOrDefault("state", state.name()).toString());
    }

    @Override
    public Map<String, Object> serialize() {
        return new LinkedHashMap<String, Object>(){{
            put("name", name);
            put("seconds", seconds);
            put("state", state.name());
        }};
    }

    public String getName() {
        return name;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getStateAsString() {
        String stateString = "";
        if (state == Timer.State.ACTIVE)
            stateString = ChatUtils.format("&aActive");
        else if (state == Timer.State.INACTIVE)
            stateString = ChatUtils.format("&cInactive");
        else if (state == Timer.State.PAUSED)
            stateString = ChatUtils.format("&ePaused");
        return stateString;
    }

    // TODO
    public String getDisplayStatus() {
        return "";
    }

    public String getDisplayFormat() {
        // TODO grab from config to properly format
        return ChatUtils.format("&f00:00:" + seconds);
    }

    // TODO Maybe find a way to clean this up later?
    public String getTimeRemainingAsString() {
        final int SECONDS_IN_A_MINUTE = 60;
        final int SECONDS_IN_A_HOUR = 3600;
        final int SECONDS_IN_A_DAY = 86400;
        StringBuilder string = new StringBuilder();
        int cacheSeconds = seconds;

        if (cacheSeconds / SECONDS_IN_A_DAY > 0 || string.length() > 0) {
            int days = cacheSeconds / SECONDS_IN_A_DAY;
            cacheSeconds %= SECONDS_IN_A_DAY;
            string.append(String.format("%s%sd ", (days < 10) ? "0" : "", days));
        }
        if (cacheSeconds / SECONDS_IN_A_HOUR > 0 || string.length() > 0) {
            int hours = cacheSeconds / SECONDS_IN_A_HOUR;
            cacheSeconds %= SECONDS_IN_A_HOUR;
            string.append(String.format("%s%sh ", (hours < 10) ? "0" : "", hours));
        }
        if (cacheSeconds / SECONDS_IN_A_MINUTE > 0 || string.length() > 0) {
            int minutes = cacheSeconds / SECONDS_IN_A_MINUTE;
            cacheSeconds %= SECONDS_IN_A_MINUTE;
            string.append(String.format("%s%sm ", (minutes < 10) ? "0" : "", minutes));
        }
        string.append(String.format("%s%ss", (cacheSeconds < 10) ? "0" : "", cacheSeconds));

        return string.toString();
    }

    public static boolean isNameValid(String name) {
        // TODO
        return true;
    }

    public void save() {
        TimersFile.ymlConfig.set("timers." + name, this);
        try {
            TimersFile.ymlConfig.save(TimersFile.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TimersFile.getTimers().contains(this))
            TimersFile.getTimers().add(this);
    }

    public void delete() {
        TimersFile.ymlConfig.set("timers." + name, null);
        try {
            TimersFile.ymlConfig.save(TimersFile.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TimersFile.getTimers().remove(this);
    }
}

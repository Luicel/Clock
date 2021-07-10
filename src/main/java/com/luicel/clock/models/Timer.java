package com.luicel.clock.models;

import com.luicel.clock.files.TimersFile;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("Timer")
public class Timer implements ConfigurationSerializable {
    private String name = "";
    private long seconds = 0;

    public enum State { ACTIVE, INACTIVE }
    private State state = State.INACTIVE;
    public enum DisplayStatus { NONE, ACTIONBAR }
    private DisplayStatus displayStatus = DisplayStatus.NONE;

    public Timer(String name, long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public Timer(Map<String, Object> map) {
        this.name = map.getOrDefault("name", name).toString();
        this.seconds = Integer.parseInt(map.getOrDefault("seconds", seconds).toString());
        this.state = State.valueOf(map.getOrDefault("state", state.name()).toString());
        this.displayStatus = DisplayStatus.valueOf(map.getOrDefault("displayStatus", displayStatus.name()).toString());
    }

    @Override
    public Map<String, Object> serialize() {
        return new LinkedHashMap<String, Object>(){{
            put("name", name);
            put("seconds", seconds);
            put("state", state.name());
            put("displayStatus", displayStatus.name());
        }};
    }

    public String getName() {
        return name;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public String getStateAsString() {
        String stateString = "";
        switch (state) {
            case ACTIVE:
                stateString = ChatUtils.format("&aActive");
                break;
            case INACTIVE:
                stateString = ChatUtils.format("&cInactive");
                break;
        }
        return stateString;
    }

    public void setDisplayStatus(DisplayStatus displayStatus) {
        this.displayStatus = displayStatus;
    }

    public DisplayStatus getDisplayStatus() {
        return displayStatus;
    }

    public String getDisplayStatusAsString() {
        return displayStatus.name().charAt(0) + displayStatus.name().substring(1).toLowerCase();
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
        long cacheSeconds = seconds;

        if (cacheSeconds / SECONDS_IN_A_DAY > 0 || string.length() > 0) {
            long days = cacheSeconds / SECONDS_IN_A_DAY;
            cacheSeconds %= SECONDS_IN_A_DAY;
            string.append(String.format("%s%sd ", (days < 10) ? "0" : "", days));
        }
        if (cacheSeconds / SECONDS_IN_A_HOUR > 0 || string.length() > 0) {
            long hours = cacheSeconds / SECONDS_IN_A_HOUR;
            cacheSeconds %= SECONDS_IN_A_HOUR;
            string.append(String.format("%s%sh ", (hours < 10) ? "0" : "", hours));
        }
        if (cacheSeconds / SECONDS_IN_A_MINUTE > 0 || string.length() > 0) {
            long minutes = cacheSeconds / SECONDS_IN_A_MINUTE;
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

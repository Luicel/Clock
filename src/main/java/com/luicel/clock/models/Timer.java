package com.luicel.clock.models;

import com.luicel.clock.files.ConfigFile;
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

    public enum Display { NONE, ACTIONBAR }
    private Display display = Display.NONE;
    private String formatPrefix = ConfigFile.getString("formats.timer.default-prefix");
    private String formatSuffix = ConfigFile.getString("formats.timer.default-suffix");

    public Timer(String name, long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public Timer(Map<String, Object> map) {
        this.name = map.getOrDefault("name", name).toString();
        this.seconds = Integer.parseInt(map.getOrDefault("seconds", seconds).toString());
        this.state = State.valueOf(map.getOrDefault("state", state.name()).toString());
        this.display = Display.valueOf(map.getOrDefault("display", display.name()).toString());
        this.formatPrefix = map.getOrDefault("formatPrefix", formatPrefix).toString();
        this.formatSuffix = map.getOrDefault("formatSuffix", formatSuffix).toString();
    }

    @Override
    public Map<String, Object> serialize() {
        return new LinkedHashMap<String, Object>(){{
            put("name", name);
            put("seconds", seconds);
            put("state", state.name());
            put("display", display.name());
            put("formatPrefix", formatPrefix);
            put("formatSuffix", formatSuffix);
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

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Display getDisplay() {
        return display;
    }

    public String getDisplayStatusAsString() {
        return display.name().charAt(0) + display.name().substring(1).toLowerCase();
    }

    public void setFormatPrefix(String formatPrefix) {
        this.formatPrefix = formatPrefix;
    }

    public String getFormatPrefix() {
        return formatPrefix;
    }

    public void setFormatSuffix(String formatSuffix) {
        this.formatSuffix = formatSuffix;
    }

    public String getFormatSuffix() {
        return formatSuffix;
    }

    public String getFormattedDisplay() {
        return formatPrefix + getTimeRemainingAsString() + formatSuffix;
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

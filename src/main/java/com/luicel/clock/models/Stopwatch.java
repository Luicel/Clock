package com.luicel.clock.models;

import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.utils.ChatUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Stopwatch extends ClockObject {
    private String name = "";
    private long seconds = 0;
    private ClockObject.State state = ClockObject.State.INACTIVE;

    private ClockObject.Display display = ClockObject.Display.NONE;
    private String formatPrefix = ConfigFile.getString("formatting.timer-default-format-prefix");
    private String formatSuffix = ConfigFile.getString("formatting.timer-default-format-suffix");

    public Stopwatch(String name, long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public Stopwatch(Map<String, Object> map) {
        this.name = map.getOrDefault("name", name).toString();
        this.seconds = Integer.parseInt(map.getOrDefault("seconds", seconds).toString());
        this.state = ClockObject.State.valueOf(map.getOrDefault("state", state.name()).toString());
        this.display = ClockObject.Display.valueOf(map.getOrDefault("display", display.name()).toString());
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

    public void setState(ClockObject.State state) {
        this.state = state;
    }

    public ClockObject.State getState() {
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

    public void setDisplay(ClockObject.Display display) {
        this.display = display;
    }

    public ClockObject.Display getDisplay() {
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

    public String getTimeRemainingAsString() {
        // TODO
        return null;
    }

    public static boolean isNameValid(String name) {
        // TODO
        return true;
    }

    public void save() {
        // TODO
    }

    public void delete() {
        // TODO
    }
}

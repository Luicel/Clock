package com.luicel.clock.models;

import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("Stopwatch")
public class Stopwatch extends ClockObject {
    private String name = "";
    private long milliseconds = 0;
    private ClockObject.State state = ClockObject.State.INACTIVE;
    private ClockObject.Display display = ClockObject.Display.NONE;
    private String formatPrefix = ConfigFile.getString("formatting.stopwatch-default-format-prefix");
    private String formatSuffix = ConfigFile.getString("formatting.stopwatch-default-format-suffix");

    public Stopwatch(String name, long milliseconds) {
        this.name = name;
        this.milliseconds = milliseconds;
    }

    public Stopwatch(Map<String, Object> map) {
        this.name = map.getOrDefault("name", name).toString();
        this.milliseconds = Integer.parseInt(map.getOrDefault("milliseconds", milliseconds).toString());
        this.state = ClockObject.State.valueOf(map.getOrDefault("state", state.name()).toString());
        this.display = ClockObject.Display.valueOf(map.getOrDefault("display", display.name()).toString());
        this.formatPrefix = map.getOrDefault("formatPrefix", formatPrefix).toString();
        this.formatSuffix = map.getOrDefault("formatSuffix", formatSuffix).toString();
    }

    @Override
    public Map<String, Object> serialize() {
        return new LinkedHashMap<String, Object>(){{
            put("name", name);
            put("milliseconds", milliseconds);
            put("state", state.name());
            put("display", display.name());
            put("formatPrefix", formatPrefix);
            put("formatSuffix", formatSuffix);
        }};
    }

    public String getName() {
        return name;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
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

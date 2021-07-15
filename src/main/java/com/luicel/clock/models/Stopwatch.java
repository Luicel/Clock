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

    public Stopwatch(String name) {
        this.name = name;
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
        return formatPrefix + getTimeAsString() + formatSuffix;
    }

    public String getTimeAsString() {
        final int MILLISECONDS_IN_A_DAY = 86400000;
        final int MILLISECONDS_IN_A_HOUR = 3600000;
        final int MILLISECONDS_IN_A_MINUTE = 60000;
        final int MILLISECONDS_IN_A_SECOND = 1000;
        boolean useLeadingZero = ConfigFile.getBoolean("formatting.use-leading-zero-for-single-digits");
        StringBuilder string = new StringBuilder();
        long cacheMilliseconds = milliseconds;

        // Days
        if (cacheMilliseconds / MILLISECONDS_IN_A_DAY > 0 || string.length() > 0) {
            long days = cacheMilliseconds / MILLISECONDS_IN_A_DAY;
            cacheMilliseconds %= MILLISECONDS_IN_A_DAY;
            if (useLeadingZero && days < 10)
                string.append("0");
            string.append(days + "d ");
        }
        // Hours
        if (cacheMilliseconds / MILLISECONDS_IN_A_HOUR > 0 || string.length() > 0) {
            long hours = cacheMilliseconds / MILLISECONDS_IN_A_HOUR;
            cacheMilliseconds %= MILLISECONDS_IN_A_HOUR;
            if (useLeadingZero && hours < 10)
                string.append("0");
            string.append(hours + "h ");
        }
        // Minutes
        if (cacheMilliseconds / MILLISECONDS_IN_A_MINUTE > 0 || string.length() > 0) {
            long minutes = cacheMilliseconds / MILLISECONDS_IN_A_MINUTE;
            cacheMilliseconds %= MILLISECONDS_IN_A_MINUTE;
            if (useLeadingZero && minutes < 10)
                string.append("0");
            string.append(minutes + "m ");
        }
        // Seconds
        long seconds = cacheMilliseconds / MILLISECONDS_IN_A_SECOND;
        cacheMilliseconds %= MILLISECONDS_IN_A_SECOND;
        if (useLeadingZero && seconds < 10)
            string.append("0");
        string.append(seconds + ".");
        // Milliseconds
        cacheMilliseconds /= 10;
        if (useLeadingZero && cacheMilliseconds < 10)
            string.append("0");
        string.append(cacheMilliseconds + "s");

        return string.toString();
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

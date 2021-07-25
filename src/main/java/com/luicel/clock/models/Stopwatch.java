package com.luicel.clock.models;

import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.files.data.StopwatchesFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SerializableAs("Stopwatch")
public class Stopwatch extends ClockObject {
    private String name = "";
    private long milliseconds = 0;
    private ClockObject.State state = ClockObject.State.INACTIVE;
    private long currentLapMilliseconds = 0;
    private List<Long> laps = new ArrayList<>();
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
        this.currentLapMilliseconds = Integer.parseInt(map.getOrDefault("currentLapMilliseconds", currentLapMilliseconds).toString());
        // Fix for Java reading file values as an Integer
        if (map.containsKey("laps")) {
            ((List<Object>) map.get("laps")).forEach(lap -> {
                addToLaps(Long.parseLong(lap.toString()));
            });
        }
        // Reverse list as map retrieves it backwards
        Collections.reverse(laps);
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
            put("currentLapMilliseconds", currentLapMilliseconds);
            put("laps", laps);
            put("display", display.name());
            put("formatPrefix", formatPrefix);
            put("formatSuffix", formatSuffix);
        }};
    }

    public void setName(String name) {
        this.name = name;
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

    public void setDisplay(ClockObject.Display display) {
        this.display = display;
    }

    public void setCurrentLapMilliseconds(long currentLapMilliseconds) {
        this.currentLapMilliseconds = currentLapMilliseconds;
    }

    public long getCurrentLapMilliseconds() {
        return currentLapMilliseconds;
    }

    public String getCurrentLapTimeAsString() {
        return convertTimeToString(currentLapMilliseconds);
    }

    public void addToLaps(long lapMilliseconds) {
        laps.add(0, lapMilliseconds);
        while (laps.size() >= 6)
            laps.remove(5);
    }

    public List<Long> getLaps() {
        return laps;
    }

    public ClockObject.Display getDisplay() {
        return display;
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
        return convertTimeToString(milliseconds);
    }

    public static String convertTimeToString(long milliseconds) {
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
        if (cacheMilliseconds < 10)
            string.append("0");
        string.append(cacheMilliseconds + "s");

        return string.toString();
    }

    public void save() {
        StopwatchesFile.ymlConfig.set("stopwatches." + name, this);
        try {
            StopwatchesFile.ymlConfig.save(StopwatchesFile.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!StopwatchesFile.getStopwatches().contains(this))
            StopwatchesFile.getStopwatches().add(this);
    }

    public void delete() {
        StopwatchesFile.ymlConfig.set("stopwatches." + name, null);
        try {
            StopwatchesFile.ymlConfig.save(StopwatchesFile.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StopwatchesFile.getStopwatches().remove(this);
    }
}

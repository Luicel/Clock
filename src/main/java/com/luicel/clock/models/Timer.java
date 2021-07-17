package com.luicel.clock.models;

import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.files.data.TimersFile;
import org.bukkit.configuration.serialization.SerializableAs;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("Timer")
public class Timer extends ClockObject {
    private String name = "";
    private long seconds = 0;
    private ClockObject.State state = ClockObject.State.INACTIVE;
    private ClockObject.Display display = ClockObject.Display.NONE;
    private String formatPrefix = ConfigFile.getString("formatting.timer-default-format-prefix");
    private String formatSuffix = ConfigFile.getString("formatting.timer-default-format-suffix");

    public Timer(String name, long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public Timer(Map<String, Object> map) {
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

    public void setDisplay(ClockObject.Display display) {
        this.display = display;
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
        return formatPrefix + getTimeRemainingAsString() + formatSuffix;
    }

    // TODO Maybe find a way to clean this up later?
    public String getTimeRemainingAsString() {
        final int SECONDS_IN_A_DAY = 86400;
        final int SECONDS_IN_A_HOUR = 3600;
        final int SECONDS_IN_A_MINUTE = 60;
        boolean useLeadingZero = ConfigFile.getBoolean("formatting.use-leading-zero-for-single-digits");
        StringBuilder string = new StringBuilder();
        long cacheSeconds = seconds;

        // Days
        if (cacheSeconds / SECONDS_IN_A_DAY > 0 || string.length() > 0) {
            long days = cacheSeconds / SECONDS_IN_A_DAY;
            cacheSeconds %= SECONDS_IN_A_DAY;
            if (useLeadingZero && days < 10)
                string.append("0");
            string.append(days + "d ");
        }
        // Hours
        if (cacheSeconds / SECONDS_IN_A_HOUR > 0 || string.length() > 0) {
            long hours = cacheSeconds / SECONDS_IN_A_HOUR;
            cacheSeconds %= SECONDS_IN_A_HOUR;
            if (useLeadingZero && hours < 10)
                string.append("0");
            string.append(hours + "h ");
        }
        // Minutes
        if (cacheSeconds / SECONDS_IN_A_MINUTE > 0 || string.length() > 0) {
            long minutes = cacheSeconds / SECONDS_IN_A_MINUTE;
            cacheSeconds %= SECONDS_IN_A_MINUTE;
            if (useLeadingZero && minutes < 10)
                string.append("0");
            string.append(minutes + "m ");
        }
        // Seconds
        if (useLeadingZero && cacheSeconds < 10)
            string.append("0");
        string.append(cacheSeconds + "s");

        return string.toString();
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

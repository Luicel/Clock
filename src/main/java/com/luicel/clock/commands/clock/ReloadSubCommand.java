package com.luicel.clock.commands.clock;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

@HelpOrder(2)
@ArgumentsText("")
public class ReloadSubCommand extends SubCommands {
    private final Map<String, Long> cachedTimerSeconds;
    private final Map<String, Long> cachedStopwatchMilliseconds;
    private final Map<String, Long> cachedStopwatchCurrentLapMilliseconds;

    public ReloadSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);

        cachedTimerSeconds = new HashMap<>();
        cachedStopwatchMilliseconds = new HashMap<>();
        cachedStopwatchCurrentLapMilliseconds = new HashMap<>();
    }

    @Override
    protected void execute() {
        DisplayRunnable.clearDisplayingObjects();
        reload();
        sendMessage(PrefixUtils.getClockPrefix() + "Successfully reloaded!");
    }

    private void reload() {
        ConfigFile.reload();

        cacheSeconds();

        TimersFile.reload();
        StopwatchesFile.reload();

        applyCachedSeconds();
    }

    private void cacheSeconds() {
        cachedTimerSeconds.clear();
        if (ConfigFile.getBoolean("mechanics.use-cached-timer-seconds-on-reload")) {
            TimersFile.getTimers().forEach(timer ->
                cachedTimerSeconds.put(timer.getName(), timer.getSeconds()
            ));
        }

        cachedStopwatchMilliseconds.clear();
        StopwatchesFile.getStopwatches().forEach(stopwatch ->
            cachedStopwatchMilliseconds.put(stopwatch.getName(), stopwatch.getMilliseconds())
        );
        cachedStopwatchCurrentLapMilliseconds.clear();
        StopwatchesFile.getStopwatches().forEach(stopwatch ->
                cachedStopwatchCurrentLapMilliseconds.put(stopwatch.getName(), stopwatch.getCurrentLapMilliseconds())
        );
    }

    private void applyCachedSeconds() {
        if (!cachedTimerSeconds.isEmpty()) {
            TimersFile.getTimers().forEach(timer ->
                    timer.setSeconds(cachedTimerSeconds.get(timer.getName()))
            );
            TimersFile.getTimers().forEach(Timer::save);
        }

        StopwatchesFile.getStopwatches().forEach(stopwatch ->
            stopwatch.setMilliseconds(cachedStopwatchMilliseconds.get(stopwatch.getName()))
        );
        StopwatchesFile.getStopwatches().forEach(stopwatch ->
            stopwatch.setCurrentLapMilliseconds(cachedStopwatchCurrentLapMilliseconds.get(stopwatch.getName()))
        );
        StopwatchesFile.getStopwatches().forEach(Stopwatch::save);
    }
}

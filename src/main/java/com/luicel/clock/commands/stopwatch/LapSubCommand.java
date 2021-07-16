package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@HelpOrder(7)
@ArgumentsText("<name>")
public class LapSubCommand extends SubCommands {
    public LapSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        if (getArgs().length < 2) {
            printSyntaxMessage(this);
        } else {
            Stopwatch stopwatch = StopwatchesFile.getStopwatch(getArgs()[1]);
            if (stopwatch == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No stopwatch with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                lapStopwatch(stopwatch);
            }
        }
    }

    private void lapStopwatch(Stopwatch stopwatch) {
        long cacheCurrentLapMilliseconds = stopwatch.getCurrentLapMilliseconds();
        stopwatch.addToLaps(stopwatch.getCurrentLapMilliseconds());
        stopwatch.setCurrentLapMilliseconds(0);
        stopwatch.save();
        sendMessage(PrefixUtils.getStopwatchPrefix() + "Lapped stopwatch '&f" + stopwatch.getName() + "&7' for &f" +
                Stopwatch.convertTimeToString(cacheCurrentLapMilliseconds) + "&7.");
    }
}

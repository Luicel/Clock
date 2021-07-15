package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(6)
@ArgumentsText("<name>")
public class StopSubCommand extends SubCommands {
    public StopSubCommand(CommandSender sender, String label, String[] args) {
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
                tryToStopStopwatch(stopwatch);
            }
        }
    }

    private void tryToStopStopwatch(Stopwatch stopwatch) {
        if (stopwatch.getState() == ClockObject.State.ACTIVE) {
            stopwatch.setState(ClockObject.State.INACTIVE);
            stopwatch.save();
            sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' has been &cstopped&7. " +
                    "Time: &f" + stopwatch.getTimeAsString() + "&7.");
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' is not active!");
        }
    }
}
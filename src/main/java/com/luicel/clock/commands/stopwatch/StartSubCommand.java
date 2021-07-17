package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.runnables.StopwatchRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(5)
@ArgumentsText("<name>")
public class StartSubCommand extends SubCommands {
    public StartSubCommand(CommandSender sender, String label, String[] args) {
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
                tryToStartStopwatch(stopwatch);
            }
        }
    }

    private void tryToStartStopwatch(Stopwatch stopwatch) {
        if (stopwatch.getState() == ClockObject.State.INACTIVE) {
            stopwatch.setState(ClockObject.State.ACTIVE);
            stopwatch.save();
            new StopwatchRunnable(stopwatch).runTaskTimer(Clock.getInstance(), 0, 1);
            sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' has been &astarted&7. " +
                    "Time: &f" + stopwatch.getTimeAsString() + " &7(" + stopwatch.getCurrentLapTimeAsString() + ").");
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' is already active!");
        }
    }
}

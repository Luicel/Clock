package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(8)
@ArgumentsText("<name>")
public class ResetSubCommand extends SubCommands {
    public ResetSubCommand(CommandSender sender, String label, String[] args) {
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
                resetStopwatch(stopwatch);
            }
        }
    }

    private void resetStopwatch(Stopwatch stopwatch) {
        stopwatch.setMilliseconds(0);
        stopwatch.setCurrentLapMilliseconds(0);
        stopwatch.getLaps().clear();
        stopwatch.save();
        sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' has been reset.");
    }
}

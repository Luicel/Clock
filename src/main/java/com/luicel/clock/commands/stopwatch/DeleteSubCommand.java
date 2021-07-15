package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(2)
@ArgumentsText("<name>")
public class DeleteSubCommand extends SubCommands {
    public DeleteSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length < 2) {
            printSyntaxMessage(this);
        } else {
            Stopwatch stopwatch = StopwatchesFile.getStopwatch(getArgs()[1]);
            if (stopwatch == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No stopwatch with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                deleteStopwatch(stopwatch);
            }
        }
    }

    private void deleteStopwatch(Stopwatch stopwatch) {
        stopwatch.delete();
        sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' successfully deleted!");
    }
}

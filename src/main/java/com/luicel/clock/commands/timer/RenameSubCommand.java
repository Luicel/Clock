package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(3)
@ArgumentsText("<name> <text>")
public class RenameSubCommand extends SubCommands {
    public RenameSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length < 2) {
            printSyntaxMessage(this);
        } else {
            Timer timer = TimersFile.getTimer(getArgs()[1]);
            if (timer == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                tryToRenameStopwatch(timer);
            }
        }
    }

    private void tryToRenameStopwatch(Timer timer) {
        if (StopwatchesFile.doesStopwatchWithNameExist(getArgs()[2])) {
            sendMessage(PrefixUtils.getErrorPrefix() + "A timer with the name '&f" + getArgs()[1] +
                    "&7' already exists!");
        } else {
            timer.delete();
            timer.setName(getArgs()[2]);
            timer.save();
            sendMessage(PrefixUtils.getTimerPrefix() + "Timer successfully renamed to '&f" + getArgs()[2] + "&7'.");
        }
    }
}


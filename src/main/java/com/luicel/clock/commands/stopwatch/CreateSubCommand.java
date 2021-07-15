package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(1)
@ArgumentsText("<name>")
public class CreateSubCommand extends SubCommands {
    public CreateSubCommand(CommandSender sender, String label, String[] args) {
    super(sender, label, args);
}

    @Override
    protected void execute() {
        if (getArgs().length < 2) {
            printSyntaxMessage(this);
        } else {
            if (StopwatchesFile.doesStopwatchWithNameExist(getArgs()[1])) {
                sendMessage(PrefixUtils.getErrorPrefix() + "A stopwatch with the name '&f" + getArgs()[1] + "&7' already exists!");
            } else if (!Stopwatch.isNameValid(getArgs()[1])) {
                sendMessage(PrefixUtils.getErrorPrefix() + "That name is invalid. Please only use alphanumeric characters, hyphens, and underscores.");
            } else {
                createStopwatch();
            }
        }
    }

    private void createStopwatch() {
        Stopwatch stopwatch = new Stopwatch(getArgs()[1]);
        stopwatch.save();
        sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' successfully created!");
    }
}

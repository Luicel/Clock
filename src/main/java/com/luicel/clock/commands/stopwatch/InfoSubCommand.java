package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(3)
@ArgumentsText("<name>")
public class InfoSubCommand extends SubCommands {
    public InfoSubCommand(CommandSender sender, String label, String[] args) {
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
                printStopwatchInfo(stopwatch);
            }
        }
    }

    private void printStopwatchInfo(Stopwatch stopwatch) {
        sendMessage(PrefixUtils.getHeaderColoredLine("&d") + "&d&lSTOPWATCH INFO:");
        sendMessage("&7Name: &f" + stopwatch.getName());
        sendMessage("&7Time: &f" + stopwatch.getTimeAsString());
        sendMessage("&7State: &f" + ChatUtils.getStateAsString(stopwatch.getState()));
        // TODO display most recent lap (hoverable that shows last 5, if more than 1 exists)
        sendMessage("&7Last Lap: &fN/A &7(Hover for More)");
        sendMessage("");
        sendMessage("&7Display: &f" + ChatUtils.getDisplayAsString(stopwatch.getDisplay()));
        sendMessage("&7Format Prefix: &7'" + stopwatch.getFormatPrefix() + "&7'");
        sendMessage("&7Format Suffix: &7'" + stopwatch.getFormatSuffix() + "&7'");
    }
}


package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(10)
@ArgumentsText("<name> <prefix/suffix> <text/clear>")
public class FormatSubCommand extends SubCommands {
    public FormatSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length < 4) {
            printSyntaxMessage(this);
        } else {
            Stopwatch stopwatch = StopwatchesFile.getStopwatch(getArgs()[1]);
            if (stopwatch == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No stopwatch with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                if (getArgs()[3].equalsIgnoreCase("clear")) {
                    clearFormat(stopwatch);
                } else {
                    format(stopwatch);
                }
            }
        }
    }

    private void clearFormat(Stopwatch stopwatch) {
        switch (getArgs()[2].toLowerCase()) {
            case "prefix": {
                stopwatch.setFormatPrefix("");
                stopwatch.save();
                sendMessage(PrefixUtils.getStopwatchPrefix() + "Cleared the prefix format of stopwatch '&f" +
                        stopwatch.getName() + "&7'.");
                break;
            }
            case "suffix": {
                stopwatch.setFormatSuffix("");
                stopwatch.save();
                sendMessage(PrefixUtils.getStopwatchPrefix() + "Cleared the suffix format of stopwatch '&f" +
                        stopwatch.getName() + "&7'.");
                break;
            }
            default:
                printSyntaxMessage(this);
                break;
        }
    }

    private void format(Stopwatch stopwatch) {
        StringBuilder string = new StringBuilder();
        for (int i = 3; i < getArgs().length; i++) {
            string.append(getArgs()[i]);
            if (i < getArgs().length - 1) string.append(" ");
        }

        switch (getArgs()[2].toLowerCase()) {
            case "prefix": {
                stopwatch.setFormatPrefix(string.toString());
                stopwatch.save();
                sendMessage(PrefixUtils.getStopwatchPrefix() +
                        "Set the prefix format of stopwatch '&f" + stopwatch.getName() + "&7' to '&f" + string + "&7'.");
                break;
            }
            case "suffix": {
                stopwatch.setFormatSuffix(string.toString());
                stopwatch.save();
                sendMessage(PrefixUtils.getStopwatchPrefix() +
                        "Set the suffix format of stopwatch '&f" + stopwatch.getName() + "&7' to '&f" + string + "&7'.");
                break;
            }
            default:
                printSyntaxMessage(this);
                break;
        }
    }
}


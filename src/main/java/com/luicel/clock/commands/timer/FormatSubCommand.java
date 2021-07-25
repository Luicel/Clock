package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.TimersFile;
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
            Timer timer = TimersFile.getTimer(getArgs()[1]);
            if (timer == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                if (getArgs()[3].equalsIgnoreCase("clear")) {
                    clearFormat(timer);
                } else {
                    format(timer);
                }
            }
        }
    }

    private void clearFormat(Timer timer) {
        switch (getArgs()[2].toLowerCase()) {
            case "prefix": {
                timer.setFormatPrefix("");
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() + "Cleared the prefix format of timer '&f" + timer.getName() + "&7'.");
                break;
            }
            case "suffix": {
                timer.setFormatSuffix("");
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() + "Cleared the suffix format of timer '&f" + timer.getName() + "&7'.");
                break;
            }
            default:
                printSyntaxMessage(this);
                break;
        }
    }

    private void format(Timer timer) {
        StringBuilder string = new StringBuilder();
        for (int i = 3; i < getArgs().length; i++) {
            string.append(getArgs()[i]);
            if (i < getArgs().length - 1) string.append(" ");
        }

        switch (getArgs()[2].toLowerCase()) {
            case "prefix": {
                timer.setFormatPrefix(string.toString());
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() +
                        "Set the prefix format of timer '&f" + timer.getName() + "&7' to '&f" + string + "&7'.");
                break;
            }
            case "suffix": {
                timer.setFormatSuffix(string.toString());
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() +
                        "Set the suffix format of timer '&f" + timer.getName() + "&7' to '&f" + string + "&7'.");
                break;
            }
            default:
                printSyntaxMessage(this);
                break;
        }
    }
}

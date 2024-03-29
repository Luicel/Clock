package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(10)
@ArgumentsText("<name> <none/actionbar>")
public class DisplaySubCommand extends SubCommands {
    public DisplaySubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length < 3) {
            printSyntaxMessage(this);
        } else {
            Stopwatch stopwatch = StopwatchesFile.getStopwatch(getArgs()[1]);
            if (stopwatch == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No stopwatch with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                updateDisplay(stopwatch);
            }
        }
    }

    private void updateDisplay(Stopwatch stopwatch) {
        switch (getArgs()[2].toLowerCase()) {
            case "none": {
                if (DisplayRunnable.isBeingDisplayed(stopwatch)) {
                    DisplayRunnable.setActionbarDisplayingObject(null);
                    stopwatch.setDisplay(ClockObject.Display.NONE);
                    stopwatch.save();
                    sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' is no longer being displayed anywhere.");
                } else {
                    sendMessage(PrefixUtils.getErrorPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' is already not being displayed anywhere.");
                }
                break;
            }
            case "actionbar": {
                if (DisplayRunnable.getActionbarDisplayingObject() != stopwatch) {
                    DisplayRunnable.setActionbarDisplayingObject(stopwatch);
                    stopwatch.setDisplay(ClockObject.Display.ACTIONBAR);
                    stopwatch.save();
                    sendMessage(PrefixUtils.getStopwatchPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' is now being displayed in the &factionbar&7.");
                } else {
                    sendMessage(PrefixUtils.getErrorPrefix() + "Stopwatch '&f" + stopwatch.getName() + "&7' is already being displayed in the &factionbar&7.");
                }
                break;
            }
            default:
                printSyntaxMessage(this);
                break;
        }
    }
}

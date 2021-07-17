package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(8)
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
            Timer timer = TimersFile.getTimer(getArgs()[1]);
            if (timer == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                updateDisplay(timer);
            }
        }
    }

    private void updateDisplay(Timer timer) {
        switch (getArgs()[2].toLowerCase()) {
            case "none": {
                if (DisplayRunnable.isBeingDisplayed(timer)) {
                    DisplayRunnable.setActionbarDisplayingObject(null);
                    timer.setDisplay(ClockObject.Display.NONE);
                    timer.save();
                    sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' is no longer being displayed anywhere.");
                } else {
                    sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is already not being displayed anywhere.");
                }
                break;
            }
            case "actionbar": {
                if (DisplayRunnable.getActionbarDisplayingObject() != timer) {
                    DisplayRunnable.setActionbarDisplayingObject(timer);
                    timer.setDisplay(ClockObject.Display.ACTIONBAR);
                    timer.save();
                    sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' is now being displayed in the &factionbar&7.");
                } else {
                    sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is already being displayed in the &factionbar&7");
                }
                break;
            }
            default:
                printSyntaxMessage(this);
                break;
        }
    }
}

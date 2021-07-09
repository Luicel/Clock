package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(6)
@ArgumentsText("<name>")
public class StopSubCommand extends SubCommands {
    public StopSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        Timer timer = TimersFile.getTimer(getArgs()[1]);
        if (timer == null) {
            sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
        } else {
            tryToStopTimer(timer);
        }
    }

    private void tryToStopTimer(Timer timer) {
        if (timer.getState() == Timer.State.ACTIVE) {
            timer.setState(Timer.State.INACTIVE);
            timer.save();
            sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' has been &cstopped&7. " +
                    "Time remaining: &f" + timer.getTimeRemainingAsString() + "&7.");
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is not active!");
        }
    }
}

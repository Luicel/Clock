package com.luicel.clock.commands.timer;

import com.luicel.clock.Clock;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.TimerRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(5)
@ArgumentsText("<name>")
public class StartSubCommand extends SubCommands {
    public StartSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        if (getArgs().length < 2) {
            printSyntaxMessage(this);
        } else {
            Timer timer = TimersFile.getTimer(getArgs()[1]);
            if (timer == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                tryToStartTimer(timer);
            }
        }
    }

    private void tryToStartTimer(Timer timer) {
        if (timer.getSeconds() > 0) {
            if (timer.getState() == ClockObject.State.INACTIVE) {
                timer.setState(ClockObject.State.ACTIVE);
                timer.save();
                new TimerRunnable(timer).runTaskTimer(Clock.getInstance(), 0, 20);
                sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' has been &astarted&7. " +
                        "Time remaining: &f" + timer.getTimeRemainingAsString() + "&7.");
            } else {
                sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is already active!");
            }
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' has 0 seconds remaining, thus it can not be started.");
        }
    }
}

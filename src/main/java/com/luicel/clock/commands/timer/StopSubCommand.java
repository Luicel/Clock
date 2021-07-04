package com.luicel.clock.commands.timer;

import com.luicel.clock.Clock;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.TimerFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.TimerRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(5)
@ArgumentsText("<name>")
public class StopSubCommand extends SubCommands {
    public StopSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        String timerName = getArgs()[1];
        if (TimerFile.doesTimerExist(timerName)) {
            Timer timer = TimerFile.getTimer(timerName);
            tryToStopTimer(timer);
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
        }
    }

    private void tryToStopTimer(Timer timer) {
        switch (timer.getState()) {
            case ACTIVE: {
                timer.setState(Timer.State.INACTIVE);
                sendMessage(Timer.getPrefix() + "Timer '&f" + timer.getName() + "&7' has been &cstopped&7.");
                break;
            }
            case PAUSED:
                sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is currently paused!");
                break;
            case INACTIVE:
                sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is not active!");
                break;
        }
    }
}

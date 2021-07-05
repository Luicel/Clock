package com.luicel.clock.commands.timer;

import com.luicel.clock.Clock;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.TimerRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(4)
@ArgumentsText("<name>")
public class StartSubCommand extends SubCommands {
    public StartSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        String timerName = getArgs()[1];
        if (TimersFile.doesTimerExist(timerName)) {
            Timer timer = TimersFile.getTimer(timerName);
            tryToStartTimer(timer);
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
        }
    }

    private void tryToStartTimer(Timer timer) {
        if (timer.getSeconds() > 0) {
            switch (timer.getState()) {
                case ACTIVE:
                    sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is already active!");
                    break;
                case PAUSED:
                    sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' is currently paused!");
                    break;
                case INACTIVE: {
                    timer.setState(Timer.State.ACTIVE);
                    new TimerRunnable(timer).runTaskTimer(Clock.getInstance(), 0, 20);
                    sendMessage(Timer.getPrefix() + "Timer '&f" + timer.getName() + "&7' has been &astarted&7.");
                    break;
                }
            }
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Timer '&f" + timer.getName() + "&7' has 0 seconds remaining, thus it can not be started&7.");
        }
    }
}

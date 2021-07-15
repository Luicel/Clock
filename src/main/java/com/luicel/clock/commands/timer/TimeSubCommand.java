package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(7)
@ArgumentsText("<name> <add/remove/set> <seconds>")
public class TimeSubCommand extends SubCommands {
    public TimeSubCommand(CommandSender sender, String label, String[] args) {
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
                try {
                    switch (getArgs()[2].toLowerCase()) {
                        case "add":
                            addSecondsToTimer(timer, Long.parseLong(getArgs()[3]));
                            break;
                        case "remove":
                            removeSecondsFromTimer(timer, Long.parseLong(getArgs()[3]));
                            break;
                        case "set":
                            setSecondsOfTimer(timer, Long.parseLong(getArgs()[3]));
                            break;
                        default:
                            printSyntaxMessage(this);
                            break;
                    }
                } catch (NumberFormatException e) {
                    sendMessage(PrefixUtils.getErrorPrefix() + "Invalid number '&f" + getArgs()[3] + "&7'. Please use an integer!");
                }
            }
        }
    }

    private void addSecondsToTimer(Timer timer, long seconds) {
        if (seconds >= 0) {
            //noinspection ConstantConditions
            if (timer.getSeconds() + seconds <= Long.MAX_VALUE) {
                timer.setSeconds(timer.getSeconds() + seconds);
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() +
                        String.format("Added &f%s &7second" + ((seconds != 1) ? "s" : "") + " to timer '&f%s&7'.", seconds, timer.getName()));
            } else {
                sendMessage(PrefixUtils.getErrorPrefix() +
                        String.format("Adding &f%s &7second" + ((seconds != 1) ? "s" : "") + " to '&f%s&7' exceeds the long limit (%s).", seconds, timer.getName(), Long.MAX_VALUE));
            }
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Can't add negative seconds to a timer.");
        }
    }

    private void removeSecondsFromTimer(Timer timer, long seconds) {
        if (seconds >= 0) {
            if (timer.getSeconds() - seconds >= 0) {
                timer.setSeconds(timer.getSeconds() - seconds);
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() +
                        String.format("Removed &f%s &7second" + ((seconds != 1) ? "s" : "") + " from timer '&f%s&7'.", seconds, timer.getName()));
            } else {
                sendMessage(PrefixUtils.getErrorPrefix() +
                        String.format("Removing &f%s &7second" + ((seconds != 1) ? "s" : "") + " from '&f%s&7' sets the timer's remaining time to below 0.", seconds, timer.getName()));
            }
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Can't remove negative seconds from a timer.");
        }
    }

    private void setSecondsOfTimer(Timer timer, long seconds) {
        if (seconds >= 0) {
            timer.setSeconds(seconds);
            timer.save();
            sendMessage(PrefixUtils.getTimerPrefix() +
                    String.format("Set the time remaining of timer '&f%s&7' to &f%s &7second" + ((seconds != 1) ? "s" : "") + ".", timer.getName(), seconds));
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "You can not set a timer's remaining time to below 0.");
        }
    }
}

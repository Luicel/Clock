package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
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
                            addSecondsToTimer(timer, Integer.parseInt(getArgs()[3]));
                            break;
                        case "remove":
                            removeSecondsFromTimer(timer, Integer.parseInt(getArgs()[3]));
                            break;
                        case "set":
                            setSecondsOfTimer(timer, Integer.parseInt(getArgs()[3]));
                            break;
                        default:
                            printSyntaxMessage(this);
                            break;
                    }
                } catch (NumberFormatException e) {
                    sendMessage(PrefixUtils.getErrorPrefix() + "Invalid integer '&f" + getArgs()[3] + "&7'.");
                }
            }
        }
    }

    private void addSecondsToTimer(Timer timer, int seconds) {
        if (seconds >= 0) {
            if ((long)timer.getSeconds() + (long)seconds >= Integer.MAX_VALUE) {
                timer.setSeconds(timer.getSeconds() + seconds);
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() +
                        String.format("Added %s second" + ((seconds != 1) ? "s" : "") + " to timer '&f%s&7'.", seconds, timer.getName()));
            } else {
                sendMessage(PrefixUtils.getErrorPrefix() +
                        String.format("Adding %s second" + ((seconds != 1) ? "s" : "") + " to '&f%s&7' exceeds the integer limit (%s).", seconds, timer.getName(), Integer.MAX_VALUE));
            }
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Can't add negative seconds to a timer.");
        }
    }

    private void removeSecondsFromTimer(Timer timer, int seconds) {
        if (seconds >= 0) {
            if (timer.getSeconds() - seconds >= 0) {
                timer.setSeconds(timer.getSeconds() - seconds);
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() +
                        String.format("Removed %s second" + ((seconds != 1) ? "s" : "") + " to timer '&f%s&7'.", seconds, timer.getName()));
            } else {
                sendMessage(PrefixUtils.getErrorPrefix() +
                        String.format("Removing %s second" + ((seconds != 1) ? "s" : "") + " from '&f%s&7' sets the timer's remaining time to below 0.", seconds, timer.getName()));
            }
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "Can't remove negative seconds from a timer.");
        }
    }

    private void setSecondsOfTimer(Timer timer, int seconds) {
        if (seconds >= 0) {
            timer.setSeconds(seconds);
            timer.save();
            sendMessage(PrefixUtils.getTimerPrefix() +
                    String.format("Set timer '&f%s&7' to %s second" + ((seconds != 1) ? "s" : "") + ".", timer.getName(), seconds));
        } else {
            sendMessage(PrefixUtils.getErrorPrefix() + "You can not set a timer's remaining time to below 0.");
        }
    }
}

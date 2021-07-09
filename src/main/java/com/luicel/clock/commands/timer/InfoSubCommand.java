package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(3)
@ArgumentsText("<name>")
public class InfoSubCommand extends SubCommands {
    public InfoSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length <= 1) {
            printSyntaxMessage(this);
        } else {
            Timer timer = TimersFile.getTimer(getArgs()[1]);
            if (timer == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                printTimerInfo(timer);
            }
        }
    }

    private void printTimerInfo(Timer timer) {
        sendMessage(PrefixUtils.getHeaderColoredLine("&b") + "&b&lTIMER INFO:");
        sendMessage("&7Name: &f" + timer.getName());
        sendMessage("&7Time Remaining: &f" + timer.getTimeRemainingAsString());
        sendMessage("&7State: &f" + timer.getState().name());
        sendMessage("");
        sendMessage("&7Display Status: &f" + timer.getDisplayFormat());
        sendMessage("&7Display Format: &f"); // TODO (prefix + format + suffix)
    }
}

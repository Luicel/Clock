package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.commands.annotations.ArgumentsText;
import com.luicel.clock.commands.annotations.HelpOrder;
import com.luicel.clock.files.TimerFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.IOException;

@HelpOrder(1)
@ArgumentsText("<name>")
public class CreateSubCommand extends SubCommands {
    public CreateSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length <= 1) {
            printSyntaxMessage(this);
        } else {
            String timerName = getArgs()[1];
            if (tryToCreateTimer(timerName)) {
                sendMessage(getPrefix() + "Timer '&f" + timerName + "&7' successfully created!");
            }
        }
    }

    private boolean tryToCreateTimer(String timerName) {
        if (TimerFile.doesTimerExist(timerName)) {
            sendMessage(PrefixUtils.getErrorPrefix() + "A timer with the name '&f" + timerName + "&7' already exists!");
            return false;
        }
        if (!Timer.isNameValid(timerName)) {
            sendMessage(PrefixUtils.getErrorPrefix() + "That name is invalid. Please only use alphanumeric characters, hyphens, and underscores.");
            return false;
        }
        try {
            TimerFile.createTimer(timerName);
        } catch (IOException e) {
            sendMessage(PrefixUtils.getInternalErrorPrefix() + "Check console for more information.");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

import java.io.IOException;

@HelpOrder(1)
@ArgumentsText("<name> <seconds>")
public class CreateSubCommand extends SubCommands {
    public CreateSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length <= 2) {
            printSyntaxMessage(this);
        } else {
            try {
                Timer timer = new Timer(getArgs()[1], Integer.parseInt(getArgs()[2]));
                if (tryToCreateTimer(timer)) {
                    sendMessage(Timer.getPrefix() + "Timer '&f" + timer.getName() + "&7' successfully created!");
                }
            } catch (NumberFormatException e) {
                sendMessage(PrefixUtils.getErrorPrefix() + "Invalid integer '&f" + getArgs()[2] + "&7'.");
            }
        }
    }

    private boolean tryToCreateTimer(Timer timer) {
        if (TimersFile.doesTimerExist(timer.getName())) {
            sendMessage(PrefixUtils.getErrorPrefix() + "A timer with the name '&f" + timer.getName() + "&7' already exists!");
            return false;
        }
        if (!Timer.isNameValid(timer.getName())) {
            sendMessage(PrefixUtils.getErrorPrefix() + "That name is invalid. Please only use alphanumeric characters, hyphens, and underscores.");
            return false;
        }
        try {
            TimersFile.createTimer(timer);
        } catch (IOException e) {
            sendMessage(PrefixUtils.getInternalErrorPrefix() + "Check console for more information.");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

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
            Timer timer = TimersFile.getTimer(getArgs()[1]);
            if (timer == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "A timer with the name '&f" + getArgs()[1] + "&7' already exists!");
            } else if (!Timer.isNameValid(timer.getName())) {
                sendMessage(PrefixUtils.getErrorPrefix() + "That name is invalid. Please only use alphanumeric characters, hyphens, and underscores.");
            } else {
                timer.save();
                sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' successfully created!");
            }
        }
    }
}

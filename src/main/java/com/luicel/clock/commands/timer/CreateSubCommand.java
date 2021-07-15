package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(1)
@ArgumentsText("<name> <seconds>")
public class CreateSubCommand extends SubCommands {
    public CreateSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length < 3) {
            printSyntaxMessage(this);
        } else {
            if (TimersFile.doesTimerWithNameExist(getArgs()[1])) {
                sendMessage(PrefixUtils.getErrorPrefix() + "A timer with the name '&f" + getArgs()[1] + "&7' already exists!");
            } else if (!Timer.isNameValid(getArgs()[1])) {
                sendMessage(PrefixUtils.getErrorPrefix() + "That name is invalid. Please only use alphanumeric characters, hyphens, and underscores.");
            } else {
                createTimer();
            }
        }
    }

    private void createTimer() {
        try {
            Timer timer = new Timer(getArgs()[1], Long.parseLong(getArgs()[2]));
            timer.save();
            sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' successfully created!");
        } catch (NumberFormatException e) {
            sendMessage(PrefixUtils.getErrorPrefix() + "Invalid number '&f" + getArgs()[2] + "&7'. Please use an integer!");
        }
    }
}

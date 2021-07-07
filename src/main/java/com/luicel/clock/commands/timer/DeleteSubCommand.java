package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

import java.io.IOException;

@HelpOrder(2)
@ArgumentsText("<name>")
public class DeleteSubCommand extends SubCommands {
    public DeleteSubCommand(CommandSender sender, String label, String[] args) {
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
                timer.delete();
                sendMessage(PrefixUtils.getTimerPrefix() + "Timer '&f" + timer.getName() + "&7' successfully deleted!");
            }
        }
    }
}

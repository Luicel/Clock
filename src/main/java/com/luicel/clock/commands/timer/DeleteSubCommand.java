package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.commands.annotations.ArgumentsText;
import com.luicel.clock.commands.annotations.HelpOrder;
import com.luicel.clock.files.TimerFile;
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
            String timerName = getArgs()[1];
            if (tryToDeleteTimer(timerName)) {
                sendMessage(getPrefix() + "Timer '&f" + timerName + "&7' successfully deleted!");
            }
        }
    }

    private boolean tryToDeleteTimer(String timerName) {
        if (!TimerFile.doesTimerExist(timerName)) {
            sendMessage(PrefixUtils.getErrorPrefix() + "No timer with the name '&f" + timerName + "&7' exists!");
            return false;
        }
        try {
            TimerFile.deleteTimer(timerName);
        } catch (IOException e) {
            sendMessage(PrefixUtils.getInternalErrorPrefix() + "Check console for more information.");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

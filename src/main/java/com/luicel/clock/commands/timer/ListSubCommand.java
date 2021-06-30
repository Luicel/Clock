package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimerFile;
import org.bukkit.command.CommandSender;

import java.util.List;

@HelpOrder(3)
@ArgumentsText("")
public class ListSubCommand extends SubCommands {
    public ListSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        printTimers();
    }

    private void printTimers() {
        List<String> timers = TimerFile.getTimers();

        sendMessage(String.format("&b&lTIMERS: &f(%s)", timers.size()));
        for (String timerName : TimerFile.getTimers()) {
            sendMessage("&7- " + timerName);
        }
    }
}

package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TimerCommand extends Commands {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName))
            executeCommand(sender, command, label, args);
        return true;
    }
}

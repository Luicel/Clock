package com.luicel.clock.commands.clock;

import com.luicel.clock.commands.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClockCommand extends Commands {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName))
            if (args.length == 0 || (args[0].equalsIgnoreCase("help"))) {
                printHelpMessage((Player) sender);
            } else {
                executeCommand(sender, command, label, args);
            }
        return true;
    }
}

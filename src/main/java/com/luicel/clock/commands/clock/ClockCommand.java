package com.luicel.clock.commands.clock;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.commands.Commands;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.commands.timer.TimerCommand;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return ChatUtils.getElementsWithPrefix(args[0], new ArrayList<>(getSubCommandClasses().keySet()));
        } else {
            // No more potential args
            return new ArrayList<>();
        }
    }
}

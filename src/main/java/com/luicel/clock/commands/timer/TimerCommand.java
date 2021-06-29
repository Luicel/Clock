package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.Commands;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.commands.annotations.ArgumentsText;
import com.luicel.clock.commands.annotations.HelpOrder;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TimerCommand extends Commands {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            if (args.length == 0 || (args[0].equalsIgnoreCase("help"))) {
                printHelpMessage((Player) sender);
            } else {
                switch (args[0].toLowerCase()) {
                    case "create":
                        new CreateSubCommand(sender, label, args);
                        break;
                    case "delete":
                        new DeleteSubCommand(sender, label, args);
                        break;
                    case "start":
                        new StartSubCommand(sender, label, args);
                        break;
                    case "stop":
                        new StopSubCommand(sender, label, args);
                        break;
                }
            }
        }
        return true;
    }
}

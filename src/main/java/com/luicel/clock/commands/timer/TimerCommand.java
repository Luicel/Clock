package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.Commands;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TimerCommand extends Commands {
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
            Class<? extends SubCommands> subCommandClass = getSubCommandClasses().get(args[0]);
            if (subCommandClass != null) {
                String[] textArguments = subCommandClass.getAnnotation(ArgumentsText.class).value().split(" ");
                try {
                    String argText = textArguments[args.length - 2].toLowerCase();
                    switch (argText) {
                        case "<name>": {
                            if (!args[0].equalsIgnoreCase("create")) {
                                List<String> timerNames = new ArrayList<>();
                                TimersFile.getTimers().forEach(timer -> timerNames.add(timer.getName()));
                                return ChatUtils.getElementsWithPrefix(args[args.length - 1], timerNames);
                            }
                            break;
                        }
                        case "<seconds>":
                        case "<text>":
                        case "<text/clear>":
                            // Arg has no auto completion
                            return new ArrayList<>();
                        default: {
                            List<String> list = Arrays.asList(argText.replace("<", "")
                                    .replace(">", "").split("/"));
                            return ChatUtils.getElementsWithPrefix(args[args.length - 1], list);
                        }

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // No more potential args
                    return new ArrayList<>();
                }
            } else {
                // Invalid sub command name
                return new ArrayList<>();
            }
        }
        // Fallback return
        return new ArrayList<>();
    }
}

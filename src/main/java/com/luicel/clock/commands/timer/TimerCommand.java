package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.Permission;
import com.luicel.clock.commands.Commands;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PermissionUtils;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TimerCommand extends Commands {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            if (args.length == 0 || (args[0].equalsIgnoreCase("help"))) {
                printHelpMessage((Player) sender);
            } else {
                executeCommand(sender, command, label, args);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Map<String, Class<? extends SubCommands>> subCommandsWithPermission = new HashMap<>();
        getSubCommandClasses().forEach((subCommandName, subCommandClass) -> {
            if (PermissionUtils.doesPlayerHavePermission((Player) sender, commandName, subCommandName))
                subCommandsWithPermission.put(subCommandName, subCommandClass);
        });

        if (args.length == 1) {
            return ChatUtils.getElementsWithPrefix(args[0],
                    new ArrayList<>(subCommandsWithPermission.keySet()));
        } else {
            Class<? extends SubCommands> subCommandClass = getSubCommandClasses().get(args[0]);
            if (subCommandClass != null && subCommandsWithPermission.containsValue(subCommandClass)) {
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

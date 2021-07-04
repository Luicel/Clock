package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.Commands;
import com.luicel.clock.commands.SubCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TimerCommand extends Commands {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            if (args.length == 0 || (args[0].equalsIgnoreCase("help"))) {
                printHelpMessage((Player) sender);
            } else {
                try {
                    Class<? extends SubCommands> subCommandClass = subCommandClasses.get(args[0]);
                    Constructor<?> constructor = subCommandClass.getConstructor(CommandSender.class, String.class, String[].class);
                    constructor.newInstance(sender, label, args);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}

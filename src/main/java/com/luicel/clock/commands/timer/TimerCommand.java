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
        if (command.getName().equalsIgnoreCase(commandName))
            executeCommand(sender, command, label, args);
        return true;
    }
}

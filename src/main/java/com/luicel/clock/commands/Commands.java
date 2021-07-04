package com.luicel.clock.commands;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Commands implements CommandExecutor {
    public String commandName;
    public Map<String, Class<? extends SubCommands>> subCommandClasses = new HashMap<>();

    public Commands() {
        commandName = this.getClass().getSimpleName().replace("Command", "").toLowerCase();
        registerSubCommands();
    }

    private void registerSubCommands() {
        new Reflections("com.luicel.clock.commands." + commandName).getSubTypesOf(SubCommands.class).forEach(subCommand -> {
            String subCommandName = subCommand.getSimpleName().replace("SubCommand", "").toLowerCase();

            subCommandClasses.put(subCommandName, subCommand);
        });
    }

    protected void printHelpMessage(Player player) {
        player.sendMessage(ChatUtils.format("&c&lCOMMAND USAGE:"));
        for (String text : getListOfHelpMessagesTexts()) {
            player.sendMessage(ChatUtils.format(String.format("&7/%s %s", commandName, text)));
        }
    }

    protected List<String> getListOfHelpMessagesTexts() {
        List<String> texts = Arrays.asList(new String[subCommandClasses.size()]);
        subCommandClasses.forEach((subCommandName, subCommandClass) -> {
            String argumentsText = subCommandClass.getAnnotation(ArgumentsText.class).value();
            int helpOrder = subCommandClass.getAnnotation(HelpOrder.class).value();

            texts.set(helpOrder - 1, String.format("%s %s", subCommandName, argumentsText));
        });
        return texts;
    }
}

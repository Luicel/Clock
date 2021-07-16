package com.luicel.clock.commands;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.files.Files;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class Commands implements TabExecutor {
    private final Map<String, Class<? extends SubCommands>> subCommandClasses = new HashMap<>();

    public String commandName;

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

    protected void executeCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Class<? extends SubCommands> subCommandClass = subCommandClasses.get(args[0]);
            Constructor<?> constructor = subCommandClass.getConstructor(CommandSender.class, String.class, String[].class);
            constructor.setAccessible(true);
            constructor.newInstance(sender, label, args);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | NullPointerException e) {
            printHelpMessage((Player) sender);
        } catch (InvocationTargetException e) {
            if (sender instanceof Player)
                sender.sendMessage(PrefixUtils.getErrorPrefix() +
                        "An error has occurred. Check console for more information.");
            e.printStackTrace();
        }
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

    protected Map<String, Class<? extends SubCommands>> getSubCommandClasses() {
        return subCommandClasses;
    }
}

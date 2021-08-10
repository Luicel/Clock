package com.luicel.clock.commands;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.annotations.Permission;
import com.luicel.clock.files.Files;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PermissionUtils;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

            // Check for valid permissions
            if (sender instanceof Player) {
                if (!PermissionUtils.doesPlayerHavePermission((Player) sender, commandName, args[0].toLowerCase())) {
                    sender.sendMessage(ChatUtils.format(PrefixUtils.getErrorPrefix() +
                            "Insufficient permissions!"));
                    return;
                }
            }

            // Create instance via constructor
            Constructor<?> constructor = subCommandClass.getConstructor(CommandSender.class, String.class, String[].class);
            constructor.setAccessible(true);
            Object subCommandInstance = constructor.newInstance(sender, label, args);

            // Execute execute() method from above instance
            Method executeMethod = subCommandClass.getDeclaredMethod("execute");
            executeMethod.setAccessible(true);
            executeMethod.invoke(subCommandInstance);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | NullPointerException e) {
            printHelpMessage((Player) sender);
        } catch (InvocationTargetException e) {
            if (sender instanceof Player)
                sender.sendMessage(ChatUtils.format(PrefixUtils.getErrorPrefix() +
                        "An error has occurred. Check console for more information."));
            e.printStackTrace();
        }
    }

    protected void printHelpMessage(Player player) {
        List<String> helpMessageTexts = getListOfHelpMessagesTexts(player);
        if (helpMessageTexts.size() > 0) {
            player.sendMessage(ChatUtils.format("&c&lCOMMAND USAGE:"));
            for (String text : helpMessageTexts) {
                player.sendMessage(ChatUtils.format(String.format("&7/%s %s", commandName, text)));
            }
        } else {
            player.sendMessage(ChatUtils.format(PrefixUtils.getErrorPrefix() + "Insufficient permissions!"));
        }
    }

    protected List<String> getListOfHelpMessagesTexts(Player player) {
        List<String> texts = new ArrayList<>();
        subCommandClasses.values().stream().sorted(Comparator.comparing(c -> c.getAnnotation(HelpOrder.class).value())).forEach(subCommandClass -> {
            // Ideally I would like to grab SubCommand name from subCommandClasses map, but :shrug:
            String subCommandName = subCommandClass.getSimpleName().replace("SubCommand", "").toLowerCase();

            if (PermissionUtils.doesPlayerHavePermission(player, commandName, subCommandName)) {
                String argumentsText = subCommandClass.getAnnotation(ArgumentsText.class).value();

                texts.add(String.format("%s %s", subCommandName, argumentsText));
            }
        });
        return texts;
    }

    protected Map<String, Class<? extends SubCommands>> getSubCommandClasses() {
        return subCommandClasses;
    }
}

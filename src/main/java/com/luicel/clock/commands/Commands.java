package com.luicel.clock.commands;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.List;

public abstract class Commands implements CommandExecutor {
    public String commandName;

    public Commands() {
        commandName = this.getClass().getSimpleName().replace("Command", "").toLowerCase();
    }

    protected void printHelpMessage(Player player) {
        List<String> texts = getListOfHelpMessagesTexts();

        player.sendMessage(ChatUtils.format("&c&lCOMMAND USAGE:"));
        for (String text : texts) {
            player.sendMessage(ChatUtils.format(String.format("&7/%s %s", commandName, text)));
        }
    }

    protected List<String> getListOfHelpMessagesTexts() {
        int amountOfSubCommands = new Reflections("com.luicel.clock.commands." + commandName).getSubTypesOf(SubCommands.class).size();
        List<String> texts = Arrays.asList(new String[amountOfSubCommands]);
        new Reflections("com.luicel.clock.commands." + commandName).getSubTypesOf(SubCommands.class).forEach(subCommand -> {
            String subCommandName = subCommand.getSimpleName().replace("SubCommand", "").toLowerCase();
            String argumentsText = subCommand.getAnnotation(ArgumentsText.class).value();
            int helpOrder = subCommand.getAnnotation(HelpOrder.class).value();

            texts.set(helpOrder - 1, String.format("%s %s", subCommandName, argumentsText));
        });
        return texts;
    }
}

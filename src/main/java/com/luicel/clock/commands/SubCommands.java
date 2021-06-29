package com.luicel.clock.commands;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommands {
    private CommandSender sender;
    private String label;
    private String[] args;

    public SubCommands(CommandSender sender, String label, String[] args) {
        this.sender = sender;
        this.label = label;
        this.args = args;

        execute();
    }

    // Variable Getters and Setters -----
    public Player getPlayer() {
        if (sender instanceof Player) {
            return (Player) sender;
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }

    public String getPrefix() {
        String color = getPrefixColor();
        return ChatUtils.format(String.format("%s&l%s! &7", color, label.toUpperCase()));
    }

    // temp until config.yml is established
    private String getPrefixColor() {
        String color = "&f";
        switch (label.toLowerCase()) {
            case "timer":
                color = "&b";
                break;
            default:
        }
        return color;
    }

    // Helper methods -----
    public void printSyntaxMessage(SubCommands subCommand) {
        sendMessage("&c&lINVALID SYNTAX:");

        String subCommandName = subCommand.getClass().getSimpleName().replace("SubCommand", "").toLowerCase();
        String argumentsText = subCommand.getClass().getAnnotation(ArgumentsText.class).value();
        sendMessage(String.format("&7/%s %s %s", getLabel(), subCommandName, argumentsText));
    }

    public void sendMessage(String message) {
        sender.sendMessage(ChatUtils.format(message));
    }

    protected abstract void execute();
}

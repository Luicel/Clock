package com.luicel.clock.commands;

import org.bukkit.command.CommandExecutor;

public abstract class Commands implements CommandExecutor {
    public String commandName;

    public Commands() {
        commandName = this.getClass().getSimpleName().replace("Command", "").toLowerCase();
    }
}

package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import org.bukkit.command.CommandSender;

public class StopSubCommand extends SubCommands {
    private static final String argText = "<name>";

    public StopSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {

    }
}

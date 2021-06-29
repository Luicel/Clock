package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.CommandSender;

public class StartSubCommand extends SubCommands {
    private static final String argText = "<name>";

    public StartSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        getPlayer().sendMessage(ChatUtils.format("&atest"));
    }
}

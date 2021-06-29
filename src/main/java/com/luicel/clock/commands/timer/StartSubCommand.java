package com.luicel.clock.commands.timer;

import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.utils.ChatUtils;
import org.bukkit.command.CommandSender;

@HelpOrder(3)
@ArgumentsText("<name>")
public class StartSubCommand extends SubCommands {
    public StartSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        getPlayer().sendMessage(ChatUtils.format("&atest"));
    }
}

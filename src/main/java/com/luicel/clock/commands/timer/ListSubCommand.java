package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@HelpOrder(4)
@ArgumentsText("")
public class ListSubCommand extends SubCommands {
    public ListSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        printTimers();
    }

    private void printTimers() {
        List<Timer> timers = TimersFile.getTimers();

        sendMessage(String.format(PrefixUtils.getHeaderColoredLine("&b") + "&b&lTIMERS: &f(%s)", timers.size()));
        for (Timer timer : timers) {
            TextComponent message = new TextComponent(ChatUtils.format("&7- " + timer.getName()));
            if (getPlayer() != null) {
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, createAndGetBaseComponent(timer)));
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/timer info " + timer.getName()));
                getPlayer().spigot().sendMessage(message);
            } else {
                sendMessage(message.getText());
            }
        }
    }

    private BaseComponent[] createAndGetBaseComponent(Timer timer) {
        StringBuilder text = new StringBuilder()
            .append("&7Time Remaining: &f").append(timer.getTimeRemainingAsString()).append("\n")
            .append("&7State: " + timer.getStateAsString()).append("\n")
            .append("\n")
            .append("&aClick for more info!");

        return new ComponentBuilder(ChatUtils.format(text.toString())).create();
    }
}

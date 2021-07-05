package com.luicel.clock.commands.timer;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.List;

@HelpOrder(3)
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

        sendMessage(String.format("&b&lTIMERS: &f(%s)", timers.size()));
        for (Timer timer : timers) {
            TextComponent message = new TextComponent(ChatUtils.format("&7- " + timer.getName()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, createAndGetBaseComponent(timer)));
            getPlayer().spigot().sendMessage(message);
        }
    }

    private BaseComponent[] createAndGetBaseComponent(Timer timer) {
        String line1 = ChatUtils.format("&7Display: " + timer.getFormattedDisplay());
        String line2 = ChatUtils.format("&7State: " + getTimerStateAsString(timer));
        String text = line1 + "\n" + line2;

        return new ComponentBuilder(text).create();
    }

    private String getTimerStateAsString(Timer timer) {
        String stateString = "";
        if (timer.getState() == Timer.State.ACTIVE)
            stateString = ChatUtils.format("&aActive");
        else if (timer.getState() == Timer.State.INACTIVE)
            stateString = ChatUtils.format("&cInactive");
        else if (timer.getState() == Timer.State.PAUSED)
            stateString = ChatUtils.format("&ePaused");
        return stateString;
    }
}

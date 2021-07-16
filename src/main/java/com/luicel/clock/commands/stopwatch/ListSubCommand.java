package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;

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
        List<Stopwatch> stopwatches = StopwatchesFile.getStopwatches();

        sendMessage(String.format(PrefixUtils.getHeaderColoredLine("&d") + "&d&lSTOPWATCHES: &f(%s)", stopwatches.size()));
        for (Stopwatch stopwatch : stopwatches) {
            TextComponent message = new TextComponent(ChatUtils.format("&7- " + stopwatch.getName()));
            if (getPlayer() != null) {
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, createAndGetBaseComponent(stopwatch)));
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stopwatch info " + stopwatch.getName()));
                getPlayer().spigot().sendMessage(message);
            } else {
                sendMessage(message.getText());
            }
        }
    }

    private BaseComponent[] createAndGetBaseComponent(Stopwatch stopwatch) {
        StringBuilder text = new StringBuilder()
                .append("&7Time: &f").append(stopwatch.getTimeAsString()).append("\n")
                .append("&7State: " + ChatUtils.getStateAsString(stopwatch.getState())).append("\n")
                .append("\n")
                .append("&aClick for more info!");

        return new ComponentBuilder(ChatUtils.format(text.toString())).create();
    }
}

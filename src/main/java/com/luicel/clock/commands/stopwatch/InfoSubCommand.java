package com.luicel.clock.commands.stopwatch;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;

import java.util.concurrent.atomic.AtomicInteger;

@HelpOrder(4)
@ArgumentsText("<name>")
public class InfoSubCommand extends SubCommands {
    public InfoSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        if (getArgs().length < 2) {
            printSyntaxMessage(this);
        } else {
            Stopwatch stopwatch = StopwatchesFile.getStopwatch(getArgs()[1]);
            if (stopwatch == null) {
                sendMessage(PrefixUtils.getErrorPrefix() + "No stopwatch with the name '&f" + getArgs()[1] + "&7' exists!");
            } else {
                printStopwatchInfo(stopwatch);
            }
        }
    }

    private void printStopwatchInfo(Stopwatch stopwatch) {
        sendMessage(PrefixUtils.getHeaderColoredLine("&d") + "&d&lSTOPWATCH INFO:");
        sendMessage("&7Name: &f" + stopwatch.getName());
        sendMessage("&7Time: &f" + stopwatch.getTimeAsString() + " &7(" +
                        stopwatch.getCurrentLapTimeAsString() + ")");
        sendMessage("&7State: &f" + ChatUtils.getStateAsString(stopwatch.getState()));
        sendLapMessage(stopwatch);
        sendMessage("");
        sendMessage("&7Display: &f" + ChatUtils.getDisplayAsString(stopwatch.getDisplay()));
        sendMessage("&7Format Prefix: &7'" + stopwatch.getFormatPrefix() + "&7'");
        sendMessage("&7Format Suffix: &7'" + stopwatch.getFormatSuffix() + "&7'");
    }

    private void sendLapMessage(Stopwatch stopwatch) {
        if (stopwatch.getLaps().size() > 0) {
            if (stopwatch.getLaps().size() == 1 || getPlayer() == null) {
                sendMessage("&7Last Lap: &f" + Stopwatch.convertTimeToString(stopwatch.getLaps().get(0)));
            } else {
                TextComponent message = new TextComponent(ChatUtils.format("&7Last Lap: &f" +
                        Stopwatch.convertTimeToString(stopwatch.getLaps().get(0)) +
                                " &7(Hover for More)"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, createAndGetBaseComponent(stopwatch)));
                getPlayer().spigot().sendMessage(message);

            }
        } else {
            sendMessage("&7Last Lap: &fN/A");
        }
    }

    private BaseComponent[] createAndGetBaseComponent(Stopwatch stopwatch) {
        StringBuilder text = new StringBuilder();
        AtomicInteger counter = new AtomicInteger(1);
        stopwatch.getLaps().forEach(lap -> {
            text.append(String.format("&7Latest Lap #%s: &f%s", counter.getAndIncrement(), Stopwatch.convertTimeToString(lap)));
            if (counter.get() - 1 != stopwatch.getLaps().size())
                text.append("\n");
        });

        return new ComponentBuilder(ChatUtils.format(text.toString())).create();
    }
}


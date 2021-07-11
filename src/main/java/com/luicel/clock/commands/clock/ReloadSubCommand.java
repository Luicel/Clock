package com.luicel.clock.commands.clock;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
import com.luicel.clock.utils.PrefixUtils;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

@HelpOrder(2)
@ArgumentsText("")
public class ReloadSubCommand extends SubCommands {
    public ReloadSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        DisplayRunnable.clearDisplayingObjects();
        reloadTimers();
        sendMessage(PrefixUtils.getClockPrefix() + "Successfully reloaded!");
    }

    private void reloadTimers() {
        // TODO add behavior for config option to disable using cached seconds
        // create map and store currently cached seconds
        Map<String, Long> map = new HashMap<>();
        TimersFile.getTimers().forEach(timer ->
                map.put(timer.getName(), timer.getSeconds())
        );

        // clear timers and re-register
        TimersFile.getTimers().clear();
        TimersFile.registerTimers();

        // update new timers with previously cached seconds and save to file
        TimersFile.getTimers().forEach(timer ->
                timer.setSeconds(map.get(timer.getName()))
        );
        TimersFile.getTimers().forEach(Timer::save);
    }
}

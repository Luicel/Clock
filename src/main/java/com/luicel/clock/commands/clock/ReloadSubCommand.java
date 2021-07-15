package com.luicel.clock.commands.clock;

import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.ConfigFile;
import com.luicel.clock.files.data.TimersFile;
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
        // TODO cleanup code
        if (ConfigFile.getBoolean("mechanics.use-cached-seconds-on-reload")) {
            Map<String, Long> map = new HashMap<>();
            TimersFile.getTimers().forEach(timer ->
                    map.put(timer.getName(), timer.getSeconds())
            );

            ConfigFile.reload();
            TimersFile.reload();

            TimersFile.getTimers().forEach(timer ->
                    timer.setSeconds(map.get(timer.getName()))
            );
            TimersFile.getTimers().forEach(Timer::save);
        } else {
            ConfigFile.reload();
            TimersFile.reload();
        }
    }
}

package com.luicel.clock.commands.clock;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

@HelpOrder(1)
@ArgumentsText("")
public class InfoSubCommand extends SubCommands {
    public InfoSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    public void execute() {
        sendMessage(PrefixUtils.getHeaderColoredLine("&e") + "&e&lCLOCK: &f(v" + Clock.getInstance().getDescription().getVersion() + ")");
        sendMessage("&7Create, delete, and configure timers and stopwatches");
        sendMessage("&7completely in-game!");
        sendMessage("");
        sendMessage("&7Check out the GitHub page for more information:");
        sendClickableGitHubLinkMessage();
    }

    private void sendClickableGitHubLinkMessage() {
        TextComponent message = new TextComponent(ChatUtils.format("&fhttps://github.com/Luicel/Clock"));
        message.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatUtils.format("&aClick to visit!")).create())
        );
        message.setClickEvent(new ClickEvent( ClickEvent.Action.OPEN_URL, "https://github.com/Luicel/Clock"));
        getPlayer().spigot().sendMessage(message);
    }
}

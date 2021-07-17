package com.luicel.clock.commands.clock;

import com.luicel.clock.Clock;
import com.luicel.clock.annotations.ArgumentsText;
import com.luicel.clock.annotations.HelpOrder;
import com.luicel.clock.commands.SubCommands;
import com.luicel.clock.utils.ChatUtils;
import com.luicel.clock.utils.PrefixUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

@HelpOrder(1)
@ArgumentsText("")
public class InfoSubCommand extends SubCommands {
    public InfoSubCommand(CommandSender sender, String label, String[] args) {
        super(sender, label, args);
    }

    @Override
    protected void execute() {
        sendMessage(PrefixUtils.getHeaderColoredLine("&e") + "&e&lCLOCK: &f(v" + Clock.getInstance().getDescription().getVersion() + ")");
        sendMessage("&7Create, configure, and display timers and stopwatches in-game!");
        sendMessage("");
        sendMessage("&7Check out the GitHub page for more information:");
        if (getPlayer() != null)
            sendClickableGitHubLinkMessage();
        else {
            sendMessage("&fhttps://github.com/Luicel/Clock");
        }
    }

    private void sendClickableGitHubLinkMessage() {
        TextComponent message = new TextComponent(ChatUtils.format("&fhttps://github.com/Luicel/Clock"));
        message.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(ChatUtils.format("&aClick to visit!")).create())
        );
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Luicel/Clock"));
        getPlayer().spigot().sendMessage(message);
    }
}

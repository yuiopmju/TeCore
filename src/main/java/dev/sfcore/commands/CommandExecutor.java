package dev.sfcore.commands;

import dev.sfcore.Handler;
import dev.sfcore.commands.handler.CommandSender;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandExecutor extends Handler {
    public void onCommand(String[] args, CommandSender sender);
}

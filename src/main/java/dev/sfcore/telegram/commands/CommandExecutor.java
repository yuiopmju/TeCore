package dev.sfcore.telegram.commands;

import dev.sfcore.telegram.Handler;
import dev.sfcore.telegram.commands.handler.CommandSender;

public interface CommandExecutor extends Handler {
    public void onCommand(String[] args, CommandSender sender);
}

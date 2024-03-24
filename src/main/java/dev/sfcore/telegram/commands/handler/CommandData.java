package dev.sfcore.telegram.commands.handler;

import dev.sfcore.telegram.commands.CommandExecutor;

@Deprecated
public class CommandData {
    private CommandExecutor executor;
    private boolean async;
    private String command;
    public CommandData(CommandExecutor executor, boolean async, String command){
        this.executor = executor;
        this.async = async;
        this.command = command;
    }
}

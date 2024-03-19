package dev.sfcore.commands.handler;

import dev.sfcore.commands.CommandExecutor;

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

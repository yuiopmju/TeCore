package dev.sfcore.server.commands;

import dev.sfcore.TeKit;
import dev.sfcore.commands.CommandExecutor;

public class Command {
    private String cmd;
    private CommandExecutor executor;
    private boolean isEnabled;

    public Command(String cmd){
        this.cmd = cmd;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Command setExecutor(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public void setState(boolean b) {
        isEnabled = b;
    }

    public String getName() {
        return cmd;
    }

    public void build(){
        TeKit.getCommandsLoader().registerCommand(this);
    }
}

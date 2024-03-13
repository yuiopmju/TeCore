package dev.sfcore.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler {
    private static final List<String> commands = new ArrayList<>();
    private static void registerCommands(){
        commands.addAll(List.of("help", "admin", "message"));
    }
}

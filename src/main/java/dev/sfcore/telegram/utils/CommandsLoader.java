package dev.sfcore.telegram.utils;

import dev.sfcore.telegram.commands.handler.CommandSender;
import dev.sfcore.telegram.server.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandsLoader {
    private volatile static List<Command> commands = new ArrayList<>();

    public List<Command> getCommands(){
        return commands;
    }

    public void registerCommand(Command command){
        for(Command c : commands){
            if(c.getName().equals(command.getName())){
                System.out.println("Command with name " + command.getName() + " already registered.");
                return;
            }

            if(c.getExecutor().getCustomId() == command.getExecutor().getCustomId()){
                System.out.println("Id for executor " + c.getExecutor().getCustomId() + " already registered.");
                return;
            }
        }

        commands.add(command);
    }

    public Command getRegisteredCommand(String command){
        for(Command c : commands){
            if(command.equals(c.getName())){
                return c;
            }
        }

        return null;
    }

    public void execute(String name, String[] args, CommandSender sender){
        getRegisteredCommand(name).getExecutor().onCommand(args, sender);
    }
}

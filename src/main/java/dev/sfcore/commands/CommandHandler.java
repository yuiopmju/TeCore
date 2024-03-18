package dev.sfcore.commands;

import dev.sfcore.Launcher;
import dev.sfcore.TeKit;
import dev.sfcore.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class CommandHandler {
    public static void execute(String command, Update update){
        String cmd = command.substring(1);

        for(Class<? extends CommandExecutor> clazz : TeKit.getLoader().getCommandExecutors()){
            for(Method method : clazz.getMethods()){
                if(method.isAnnotationPresent(Command.class)){
                    Command a = method.getAnnotation(Command.class);

                    if(a.command().equals(command)){
                        try {
                            method.invoke(null, command.split(" ")[0], Arrays.copyOfRange(command.split(" "), 1, command.split(" ").length), update);
                            System.out.println("User " + update.getMessage().getChat().getUserName() + " executed command: " + command);
                            return;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        MessageUtils.sendMessage(update, "Команда не найдена.");
    }

    public static void dispatchConsole(String command){
        switch (command) {
            case "end":
                System.out.println(TeKit.GREEN + "Disable response sent" + TeKit.RESET);
                Launcher.shutdown();
                break;
            default:
                System.out.println(TeKit.RED + "Unknown command" + TeKit.RESET);
        }
    }
}

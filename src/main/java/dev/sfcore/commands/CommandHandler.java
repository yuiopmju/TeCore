package dev.sfcore.commands;

import dev.sfcore.Handler;
import dev.sfcore.Launcher;
import dev.sfcore.TeKit;
import dev.sfcore.commands.handler.CommandSender;
import dev.sfcore.commands.handler.CommandSenderHandler;
import dev.sfcore.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class CommandHandler implements Handler {
    public static void execute(String command, Update update){
        String cmd = command.split(" ")[0].substring(1);

        System.out.println(TeKit.getLoader().getCommandExecutors());

        for(Class<? extends CommandExecutor> clazz : TeKit.getLoader().getCommandExecutors()){
            try {
                clazz.getMethod("onCommand", String[].class, CommandSender.class).invoke(null, Arrays.copyOfRange(command.split(" "), 1, command.split(" ").length), new CommandSenderHandler(update));
                System.out.println("Executed command from " + new CommandSenderHandler(update).getUser().getUserName() + ": " + command);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
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

package dev.sfcore.telegram.utils;

import dev.sfcore.telegram.Bot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public class MessageUtils {
    public static <T extends Serializable, Method extends BotApiMethod<T>> void execute(Method method){
        try {
            Bot.getInstance().execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(Update upd, String msg){
        execute(new SendMessage(upd.getMessage().getChatId().toString(), msg));
    }

    public static void sendMessage(Update upd, String msg, boolean enableHtml){
        SendMessage message = new SendMessage();
        message.setChatId(upd.getMessage().getChatId());
        message.enableHtml(true);
        message.setText(msg);

        execute(message);
    }
}

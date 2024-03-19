package dev.sfcore.commands.handler;

import dev.sfcore.server.client.User;
import dev.sfcore.server.client.hander.UserHandler;
import dev.sfcore.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandSender {
    public Update getUpdate();

    default void sendMessage(String message){
        MessageUtils.sendMessage(getUpdate(), message);
    }

    public void setUpdate(Update upd);

    default void sendReply(String reply){
        SendMessage message = new SendMessage();
        message.setChatId(getUpdate().getMessage().getChatId());
        message.setReplyToMessageId(getUpdate().getMessage().getMessageId());
        message.setText(reply);

        MessageUtils.execute(message);
    }

    default Message getMessage(){
        return getUpdate().getMessage();
    }

    default User getUser(){
        return new UserHandler(getUpdate().getMessage().getFrom());
    }
}


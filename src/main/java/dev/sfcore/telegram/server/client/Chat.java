package dev.sfcore.telegram.server.client;

import dev.sfcore.telegram.server.ChatType;
import dev.sfcore.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Chat {
    public long getChatId();

    /**
     * Sends a message to chat
     * @param msg your message
     */
    default void sendMessage(String msg){
        SendMessage message = new SendMessage();
        message.setChatId(getChatId());
        message.setText(msg);
        MessageUtils.execute(message);
    }

    /**
     * Sends a message to chat with html on or off
     * @param msg your message
     * @param b enableHtml parameter
     */
    default void sendMessage(String msg, boolean b){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getChatId());
        sendMessage.setText(msg);
        sendMessage.enableHtml(b);
        MessageUtils.execute(sendMessage);
    }

    default org.telegram.telegrambots.meta.api.objects.Chat getTelegramChat(ChatType type){
        return new org.telegram.telegrambots.meta.api.objects.Chat(getChatId(), type.getName());
    }

    /**
     * Logging chat information, like type, members, name, icon and another
     */
    default void callbackChatInformation(){

    }
}

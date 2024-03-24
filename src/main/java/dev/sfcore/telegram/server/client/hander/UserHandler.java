package dev.sfcore.telegram.server.client.hander;

import dev.sfcore.telegram.server.client.User;
import org.telegram.telegrambots.meta.api.objects.Chat;

public class UserHandler implements User {
    private String userId;
    private Chat chat;
    private org.telegram.telegrambots.meta.api.objects.User tgUser;
    public UserHandler(org.telegram.telegrambots.meta.api.objects.User tgUser, Chat chat){
        this.tgUser = tgUser;
        this.userId = tgUser.getId().toString();
        this.chat = chat;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Chat getChat() {
        return chat;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public org.telegram.telegrambots.meta.api.objects.User getTelegramUser() {
        return tgUser;
    }
}

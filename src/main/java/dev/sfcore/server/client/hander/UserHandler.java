package dev.sfcore.server.client.hander;

import dev.sfcore.server.client.User;

public class UserHandler implements User {
    private String userId;
    private org.telegram.telegrambots.meta.api.objects.User tgUser;
    public UserHandler(org.telegram.telegrambots.meta.api.objects.User tgUser){
        this.tgUser = tgUser;
        this.userId = tgUser.getId().toString();
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

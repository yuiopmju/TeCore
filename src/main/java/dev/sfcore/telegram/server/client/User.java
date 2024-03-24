package dev.sfcore.telegram.server.client;

import org.telegram.telegrambots.meta.api.objects.Chat;

public interface User {
    default String getUserName(){
        return getTelegramUser().getUserName();
    }

    public Chat getChat();

    public String getUserId();

    public org.telegram.telegrambots.meta.api.objects.User getTelegramUser();

    default public String getInitials(){
        return getTelegramUser().getFirstName() + " " + getTelegramUser().getLastName();
    }

    default public boolean isBot(){
        return getTelegramUser().getIsBot();
    }

    default boolean isPremium(){
        return getTelegramUser().getIsPremium();
    }
}

package dev.sfcore.server.client;

import dev.sfcore.Bot;
import org.telegram.telegrambots.meta.api.objects.Chat;

public interface User {
    default String getUserName(){
        return getTelegramUser().getUserName();
    }

    public String getUserId();

    public org.telegram.telegrambots.meta.api.objects.User getTelegramUser();
}

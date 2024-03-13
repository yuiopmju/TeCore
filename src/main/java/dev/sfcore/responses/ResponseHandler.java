package dev.sfcore.responses;

import dev.sfcore.responses.types.TextResponse;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class ResponseHandler {

    public static void handleResponse(Update update){
        onResponse(update).run();
        if(update.hasMessage() && update.getMessage().hasText()){
            onTextResponse(update).run();
        }
    }

    public static Response onResponse(Update upd) {
        return null;
    }

    public static Response onTextResponse(Update upd){
        return null;
    }
}

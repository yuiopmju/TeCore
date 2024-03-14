package dev.sfcore.responses;

import dev.sfcore.responses.types.TextResponse;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ResponseHandler {

    /*public static void handleResponse(Update update){
        onResponse(update).run();
        if(update.hasMessage() && update.getMessage().hasText()){
            onTextResponse(update).run();
        }
    }*/

    public Response onResponse(Update upd);

    public Response onTextResponse(Update upd);

    default void handleResponse(Update upd){
        onResponse(upd).run();
        if(upd.hasMessage() && upd.getMessage().hasText()){
            onTextResponse(upd).run();
        }
    }
}

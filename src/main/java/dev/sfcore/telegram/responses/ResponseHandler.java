package dev.sfcore.telegram.responses;

import dev.sfcore.telegram.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ResponseHandler extends Handler {

    default Response onObjectMappedResponse(Update upd){
        return null;
    }

    public void onResponse(Update update);

    default void handleResponse(Update upd){
        onResponse(upd);
        if(upd.hasMessage() && upd.getMessage().hasText()){
            onObjectMappedResponse(upd);
        }
    }
}

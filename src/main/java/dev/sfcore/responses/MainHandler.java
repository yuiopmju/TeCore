package dev.sfcore.responses;

import dev.sfcore.TeKit;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainHandler {
    private List<Class<? extends ResponseHandler>> handlerClasses;

    public MainHandler(){
    }

    public void handleMessage(Update update){
        for (Class<? extends ResponseHandler> handlerClass : handlerClasses) {
            try {
                Constructor<? extends ResponseHandler> constructor = handlerClass.getConstructor();
                ResponseHandler handler = constructor.newInstance();
                handler.onResponse(update);
                if (update.hasMessage() && update.getMessage().hasText()) {
                    handler.onObjectMappedResponse(update);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void load(){
        handlerClasses = new ArrayList<>(TeKit.getLoader().getResponseHandlers());
    }
}

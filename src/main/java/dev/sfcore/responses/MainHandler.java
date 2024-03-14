package dev.sfcore.responses;

import dev.sfcore.TeKit;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainHandler {
    private final List<Class<? extends ResponseHandler>> handlerClasses = new ArrayList<>(TeKit.getLoader().getHandlers());

    public MainHandler(){
    }

    public void handleMessage(Update update){
        for (Class<? extends ResponseHandler> handlerClass : handlerClasses) {
            try {
                Constructor<? extends ResponseHandler> constructor = handlerClass.getConstructor();
                ResponseHandler handler = constructor.newInstance();
                // Вызываем соответствующие методы
                handler.onResponse(update);
                if (update.hasMessage() && update.getMessage().hasText()) {
                    handler.onTextResponse(update);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

package dev.sfcore.telegram.responses;

import dev.sfcore.telegram.responses.answer.AnswerDispatcher;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface Response {
    public Update getUpdate();
    default <T> T run(){
        CompletableFuture<T> c = CompletableFuture.supplyAsync(getAnswerDispatcher().getAnswer().getAnswer());
        try {
            return c.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public AnswerDispatcher getAnswerDispatcher();
    default AnswerDispatcher createDispatcher(){
        return new AnswerDispatcher(getUpdate(), this, getType());
    }

    public ResponseType getType();
}

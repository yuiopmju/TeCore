package dev.sfcore.responses.answer;

import dev.sfcore.responses.Response;
import dev.sfcore.responses.ResponseType;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Supplier;

public class AnswerDispatcher {
    private Update upd;
    private Response response;
    private ResponseType type;
    private Answer answer;

    public AnswerDispatcher(Update upd, Response response, ResponseType type){
        this.upd = upd;
        this.response = response;
        this.type = type;
    }

    public void setAnswer(Answer answer){
        this.answer = answer;
    }

    public Update getUpdate() {
        return upd;
    }

    public Response getResponse() {
        return response;
    }

    public ResponseType getType() {
        return type;
    }

    public Answer getAnswer() {
        return answer;
    }
}

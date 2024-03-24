package dev.sfcore.telegram.responses.answer;

import dev.sfcore.telegram.responses.Response;
import dev.sfcore.telegram.responses.ResponseType;
import org.telegram.telegrambots.meta.api.objects.Update;

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

package dev.sfcore.telegram.responses.types;

import dev.sfcore.telegram.responses.Response;
import dev.sfcore.telegram.responses.ResponseType;
import dev.sfcore.telegram.responses.answer.AnswerDispatcher;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TextResponse implements Response {

    private Update upd;
    private AnswerDispatcher answerDispatcher;
    private final ResponseType TYPE = ResponseType.TEXT;

    public TextResponse(Update upd){
        this.upd = upd;
        answerDispatcher = createDispatcher();
    }

    @Override
    public Update getUpdate() {
        return upd;
    }

    @Override
    public AnswerDispatcher getAnswerDispatcher() {
        return answerDispatcher;
    }

    @Override
    public ResponseType getType() {
        return TYPE;
    }
}

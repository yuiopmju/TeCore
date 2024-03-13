package dev.sfcore.responses.types;

import dev.sfcore.responses.Response;
import dev.sfcore.responses.ResponseType;
import dev.sfcore.responses.answer.AnswerDispatcher;
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

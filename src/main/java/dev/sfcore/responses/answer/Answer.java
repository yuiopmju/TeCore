package dev.sfcore.responses.answer;

import dev.sfcore.utils.MessageUtils;

import java.util.function.Supplier;

public class Answer {
    private AnswerDispatcher dispatcher;
    private Supplier answer;

    public <T> Answer(AnswerDispatcher dispatcher, Supplier<T> answer){
        this.dispatcher = dispatcher;
        this.answer = answer;
    }

    public AnswerDispatcher getDispatcher() {
        return dispatcher;
    }

    public Supplier getAnswer() {
        return answer;
    }
}

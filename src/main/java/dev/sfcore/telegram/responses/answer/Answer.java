package dev.sfcore.telegram.responses.answer;

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

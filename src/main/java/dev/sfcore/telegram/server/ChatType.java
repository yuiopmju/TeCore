package dev.sfcore.telegram.server;

public enum ChatType {
    PRIVATE("private"), GROUP("group"), SUPERGROUP("supergroup"), CHANNEL("channel"), SELF_DESTRUCTING("self-destructing"), BOT("bot");

    String name;

    ChatType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

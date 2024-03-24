package dev.sfcore.telegram;

public interface Handler {
    int s = TeKit.getNewId();
    default int getCustomId(){
        return s;
    }
}

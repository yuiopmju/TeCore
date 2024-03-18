package dev.sfcore;

public interface Handler {
    int s = TeKit.getNewId();
    default int getCustomId(){
        return s;
    }
}

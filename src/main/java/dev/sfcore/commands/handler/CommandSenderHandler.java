package dev.sfcore.commands.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandSenderHandler implements CommandSender {

    private Update update;

    public CommandSenderHandler(Update upd){
        setUpdate(upd);
    }

    @Override
    public Update getUpdate() {
        return null;
    }

    @Override
    public void setUpdate(Update upd) {
        update = upd;
    }
}

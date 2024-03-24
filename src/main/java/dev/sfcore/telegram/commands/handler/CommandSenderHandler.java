package dev.sfcore.telegram.commands.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandSenderHandler implements CommandSender {

    private Update update;

    public CommandSenderHandler(Update upd){
        setUpdate(upd);
        update = upd;
    }

    @Override
    public Update getUpdate() {
        return update;
    }

    @Override
    public void setUpdate(Update upd) {
        update = upd;
    }

    public CommandSender getCommandSender(){
        return this;
    }
}

package dev.sfcore.telegram;

import dev.sfcore.telegram.commands.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {

    private static String name;
    private static String token;

    private static Bot instance;

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().startsWith("/")){
            CommandHandler.execute(update.getMessage().getText().replaceFirst("/", ""), update);
            return;
        }

        System.out.println(update.getMessage().getChat().getType());

        TeKit.getMainHandler().handleMessage(update);

        System.out.println("Registered response from " + update.getMessage().getFrom().getUserName());
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public static void main(String[] args) {

        System.out.println("Starting bot...");

        if(args.length != 2){
            System.out.println("Error while reading args");
            return;
        }

        token = args[0];
        name = args[1];

        Bot bot = new Bot();
        instance = bot;
        TelegramBotsApi botsApi;

        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static Bot getInstance() {
        return instance;
    }
}

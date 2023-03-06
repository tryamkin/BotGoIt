package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        //t.me/TryamGoitBot
        //Use this token to access the HTTP API:
        //6027341656:AAEde3-KeKWS4LCNf661mQlBidEYCYhvP5E
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        //System.out.println("Hello Taras!");
        api.registerBot(new Main());
    }

    @Override
    public String getBotUsername() {
        return "TryamGoitBot";
    }

    @Override
    public String getBotToken() {
        return "6027341656:AAEde3-KeKWS4LCNf661mQlBidEYCYhvP5E";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);

        if (update.hasMessage()&& update.getMessage().getText().equals("/start")){
            SendMessage message = creaateMessage("Hello!!!");
            message.setChatId(chatId);
            attacBbuttons(message, Map.of(
                    "Glory to Ukraine !!!", "glory_for_ukraine"
            ));
            sendApiMethodAsync(message);
        }

        if (update.hasCallbackQuery()){
            if (update.getCallbackQuery().getData().equals("glory_for_ukraine")){
                String txt = "Heroes glory!!!";
                
                SendMessage message = creaateMessage(txt);
                message.setChatId(chatId);
                attacBbuttons(message, Map.of(
                        "Glory to the nation!!!", "glory_for_nation"
                ));
                sendApiMethodAsync(message);
            }
            if (update.getCallbackQuery().getData().equals("glory_for_nation")){
                SendMessage message = creaateMessage("Death to enemies!!!");
                message.setChatId(chatId);
                sendApiMethodAsync(message);
            }
        }
        /*SendMessage message = new SendMessage();
        message.setText("Hello from Java!");
        message.setChatId(chatId);
        sendApiMethodAsync(message);*/

//
//        SendMessage msg = creaateMessage("*Hello MISHA!!!!* " );
///*        attacBbuttons(msg, Map.of(
//                "Hello", "hello_btn",
//                "Hello1", "hello_btn1"
//        ));*/
//        msg.setChatId(chatId);
//        sendApiMethodAsync(msg);
    }

    public Long getChatId (Update update){
        if (update.hasMessage()){
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public SendMessage creaateMessage(String text){
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        return message;
    }
    public void attacBbuttons (SendMessage message, Map<String, String> buttons){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonsName: buttons.keySet()) {
            String buttonVaulue = buttons.get(buttonsName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(new String(buttonsName.getBytes(),StandardCharsets.UTF_8));
            button.setCallbackData(buttonVaulue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}
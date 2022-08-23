package com.oa.api.telegram;

import com.google.common.collect.Lists;
import com.oa.api.model.UpcomingBet;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

public class OABot extends TelegramLongPollingBot {

    private final static String token = "5492412973:AAGuv4i27xVzitxE6sY-uoELX62T7MoN_WI";

    private final static String chatId = "-1001435276246";

    @Override
    public String getBotUsername() {
        return "oavb_bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void chunkMessage(List<UpcomingBet> upcomingBetList) {
        List<List<UpcomingBet>> allLists = Lists.partition(upcomingBetList, 5);

        for(List<UpcomingBet> list : allLists){
            sendMessage(removeUnwantedCharacters(list.toString()));
        }
    }

    private String removeUnwantedCharacters(String listString) {
        return listString.replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "");

    }

    private void sendMessage(String message){
        SendMessage request = new SendMessage();
        request.setChatId(chatId);
        request.setText(message);

        try {
            execute(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

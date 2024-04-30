package com.oa.live.api.telegram;

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

    /*public void chunkMessage(String filterName, List<UpcomingBet> upcomingBetList, String market, String bookie, boolean error) {
        List<List<UpcomingBet>> allLists = Lists.partition(upcomingBetList, 5);

        if(error){
            sendMessage("\u2757 There has been an error while trying to fetch new games for " + MarketMapper.getNameByKey(market) + " on " + bookie);
        }
        else {
            for(List<UpcomingBet> list : allLists){
                sendMessage("\uD83D\uDCCC " + filterName.toUpperCase() + "\n\n" + removeUnwantedCharacters(list.toString()));
            }
        }

        *//*if(allLists.isEmpty()){
            sendMessage("\uD83D\uDD34 No new games to bet on for " + market + " on " + bookie);
        }*//*
    }*/

    private String removeUnwantedCharacters(String listString) {
        return listString.replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "");

    }

    public void sendMessage(String message){
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

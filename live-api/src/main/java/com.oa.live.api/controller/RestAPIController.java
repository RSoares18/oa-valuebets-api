package com.oa.live.api.controller;
import com.oa.live.api.telegram.OABot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableScheduling
@Configuration
public class RestAPIController {

    private static final Logger log = LoggerFactory.getLogger(RestAPIController.class);

    private static final String PAGE_EXTENSION = "&page=";
    private static final String MARKET_EXTENSION = "&market=";

    private static final String LIVE_API_URL = "https://data.oddalerts.com/api/fixtures/live?api_token=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP&include=stats,probability,odds,odds.live";


    OABot telegramBot = new OABot();

    private DefaultBotSession botSession;

    @PostConstruct
    public void init(){
        /*try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = (DefaultBotSession) botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
    }


    @GetMapping(value ="/in-play")
    public List<HashMap<String,Object>> getResults(){
        //int existingRecords = betGameService.getTotalRecords();
        List<HashMap<String, Object>> allGames = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        List<HashMap<String,Object>> result = (List<HashMap<String,Object>>) restTemplate.getForObject(LIVE_API_URL, HashMap.class).get("data");

        log.info("Call Successful");
        /*int pages = (Integer) globalInfo.get("info").get("pages");
        int currentPage = 1;

        while(currentPage <= pages){
            restTemplate = new RestTemplate();
            globalInfo = restTemplate.getForObject(uri + PAGE_EXTENSION + currentPage, HashMap.class);
            List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) globalInfo.get("data");
            for(HashMap<String, Object> betGame : result){
                betGameService.createBetGame(betGame);
            }
            allGames.addAll(result);
            currentPage++;
        }

        int totalGamesInserted = betGameService.getTotalRecords()-existingRecords;
        telegramBot.sendMessage("[OA SERVICE] Inserted:" + totalGamesInserted + "games in the DB");
        telegramBot.onClosing();
        log.info("[OA SERVICE] Inserted: {} games in the DB", totalGamesInserted);*/
        return result;
    }


    private void registerBot(OABot bot){
        try {
            stopBotSession();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = (DefaultBotSession) botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


   /* @Scheduled (cron="0 20 12 * * *")
    public void runResults() throws UnsupportedEncodingException {
        registerBot(telegramBot);
        log.info("STARTED getting latest results...");
        getResults();
        log.info("FINISHED getting latest results");
        stopBotSession();
    }*/

    /*@Scheduled (cron="0 0 * * * *")
    public void isAlive() {
        registerBot(telegramBot);
        log.info("Checking app status...");
        try {
            telegramBot.sendMessage("\u2747 Application Running");
            log.info("App running!");
        } catch(Exception e){
            telegramBot.sendMessage("\u2757 Error checking application status");
            log.error("Something wrong while checking status");
        }

        stopBotSession();
    }*/

    private void stopBotSession(){
        if(botSession.isRunning()){
            botSession.stop();
        }
    }
}

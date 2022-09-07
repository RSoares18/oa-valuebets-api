package com.oa.api.controller;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.entity.RegisteredBetDTO;
import com.oa.api.model.*;
import com.oa.api.service.BetGameService;
import com.oa.api.service.TestService;
import com.oa.api.service.UpcomingBetService;
import com.oa.api.telegram.OABot;
import com.oa.api.util.UpcomingRequests;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableScheduling
@Configuration
public class RestAPIController {

    private static final Logger log = Logger.getLogger(RestAPIController.class);

    OABot telegramBot = new OABot();

    @Autowired
    BetGameService betGameService;

    @Autowired
    TestService testService;

    @Autowired
    UpcomingBetService upcomingBetService;

    private DefaultBotSession botSession;

    @PostConstruct
    public void init(){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = (DefaultBotSession) botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @GetMapping(value ="/results")
    public List<HashMap<String, Object>> getResults(){
        int existingRecords = betGameService.getTotalRecords();
        String uri = "https://oddalerts.com/value-bets?export=results&key=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP";
        RestTemplate restTemplate = new RestTemplate();
        List<HashMap<String, Object>> result = restTemplate.getForObject(uri, List.class);
        for(HashMap<String, Object> betGame : result){
            betGameService.createBetGame(betGame);
        }
        log.info("[OA SERVICE] Inserted: " + (betGameService.getTotalRecords()-existingRecords) + " games");
        return result;
    }

    @PostMapping(value ="/upcoming")
    public List<UpcomingBet> getUpcoming(@RequestBody FilterRequest filterRequest) throws UnsupportedEncodingException {
        String uri = "https://oddalerts.com/value-bets?export=upcoming&key=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP";
        RestTemplate restTemplate = new RestTemplate();
        List<HashMap<String, Object>> result = restTemplate.getForObject(uri, List.class);
        List<UpcomingBet> upcomingBets = upcomingBetService.executeFiltering(filterRequest, result);
        registerBot(telegramBot);
        telegramBot.chunkMessage(upcomingBets);
        telegramBot.onClosing();
        return upcomingBets;
    }

    @GetMapping(value ="/allUpcoming")
    public List<UpcomingBet> getUpcoming() throws UnsupportedEncodingException {
        log.info("Get All Upcoming Requests");
        String uri = "https://oddalerts.com/value-bets?export=upcoming&key=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP";
        RestTemplate restTemplate = new RestTemplate();
        List<HashMap<String, Object>> result = restTemplate.getForObject(uri, List.class);
        UpcomingRequests requests = new UpcomingRequests();
        List<FilterRequest> allRequests = requests.getAllRequests();
        List<UpcomingBet> upcomingBets = new ArrayList<>();

        for(FilterRequest filterRequest : allRequests){
            upcomingBets.addAll(upcomingBetService.executeFiltering(filterRequest, result));
        }
        if(!botSession.isRunning()){
            registerBot(telegramBot);
        }
        telegramBot.chunkMessage(upcomingBets);
        telegramBot.onClosing();
        return upcomingBets;
    }

    @PostMapping(value ="/test")
    public TestResponse getTestResult(@RequestBody TestRequest request){
        return testService.doTest(request);
    }

    @PostMapping(value ="/save")
    public List<RegisteredBetDTO> getTestResult(@RequestBody List<RegisteredBet> bets){
        return upcomingBetService.saveAll(bets);
    }

    @GetMapping(value ="/market")
    public List<BetGameDTO> getTestResult(@RequestParam String market, @RequestParam Double minOdds){
        return betGameService.getGamesByMarket(market).stream().filter(betGameDTO -> betGameDTO.getOpening_1xbet_odds() != null && betGameDTO.getOpening_1xbet_odds() >= minOdds).collect(Collectors.toList());
    }

    private void registerBot(OABot bot){
        try {
            botSession.stop();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = (DefaultBotSession) botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Scheduled (cron="0 0/30 * * * *")
    public void runRequests() throws UnsupportedEncodingException {
        registerBot(telegramBot);
        log.info("Starting scheduled upcoming request...");
        UpcomingRequests requests = new UpcomingRequests();
        List<FilterRequest> allRequests = requests.getAllRequests();

        for(FilterRequest request : allRequests){
            getUpcoming(request);
        }

    }
}

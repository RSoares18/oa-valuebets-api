package com.oa.api.controller;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.entity.RegisteredBetDTO;
import com.oa.api.model.*;
import com.oa.api.service.BetGameService;
import com.oa.api.service.TestService;
import com.oa.api.service.UpcomingBetService;
import com.oa.api.telegram.OABot;
import com.oa.api.util.MarketMapper;
import com.oa.api.util.UpcomingRequests;
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
        String uri = "https://data.oddalerts.com/api/value/results?api_token=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP";
        RestTemplate restTemplate = new RestTemplate();
        List<HashMap<String, Object>> result = restTemplate.getForObject(uri, List.class);
        for(HashMap<String, Object> betGame : result){
            betGameService.createBetGame(betGame);
        }
        log.info("[OA SERVICE] Inserted: {} games in the DB", (betGameService.getTotalRecords()-existingRecords));
        return result;
    }

    @PostMapping(value ="/upcoming")
    public List<UpcomingBet> getUpcoming(@RequestBody FilterRequest filterRequest) throws UnsupportedEncodingException {
        try{
            String uri = "https://data.oddalerts.com/api/value/upcoming?api_token=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP";
            RestTemplate restTemplate = new RestTemplate();
            List<HashMap<String, Object>> result = restTemplate.getForObject(uri, List.class);
            List<UpcomingBet> upcomingBets = upcomingBetService.executeFiltering(filterRequest, result);
            String market = MarketMapper.getNameByKey(filterRequest.getMarket());
            String bookie = filterRequest.getBookie();
            String filterName = filterRequest.getFilterName();
            log.info("{} - {} NEW GAMES FOR {} MARKET ON {}", filterRequest.getFilterName(), upcomingBets.size(), market, bookie);
            telegramBot.chunkMessage(filterName,upcomingBets, market, bookie, false);
            telegramBot.onClosing();
            return upcomingBets;
        } catch(Exception e){
            log.info(e.getMessage(), e);
            telegramBot.chunkMessage(filterRequest.getFilterName(), new ArrayList<>(), filterRequest.getMarket(), filterRequest.getBookie(), true);
            return new ArrayList<>();
        }

    }

    @GetMapping(value ="/allUpcoming")
    public List<UpcomingBet> getUpcoming() throws UnsupportedEncodingException {
        try{
            log.info("Get All Upcoming Requests");
            String uri = "https://data.oddalerts.com/api/value/upcoming?api_token=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP";
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

            log.info("{} NEW GAMES TO BET!", upcomingBets.size());
            telegramBot.chunkMessage("All Filters", upcomingBets, "All Markets", "requested bookies", false);
            telegramBot.onClosing();
            stopBotSession();
            return upcomingBets;
        } catch(Exception e){
            log.info(e.getMessage(), e);
            return new ArrayList<>();
        }

    }

    @PostMapping(value ="/test")
    public TestResponse getTestResult(@RequestBody TestRequest request){
        return testService.doTest(request);
    }

    @PostMapping(value ="/test/all")
    public MultipleTestResponse getAllTests(@RequestBody MultipleTestRequest request){
        return testService.doMultipleTests(request);
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
            stopBotSession();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = (DefaultBotSession) botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Scheduled (cron="0 0/15 * * * *")
    public void runRequests() throws UnsupportedEncodingException {
        registerBot(telegramBot);
        log.info("Starting scheduled upcoming request...");
        UpcomingRequests requests = new UpcomingRequests();
        List<FilterRequest> allRequests = requests.getAllRequests();

        for(FilterRequest request : allRequests){
            getUpcoming(request);
        }
        stopBotSession();
    }

    @Scheduled (cron="0 0 * * * *")
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
    }

    private void stopBotSession(){
        if(botSession.isRunning()){
            botSession.stop();
        }
    }
}

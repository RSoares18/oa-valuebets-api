package com.oa.api.bankroll;

import com.oa.api.bankroll.model.Bet;
import com.oa.api.bankroll.model.BetsStatement;
import com.oa.api.service.UpcomingBetService;
import com.oa.api.util.BigDecimalRoundDoubleMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BankrollService {

    public static final double FIRST_UNIT_SIZE = 5.0;

    public static final String URL_BETS = "https://data.oddalerts.com/api/bets?api_token=uV9gMykWc2GZ3DRvrq39jyNRahLo5lOYlT8P68JIwcW1mZGsbQ6zNQSqLkhP&per_page=50";

    private static final String PAGE_EXTENSION ="&page=";

    @Autowired
    private BetPlacedConverter betPlacedConverter;

    public BetsStatement calculateStatement(String startDate, String endDate, boolean includeGames){
        BetsStatement betsStatement = new BetsStatement();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        betsStatement.setStartDate(startDate);
        betsStatement.setEndDate(endDate);


        List<HashMap<String,Object>> resultFilteredByDate = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        int currentPage = 1;
        boolean listChanged = true;

        while(listChanged){
            listChanged = false;
            HashMap<String, HashMap<String, Object>> globalInfo = restTemplate.getForObject(URL_BETS+PAGE_EXTENSION+currentPage, HashMap.class);
            List<HashMap<String,Object>> result = (List<HashMap<String, Object>>) globalInfo.get("data");
            for(HashMap<String,Object> obj : result){
                if(isValidDate(start, end, betPlacedConverter.getGameDate(obj))){
                    resultFilteredByDate.add(obj);
                    listChanged = true;
                }
            }
            currentPage++;
        }

        List<Bet> settledBets = new ArrayList<>();

        for(HashMap<String,Object> obj : resultFilteredByDate){
            Bet bet = betPlacedConverter.convertToBetDTO(obj);
            if(bet != null){
                settledBets.add(bet);
            }
        }

        buildStatement(settledBets,betsStatement);
        if(includeGames) {
            betsStatement.setBets(settledBets);
        }
        betsStatement.setNumBets(settledBets.size());

        return betsStatement;
    }

    private void buildStatement(List<Bet> settledBets, BetsStatement statement) {
        double totalProfit = 0.0;
        double totalStaked = 0.0;
        double roi = 0.0;
        int wins = 0;
        int losses = 0;
        for(Bet b : settledBets){
            totalProfit += b.getBetResult().getProfit();
            totalStaked += b.getStake();
            if(b.getBetResult().getText().equalsIgnoreCase("lost")) losses++;else wins++;
        }

        roi = totalStaked > 0 ? (totalProfit/totalStaked) * 100 : roi;

        statement.setLosses(losses);
        statement.setWins(wins);
        statement.setRoi(BigDecimalRoundDoubleMain.roundDouble(roi,2));
        statement.setProfit_loss_units(BigDecimalRoundDoubleMain.roundDouble((totalProfit/ UpcomingBetService.BANKROLL)*100,2));
        statement.setProfit_loss_money(BigDecimalRoundDoubleMain.roundDouble(totalProfit,2));
        statement.setStaked(BigDecimalRoundDoubleMain.roundDouble(totalStaked,2));

    }

    private boolean isValidDate(LocalDate startDate, LocalDate endDate, LocalDate gameDate){
        return (gameDate.isAfter(startDate) || gameDate.isEqual(startDate)) && (gameDate.isBefore(endDate) || gameDate.isEqual(endDate));
    }




}

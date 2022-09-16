package com.oa.api.service;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.model.TestRequest;
import com.oa.api.model.TestResponse;
import com.oa.api.util.BigDecimalRoundDoubleMain;
import com.oa.api.util.Bookmakers;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final static Logger log = Logger.getLogger(TestService.class);
    private final static Double STAKE = 1.00;

    @Autowired
    private BetGameService betGameService;

    public TestResponse doTest(TestRequest request){
        TestResponse response = new TestResponse();
        response.setMinOdds(request.getMinOdds());
        response.setMaxOdds(request.getMaxOdds());
        response.setMinProbability(request.getMinProbability());
        response.setMinValue(request.getMinValue());
        response.setMaxValue(request.getMaxValue());
        response.setMarket(request.getMarket());
        response.setMaxLeagueProgress(request.getMaxProgress());
        response.setMinGamesPlayed(request.getMinGamesPlayed());
        response.setKellyFactor(request.getKellyFactor());
        List<BetGameDTO> marketGames = betGameService.getGamesByMarket(request.getMarket());
        List<BetGameDTO> gamesToTest = new ArrayList<>();

        if(request.getBookie().equals(Bookmakers.ONEXBET.getName())){
            gamesToTest = filterGames1xbet(request, marketGames);
        } else if(request.getBookie().equals(Bookmakers.BET365.getName())){
            gamesToTest = filterGamesBet365(request, marketGames);
        } else if (request.getBookie().equals(Bookmakers.PINNACLE.getName())){
            gamesToTest = filterGamesPinnacle(request, marketGames);
        }

        log.info("Total market games " + marketGames.size());
        log.info("Total games to test " + gamesToTest.size());
        if(!gamesToTest.isEmpty()){
            executeTest(response, gamesToTest, request.isOpeningOdds(), request.getBookie(), request.getKellyFactor(), request.getMinGamesPlayed());
        }
        return response;
    }

    private Double calculateKellyFactor1xBet(BetGameDTO betGameDTO, boolean opening){
        Double b_decimal_odds = (opening ? betGameDTO.getOpening_1xbet_odds() : betGameDTO.getLatest_1xbet_odds()) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyFactorBet365(BetGameDTO betGameDTO, boolean opening){
        Double b_decimal_odds = (opening ? betGameDTO.getOpening_b365_odds() : betGameDTO.getLatest_b365_odds()) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyFactorPinnacle(BetGameDTO betGameDTO, boolean opening){
        Double b_decimal_odds = (opening ? betGameDTO.getOpening_pinnacle_odds() : betGameDTO.getLatest_pinnacle_odds()) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKelly(BetGameDTO betGameDTO, Double bDecimalOdds){
        Double p_win = betGameDTO.getProbability()/100;
        Double q_lose = 1.00 - p_win;
        return ((bDecimalOdds*p_win)-q_lose)/bDecimalOdds;
    }

    private void executeTest(TestResponse response, List<BetGameDTO> gamesToTest, boolean opening, String bookie, Double kellyFactor, int minGamesPlayed){
        int wins = 0;
        int losses = 0;
        Double currentDrawdown = 0.0;
        Double maxDrawdown = 0.0;
        Double maxProfit = 0.0;
        Double profit = 0.0;

        Double staked = 0.0;
        Double returned = 0.0;

        if(kellyFactor == 0.00){
            for(BetGameDTO betGameDTO : gamesToTest){
                if(betGameDTO.getHome_played() >= minGamesPlayed && betGameDTO.getAway_played() >= minGamesPlayed){
                    if(betGameDTO.isResult()){
                        wins++;
                        Double oddsToCalculate = calculateOdds(opening,bookie, betGameDTO);
                        returned = returned + (oddsToCalculate * STAKE);
                    } else {
                        losses++;
                    }
                    staked = staked + STAKE;
                    profit = returned - staked;
                    maxProfit = profit > maxProfit ? profit : maxProfit;
                    currentDrawdown = profit >= maxProfit ? 0.0 : profit - maxProfit;
                    maxDrawdown = currentDrawdown < maxDrawdown ? currentDrawdown : maxDrawdown;
                }
            }
        } else {
            for(BetGameDTO betGameDTO : gamesToTest){
                if(betGameDTO.getHome_played() >= minGamesPlayed && betGameDTO.getAway_played() >= minGamesPlayed) {
                    Double kellyFactorCalc = 0.0;
                    if (bookie.equals(Bookmakers.ONEXBET.getName())) {
                        kellyFactorCalc = calculateKellyFactor1xBet(betGameDTO, opening);
                    } else if (bookie.equals(Bookmakers.BET365.getName())) {
                        kellyFactorCalc = calculateKellyFactorBet365(betGameDTO, opening);
                    } else if (bookie.equals(Bookmakers.PINNACLE.getName())) {
                        kellyFactorCalc = calculateKellyFactorPinnacle(betGameDTO, opening);
                    }
                    if (kellyFactorCalc >= kellyFactor) {
                        if (betGameDTO.isResult()) {
                            wins++;
                            Double oddsToCalculate = calculateOdds(opening, bookie, betGameDTO);
                            returned = returned + (oddsToCalculate * STAKE);
                        } else {
                            losses++;
                        }
                        staked = staked + STAKE;
                        profit = returned - staked;
                        maxProfit = profit > maxProfit ? profit : maxProfit;
                        currentDrawdown = profit >= maxProfit ? 0.0 : profit - maxProfit;
                        maxDrawdown = currentDrawdown < maxDrawdown ? currentDrawdown : maxDrawdown;
                    }
                }
            }
        }

        response.setMaxDrawdown(BigDecimalRoundDoubleMain.roundDouble(maxDrawdown, 2));
        response.setMaxProfit(BigDecimalRoundDoubleMain.roundDouble(maxProfit,2));
        response.setRoi(BigDecimalRoundDoubleMain.roundDouble(((profit*STAKE)/ (staked)) * 100, 2));
        response.setWins(wins);
        response.setLosses(losses);
        response.setProfit(BigDecimalRoundDoubleMain.roundDouble(profit,2));
        response.setNumBets(wins+losses);
        response.setHitRate(wins > 0 ? BigDecimalRoundDoubleMain.roundDouble(((double)wins / response.getNumBets()) * 100,2) : 0.00);

    }

    private Double calculateOdds(boolean opening, String bookie, BetGameDTO betGameDTO){
        if(opening){
            switch (bookie){
                case "1xBet":
                    return betGameDTO.getOpening_1xbet_odds();
                case "Bet365":
                    return betGameDTO.getOpening_b365_odds();
                case "Pinnacle":
                    return betGameDTO.getOpening_pinnacle_odds();
            }
        } else {
            switch (bookie){
                case "1xBet":
                    return betGameDTO.getLatest_1xbet_odds();
                case "Bet365":
                    return betGameDTO.getLatest_b365_odds();
                case "Pinnacle":
                    return betGameDTO.getLatest_pinnacle_odds();
            }
        }
        return 0.0;
    }

    private List<BetGameDTO> filterGames1xbet(TestRequest request, List<BetGameDTO> marketGames){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            if(request.isOpeningOdds()){
                Double openingOdds = game.getOpening_1xbet_odds();
                if(openingOdds!= null && openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(openingOdds, game.getOur_odds());
                    if(value != null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request)){
                            result.add(game);
                        }
                    }
                }
            } else {
                Double latestOdds = game.getLatest_1xbet_odds();
                if(latestOdds!= null && latestOdds >= request.getMinOdds() && latestOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(latestOdds, game.getOur_odds());
                    if(value!= null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request)){
                            result.add(game);
                        }
                    }
                }
            }
        }
        return result;
    }

    private List<BetGameDTO> filterGamesPinnacle(TestRequest request, List<BetGameDTO> marketGames){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            if(request.isOpeningOdds()){
                Double openingOdds = game.getOpening_pinnacle_odds();
                if(openingOdds!= null && openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(openingOdds, game.getOur_odds());
                    if(value != null &&
                    value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request)){
                            result.add(game);
                        }
                    }
                }

            } else {
                Double latestOdds = game.getLatest_pinnacle_odds();
                if(latestOdds!= null && latestOdds >= request.getMinOdds() && latestOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(latestOdds, game.getOur_odds());
                    if(value != null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request)){
                            result.add(game);
                        }
                    }
                }
            }
        }
        return result;
    }

    private List<BetGameDTO> filterGamesBet365(TestRequest request, List<BetGameDTO> marketGames){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            if(request.isOpeningOdds()){
                Double openingOdds = game.getOpening_b365_odds();
                if(openingOdds!= null && openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(openingOdds, game.getOur_odds());
                    if(value != null
                            && value>= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request)){
                            result.add(game);
                        }
                    }
                }

            } else {
                Double latestOdds = game.getLatest_b365_odds();
                if(latestOdds!= null && latestOdds >= request.getMinOdds() && latestOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(latestOdds, game.getOur_odds());
                    if(value != null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request)){
                            result.add(game);
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean criteriaMatch(BetGameDTO game, TestRequest request){
        return game.getProbability() != null
                && (game.getCompetition_progress() == null || game.getCompetition_progress() <= request.getMaxProgress())
                && game.getProbability() >= request.getMinProbability()
                && (request.isCountCups() || game.isCompetition_cup()==request.isCountCups())
                && (request.isCountFriendlies() || game.isCompetition_friendly()==request.isCountFriendlies());
    }

    private Double calculateValue(Double bookieOdds, Double ourOdds){
        Double diff = bookieOdds - ourOdds;
        return (diff/ourOdds) * 100.00;
    }
}

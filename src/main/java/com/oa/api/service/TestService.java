package com.oa.api.service;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.model.*;
import com.oa.api.util.BigDecimalRoundDoubleMain;
import com.oa.api.util.Bookmakers;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestService {

    private final static Logger log = Logger.getLogger(TestService.class);
    private double STAKE = 1.00;

    @Autowired
    private BetGameService betGameService;

    public MultipleTestResponse doMultipleTests(MultipleTestRequest testRequest){
        MultipleTestResponse response = new MultipleTestResponse();
        List<String> testedMarkets = new ArrayList<>();
        List<TestRequest> requestList = testRequest.getRequestList();
        requestList.forEach(request -> testedMarkets.add(request.getMarket()));
        List<BetGameDTO> marketGames = betGameService.getGamesByMarkets(testedMarkets);
        response.setMarkets(testedMarkets);
        response.setStartDate(testRequest.getStartDate());
        response.setEndDate(testRequest.getEndDate());

        List<BetGameDTO> gamesToTest = new ArrayList<>();
        Long unixStart = null;
        Long unixEnd = null;


        if(testRequest.getEndDate() != null && testRequest.getStartDate() != null){
            try{
                unixStart = convertDateToUnix(testRequest.getStartDate());
                unixEnd = convertDateToUnix(testRequest.getEndDate());
            } catch (ParseException e){
                log.error("Invalid date format");
            }
        }

        if(testRequest.getBookie().equals(Bookmakers.ONEXBET.getName())){
            gamesToTest = filterGames1xbetForMultipleMarkets(requestList, marketGames, unixStart, unixEnd);
        } else if(testRequest.getBookie().equals(Bookmakers.BET365.getName())){
            gamesToTest = filterGamesBet365ForMultipleMarkets(requestList, marketGames, unixStart, unixEnd);
        } else if (testRequest.getBookie().equals(Bookmakers.PINNACLE.getName())){
            gamesToTest = filterGamesPinnacleForMultipleMarkets(requestList, marketGames, unixStart, unixEnd);
        }


        log.info("Total market games " + marketGames.size());
        log.info("Total games to test " + gamesToTest.size());

        if(!gamesToTest.isEmpty()){
            executeMultipleTest(requestList, response, gamesToTest, testRequest.getBookie(), testRequest.getMyStake());
        }



        return response;
    }

    private void executeMultipleTest(List<TestRequest> requests, MultipleTestResponse response, List<BetGameDTO> gamesToTest, String bookie, Double myStake) {
        List<TestedGame> testedGames = new ArrayList<>();
        int wins = 0;
        int losses = 0;
        Double currentDrawdown = 0.0;
        Double maxDrawdown = 0.0;
        Double maxProfit = 0.0;
        Double profit = 0.0;

        Double staked = 0.0;
        Double returned = 0.0;
        int betNo = 1;

        for (BetGameDTO betGameDTO : gamesToTest) {
            for (TestRequest req : requests) {
                if (req.getMarket().equals(betGameDTO.getMarket())) {
                    if (req.getKellyCriteria() == 0.00) {
                        Double profitLoss = 0.0;
                        Double oddsToCalculate = calculateOdds(req.isOpeningOdds(), bookie, betGameDTO) * req.getOddsPercentage();
                        if (betGameDTO.getHome_played() >= req.getMinGamesPlayed() && betGameDTO.getAway_played() >= req.getMinGamesPlayed()) {
                            if (betGameDTO.isResult()) {
                                wins++;
                                profitLoss = oddsToCalculate * myStake - myStake;
                                returned = returned + (oddsToCalculate * myStake);
                            } else {
                                profitLoss = profitLoss - myStake;
                                losses++;
                            }

                            staked = staked + myStake;
                            profit = returned - staked;
                            maxProfit = profit > maxProfit ? profit : maxProfit;
                            currentDrawdown = profit >= maxProfit ? 0.0 : profit - maxProfit;
                            maxDrawdown = currentDrawdown < maxDrawdown ? currentDrawdown : maxDrawdown;
                            testedGames.add(createTestedGame(betNo,oddsToCalculate, myStake, betGameDTO.getMarket(),
                                    betGameDTO.getHome_name(), betGameDTO.getAway_name(),profit, profitLoss));
                            betNo++;
                        }
                    } else {
                        boolean isKellyFactorConsidered = req.getKellyFactor() != null && !req.getKellyFactor().isNaN()  && req.getKellyFactor() != 1;
                        if (betGameDTO.getHome_played() >= req.getMinGamesPlayed() && betGameDTO.getAway_played() >= req.getMinGamesPlayed()) {
                            Double profitLoss = 0.0;
                            Double kellyCriteriaCalc = calculateKellyFactorCalc(bookie, betGameDTO, req.isOpeningOdds(), 1.00);
                            if (kellyCriteriaCalc >= req.getKellyCriteria() && kellyCriteriaCalc <= req.getMaxKellyCriteria()) {
                                Double oddsToCalculate = calculateOdds(req.isOpeningOdds(), bookie, betGameDTO) * req.getOddsPercentage();
                                if(req.getOddsPercentage()!= 1.00){
                                    kellyCriteriaCalc = calculateKellyFactorCalc(bookie, betGameDTO, req.isOpeningOdds(), req.getOddsPercentage());
                                }
                                if(kellyCriteriaCalc >= req.getMinKellyCriteriaAccepted() && kellyCriteriaCalc <= req.getMaxKellyCriteria() && (!req.isCompareToPinnacle() || (req.isCompareToPinnacle() && checkPinnacle(betGameDTO, bookie, req.getMaxPinnaclePercentageOdds())))){
                                    Double amountStaked = isKellyFactorConsidered? myStake * ((kellyCriteriaCalc*100*req.getKellyFactor())) : myStake;
                                    if (betGameDTO.isResult()) {
                                        wins++;
                                        profitLoss = oddsToCalculate * amountStaked - amountStaked;
                                        returned = returned + (oddsToCalculate * amountStaked);
                                    } else {
                                        profitLoss = profitLoss - amountStaked;
                                        losses++;
                                    }
                                    staked = staked + amountStaked;
                                    profit = returned - staked;
                                    maxProfit = profit > maxProfit ? profit : maxProfit;
                                    currentDrawdown = profit >= maxProfit ? 0.0 : profit - maxProfit;
                                    maxDrawdown = currentDrawdown < maxDrawdown ? currentDrawdown : maxDrawdown;
                                    testedGames.add(createTestedGame(betNo,oddsToCalculate, amountStaked, betGameDTO.getMarket(),
                                            betGameDTO.getHome_name(), betGameDTO.getAway_name(),profit, profitLoss));
                                    betNo++;
                                }

                            }
                        }
                    }
                }
            }

        }


        response.setMaxDrawdown(BigDecimalRoundDoubleMain.roundDouble(maxDrawdown, 2));
        response.setMaxProfit(BigDecimalRoundDoubleMain.roundDouble(maxProfit, 2));
        response.setRoi(BigDecimalRoundDoubleMain.roundDouble((profit/staked) * 100, 2));
        response.setWins(wins);
        response.setLosses(losses);
        response.setProfit(BigDecimalRoundDoubleMain.roundDouble(profit, 2));
        response.setNumBets(wins + losses);
        response.setHitRate(wins > 0 ? BigDecimalRoundDoubleMain.roundDouble(((double) wins / response.getNumBets()) * 100, 2) : 0.00);
        response.setTestedGames(testedGames);
    }


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
        response.setCountry(request.getCountry());
        response.setMaxProbability(request.getMaxProbability());
        response.setKellyCriteria(request.getKellyCriteria());
        response.setMaxKellyCriteria(request.getMaxKellyCriteria());
        response.setKellyFactor(request.getKellyFactor());
        response.setMyStake(request.getMyStake());
        response.setStartDate(request.getStartDate());
        response.setEndDate(request.getEndDate());
        response.setOddsPercentage(request.getOddsPercentage());
        List<BetGameDTO> marketGames = betGameService.getGamesByMarket(request.getMarket());
        List<BetGameDTO> gamesToTest = new ArrayList<>();
        Long unixStart = null;
        Long unixEnd = null;

        if(request.getEndDate() != null && request.getStartDate() != null){
            try{
                unixStart = convertDateToUnix(request.getStartDate());
                unixEnd = convertDateToUnix(request.getEndDate());
            } catch (ParseException e){
                log.error("Invalid date format");
            }
        }


        if(request.getBookie().equals(Bookmakers.ONEXBET.getName())){
            gamesToTest = filterGames1xbet(request, marketGames, unixStart, unixEnd);
        } else if(request.getBookie().equals(Bookmakers.BET365.getName())){
            gamesToTest = filterGamesBet365(request, marketGames, unixStart, unixEnd);
        } else if (request.getBookie().equals(Bookmakers.PINNACLE.getName())){
            gamesToTest = filterGamesPinnacle(request, marketGames, unixStart, unixEnd);
        }

        log.info("Total market games " + marketGames.size());
        log.info("Total games to test " + gamesToTest.size());
        if(!gamesToTest.isEmpty()){
            executeTest(response, gamesToTest, request.isOpeningOdds(), request.getBookie(), request.getKellyCriteria(), request.getMaxKellyCriteria(),
                    request.getKellyFactor(),request.getMyStake(),request.getMinGamesPlayed(), request.getOddsPercentage(), request.isCompareToPinnacle(),
                    request.getMinKellyCriteriaAccepted(), request.getMaxPinnaclePercentageOdds());
        }
        return response;
    }

    private Double calculateKellyCriteria1xBet(BetGameDTO betGameDTO, boolean opening){
        Double b_decimal_odds = (opening ? betGameDTO.getOpening_1xbet_odds() : betGameDTO.getLatest_1xbet_odds()) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyCriteriaBet365(BetGameDTO betGameDTO, boolean opening){
        Double b_decimal_odds = (opening ? betGameDTO.getOpening_b365_odds() : betGameDTO.getLatest_b365_odds()) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyCriteriaPinnacle(BetGameDTO betGameDTO, boolean opening){
        Double b_decimal_odds = (opening ? betGameDTO.getOpening_pinnacle_odds() : betGameDTO.getLatest_pinnacle_odds()) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyCriteria1xBetWithPercentage(BetGameDTO betGameDTO, boolean opening, Double oddsPercentage){
        Double b_decimal_odds = (opening ? (betGameDTO.getOpening_1xbet_odds()*oddsPercentage) : (betGameDTO.getLatest_1xbet_odds()*oddsPercentage)) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyCriteriaBet365WithPercentage(BetGameDTO betGameDTO, boolean opening, Double oddsPercentage){
        Double b_decimal_odds = (opening ? (betGameDTO.getOpening_b365_odds()*oddsPercentage) : (betGameDTO.getLatest_b365_odds()*oddsPercentage)) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyCriteriaPinnacleWithPercentage(BetGameDTO betGameDTO, boolean opening, Double oddsPercentage){
        Double b_decimal_odds = (opening ? (betGameDTO.getOpening_pinnacle_odds() * oddsPercentage) : (betGameDTO.getLatest_pinnacle_odds()*oddsPercentage)) -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKelly(BetGameDTO betGameDTO, Double bDecimalOdds){
        Double p_win = betGameDTO.getProbability()/100;
        Double q_lose = 1.00 - p_win;
        return ((bDecimalOdds*p_win)-q_lose)/bDecimalOdds;
    }

    private TestedGame createTestedGame(int betNo,Double odds, Double stake, String market,String home, String away, Double currentProfit, Double profitLoss){
        return new TestedGame(betNo,home, away,market, BigDecimalRoundDoubleMain.roundDouble(odds,2), BigDecimalRoundDoubleMain.roundDouble(stake, 2), BigDecimalRoundDoubleMain.roundDouble(profitLoss, 2), BigDecimalRoundDoubleMain.roundDouble(currentProfit, 2));

    }

    private void executeTest(TestResponse response, List<BetGameDTO> gamesToTest, boolean opening, String bookie, Double kellyCriteria, Double maxKellyCriteria, Double kellyFactor, Double myStake,int minGamesPlayed, Double oddsPercentage, boolean compareToPinnacle, Double minKCAccepted, Double pinnyPercentageOdds){
        List<TestedGame> testedGames = new ArrayList<>();
        int wins = 0;
        int losses = 0;
        Double currentDrawdown = 0.0;
        Double maxDrawdown = 0.0;
        Double maxProfit = 0.0;
        Double profit = 0.0;

        Double staked = 0.0;
        Double returned = 0.0;
        int betNo = 1;


        if(kellyCriteria == 0.00){
            for(BetGameDTO betGameDTO : gamesToTest){
                Double profitLoss = 0.0;
                if(betGameDTO.getHome_played() >= minGamesPlayed && betGameDTO.getAway_played() >= minGamesPlayed && (!compareToPinnacle || (compareToPinnacle && checkPinnacle(betGameDTO, bookie,pinnyPercentageOdds)))){
                    Double oddsToCalculate = calculateOdds(opening,bookie, betGameDTO) * oddsPercentage;
                    if(betGameDTO.isResult()){
                        wins++;
                        profitLoss = oddsToCalculate * myStake - myStake;
                        returned = returned + (oddsToCalculate * myStake);
                    } else {
                        profitLoss = profitLoss - myStake;
                        losses++;
                    }
                    staked = staked + myStake;
                    profit = returned - staked;
                    maxProfit = profit > maxProfit ? profit : maxProfit;
                    currentDrawdown = profit >= maxProfit ? 0.0 : profit - maxProfit;
                    maxDrawdown = currentDrawdown < maxDrawdown ? currentDrawdown : maxDrawdown;
                    if(compareToPinnacle){
                        if(checkPinnacle(betGameDTO, bookie, pinnyPercentageOdds)){
                            testedGames.add(createTestedGame(betNo,oddsToCalculate, myStake, betGameDTO.getMarket(),
                                    betGameDTO.getHome_name(), betGameDTO.getAway_name(),profit, profitLoss));
                            betNo++;
                        }
                    } else {
                        testedGames.add(createTestedGame(betNo,oddsToCalculate, myStake, betGameDTO.getMarket(),
                                betGameDTO.getHome_name(), betGameDTO.getAway_name(),profit, profitLoss));
                        betNo++;
                    }

                }
            }
        } else {
            boolean isKellyFactorConsidered = kellyFactor != null && !kellyFactor.isNaN()  && kellyFactor != 1;
            for(BetGameDTO betGameDTO : gamesToTest){
                if(betGameDTO.getHome_played() >= minGamesPlayed && betGameDTO.getAway_played() >= minGamesPlayed) {
                    Double profitLoss = 0.0;
                    Double kellyCriteriaCalc = calculateKellyFactorCalc(bookie, betGameDTO, opening,1.00);
                    if (kellyCriteriaCalc >= kellyCriteria && kellyCriteriaCalc <= maxKellyCriteria) {
                        Double oddsToCalculate = calculateOdds(opening, bookie, betGameDTO) * oddsPercentage;
                        if(oddsPercentage!= 1.00){
                            kellyCriteriaCalc = calculateKellyFactorCalc(bookie, betGameDTO, opening,oddsPercentage);
                        }
                        if (kellyCriteriaCalc >= (minKCAccepted) && kellyCriteriaCalc <= maxKellyCriteria && (!compareToPinnacle || (compareToPinnacle && checkPinnacle(betGameDTO, bookie, pinnyPercentageOdds)))) {
                            Double amountStaked = isKellyFactorConsidered? myStake * ((kellyCriteriaCalc*100*kellyFactor)) : myStake;
                            if (betGameDTO.isResult()) {
                                wins++;
                                profitLoss = oddsToCalculate * amountStaked - amountStaked;
                                returned = returned + (oddsToCalculate * amountStaked);
                            } else {
                                profitLoss = profitLoss - amountStaked;
                                losses++;
                            }
                            staked = staked + amountStaked;
                            profit = returned - staked;
                            maxProfit = profit > maxProfit ? profit : maxProfit;
                            currentDrawdown = profit >= maxProfit ? 0.0 : profit - maxProfit;
                            maxDrawdown = currentDrawdown < maxDrawdown ? currentDrawdown : maxDrawdown;
                            testedGames.add(createTestedGame(betNo,oddsToCalculate, amountStaked, betGameDTO.getMarket(),
                                    betGameDTO.getHome_name(), betGameDTO.getAway_name(),profit, profitLoss));
                            betNo++;

                        }
                    }
                }
            }
        }

        response.setMaxDrawdown(BigDecimalRoundDoubleMain.roundDouble(maxDrawdown, 2));
        response.setMaxProfit(BigDecimalRoundDoubleMain.roundDouble(maxProfit,2));
        response.setRoi(BigDecimalRoundDoubleMain.roundDouble((profit/staked) * 100, 2));
        response.setAvgStake(BigDecimalRoundDoubleMain.roundDouble((staked)/(wins+losses), 2));
        response.setWins(wins);
        response.setLosses(losses);
        response.setProfit(BigDecimalRoundDoubleMain.roundDouble(profit,2));
        response.setNumBets(wins+losses);
        response.setHitRate(wins > 0 ? BigDecimalRoundDoubleMain.roundDouble(((double)wins / response.getNumBets()) * 100,2) : 0.00);
        response.setTestedGames(testedGames);

    }

    private boolean checkPinnacle(BetGameDTO betGameDTO, String bookie, Double pinnaclePercentage){

        boolean isPinnacleHigher1xOrNull = betGameDTO.getOpening_1xbet_odds() != null && (betGameDTO.getOpening_pinnacle_odds() == null || (betGameDTO.getOpening_pinnacle_odds() <= (betGameDTO.getOpening_1xbet_odds()*pinnaclePercentage)));
        boolean isPinnacleHigherOrNull = betGameDTO.getOpening_b365_odds() != null && (betGameDTO.getOpening_pinnacle_odds() == null || (betGameDTO.getOpening_pinnacle_odds() <= (betGameDTO.getOpening_b365_odds()*pinnaclePercentage)));
        boolean isPinnacleHigherOr365Null = betGameDTO.getOpening_b365_odds() != null && betGameDTO.getOpening_pinnacle_odds() != null &&(betGameDTO.getOpening_pinnacle_odds() <= (betGameDTO.getOpening_b365_odds()*pinnaclePercentage));
        if(bookie.equals(Bookmakers.BET365.getName()) && (isPinnacleHigherOrNull)){
            return true;
        } else if((bookie.equals(Bookmakers.PINNACLE.getName()) && (isPinnacleHigherOr365Null))){
            return true;
        } else if(bookie.equals(Bookmakers.ONEXBET.getName()) && (isPinnacleHigher1xOrNull)){
            return true;
        }
        return false;
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
                    return betGameDTO.getPeak_1xbet_odds();
                case "Bet365":
                    return betGameDTO.getPeak_b365_odds();
                case "Pinnacle":
                    return betGameDTO.getPeak_pinnacle_odds();
            }
        }
        return 0.0;
    }

    private List<BetGameDTO> filterGames1xbet(TestRequest request, List<BetGameDTO> marketGames, Long start, Long end){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            if(request.isOpeningOdds()){
                Double openingOdds = game.getOpening_1xbet_odds();
                if(openingOdds!= null && openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(openingOdds, game.getOur_odds());
                    if(value != null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request, start, end)){
                            result.add(game);
                        }
                    }
                }
            } else {
                Double latestOdds = game.getPeak_1xbet_odds();
                if(latestOdds!= null && latestOdds >= request.getMinOdds() && latestOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(latestOdds, game.getOur_odds());
                    if(value!= null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request, start, end)){
                            result.add(game);
                        }
                    }
                }
            }
        }
        return result;
    }

    private List<BetGameDTO> filterGames1xbetForMultipleMarkets(List<TestRequest> requests, List<BetGameDTO> marketGames, Long start, Long end){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            for(TestRequest req : requests){
                if(req.getMarket().equals(game.getMarket())){
                    if(req.isOpeningOdds()){
                        Double openingOdds = game.getOpening_1xbet_odds();
                        if(openingOdds!= null && openingOdds >= req.getMinOdds() && openingOdds <= req.getMaxOdds()) {
                            Double value = calculateValue(openingOdds, game.getOur_odds());
                            if(value != null
                                    && value >= req.getMinValue()
                                    && value <= req.getMaxValue()){
                                if(criteriaMatch(game, req, start, end)){
                                    result.add(game);
                                }
                            }
                        }
                    } else {
                        Double latestOdds = game.getLatest_1xbet_odds();
                        if(latestOdds!= null && latestOdds >= req.getMinOdds() && latestOdds <= req.getMaxOdds()) {
                            Double value = calculateValue(latestOdds, game.getOur_odds());
                            if(value!= null
                                    && value >= req.getMinValue()
                                    && value <= req.getMaxValue()){
                                if(criteriaMatch(game, req, start, end)){
                                    result.add(game);
                                }
                            }
                        }
                    }
                }
            }

        }
        return result;
    }

    private List<BetGameDTO> filterGamesPinnacleForMultipleMarkets(List<TestRequest> requests, List<BetGameDTO> marketGames, Long start, Long end){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            for(TestRequest req : requests){
                if(req.getMarket().equals(game.getMarket())){
                    if(req.isOpeningOdds()){
                        Double openingOdds = game.getOpening_pinnacle_odds();
                        if(openingOdds!= null && openingOdds >= req.getMinOdds() && openingOdds <= req.getMaxOdds()) {
                            Double value = calculateValue(openingOdds, game.getOur_odds());
                            if(value != null
                                    && value >= req.getMinValue()
                                    && value <= req.getMaxValue()){
                                if(criteriaMatch(game, req, start, end)){
                                    result.add(game);
                                }
                            }
                        }
                    } else {
                        Double latestOdds = game.getLatest_pinnacle_odds();
                        if(latestOdds!= null && latestOdds >= req.getMinOdds() && latestOdds <= req.getMaxOdds()) {
                            Double value = calculateValue(latestOdds, game.getOur_odds());
                            if(value!= null
                                    && value >= req.getMinValue()
                                    && value <= req.getMaxValue()){
                                if(criteriaMatch(game, req, start, end)){
                                    result.add(game);
                                }
                            }
                        }
                    }
                }
            }

        }
        return result;
    }

    private List<BetGameDTO> filterGamesBet365ForMultipleMarkets(List<TestRequest> requests, List<BetGameDTO> marketGames, Long start, Long end){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            for(TestRequest req : requests){
                if(req.getMarket().equals(game.getMarket())){
                    if(req.isOpeningOdds()){
                        Double openingOdds = game.getOpening_b365_odds();
                        if(openingOdds!= null && openingOdds >= req.getMinOdds() && openingOdds <= req.getMaxOdds()) {
                            Double value = calculateValue(openingOdds, game.getOur_odds());
                            if(value != null
                                    && value >= req.getMinValue()
                                    && value <= req.getMaxValue()){
                                if(criteriaMatch(game, req, start, end)){
                                    result.add(game);
                                }
                            }
                        }
                    } else {
                        Double latestOdds = game.getLatest_b365_odds();
                        if(latestOdds!= null && latestOdds >= req.getMinOdds() && latestOdds <= req.getMaxOdds()) {
                            Double value = calculateValue(latestOdds, game.getOur_odds());
                            if(value!= null
                                    && value >= req.getMinValue()
                                    && value <= req.getMaxValue()){
                                if(criteriaMatch(game, req, start, end)){
                                    result.add(game);
                                }
                            }
                        }
                    }
                }
            }

        }
        return result;
    }



    private List<BetGameDTO> filterGamesPinnacle(TestRequest request, List<BetGameDTO> marketGames, Long start, Long end){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            if(request.isOpeningOdds()){
                Double openingOdds = game.getOpening_pinnacle_odds();
                if(openingOdds!= null && openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(openingOdds, game.getOur_odds());
                    if(value != null &&
                    value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request, start, end)){
                            result.add(game);
                        }
                    }
                }

            } else {
                Double latestOdds = game.getPeak_pinnacle_odds();
                if(latestOdds!= null && latestOdds >= request.getMinOdds() && latestOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(latestOdds, game.getOur_odds());
                    if(value != null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request, start, end)){
                            result.add(game);
                        }
                    }
                }
            }
        }
        return result;
    }

    private List<BetGameDTO> filterGamesBet365(TestRequest request, List<BetGameDTO> marketGames, Long start, Long end){
        List <BetGameDTO> result = new ArrayList<>();
        for(BetGameDTO game : marketGames){
            if(request.isOpeningOdds()){
                Double openingOdds = game.getOpening_b365_odds();
                if(openingOdds!= null && openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(openingOdds, game.getOur_odds());
                    if(value != null
                            && value>= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request, start, end)){
                            result.add(game);
                        }
                    }
                }

            } else {
                Double peakOdds = game.getPeak_b365_odds();
                if(peakOdds!= null && peakOdds >= request.getMinOdds() && peakOdds <= request.getMaxOdds()) {
                    Double value = calculateValue(peakOdds, game.getOur_odds());
                    if(value != null
                            && value >= request.getMinValue()
                            && value <= request.getMaxValue()){
                        if(criteriaMatch(game, request, start, end)){
                            result.add(game);
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean criteriaMatch(BetGameDTO game, TestRequest request, Long start, Long end){
        List<String> pred = new ArrayList<>();
        if(request.getPredictability() != null){
            pred = Arrays.stream(request.getPredictability().split(",")).collect(Collectors.toList());
        }


        return game.getProbability() != null
                && (game.getCompetition_progress() == null || game.getCompetition_progress() <= request.getMaxProgress())
                && (game.getProbability() >= request.getMinProbability() && game.getProbability() <= request.getMaxProbability())
                && (request.isCountCups() || game.isCompetition_cup()==request.isCountCups())
                && (request.isCountFriendlies() || game.isCompetition_friendly()==request.isCountFriendlies())
                && ((request.getCountry() == null || request.getCountry().isEmpty()) || request.getCountry().equalsIgnoreCase(game.getCompetition_country()))
                && (pred.isEmpty() || pred.contains(game.getCompetition_predictability()))
                && validDate(game, start, end);
    }

    private boolean validDate(BetGameDTO game, Long start, Long end){
        boolean isNull = start == null || end == null;

        if(!isNull){
            return game.getUnix().compareTo(start) >= 0 && game.getUnix().compareTo(end) <= 0;
        } else {
            return true;
        }
    }

    private Double calculateValue(Double bookieOdds, Double ourOdds){
        Double diff = bookieOdds - ourOdds;
        return (diff/ourOdds) * 100.00;
    }

    private Long convertDateToUnix(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateConverted = dateFormat.parse(date);
        log.info("Date converted: " + date);
        return dateConverted.getTime() / 1000;

    }

    private Double calculateKellyFactorCalc(String bookie, BetGameDTO betGameDTO, boolean opening, Double oddsPercentage){
        Double kellyCriteriaCalc = 0.0;
        if(oddsPercentage == 1.00){
            if (bookie.equals(Bookmakers.ONEXBET.getName())) {
                kellyCriteriaCalc = calculateKellyCriteria1xBet(betGameDTO, opening);
            } else if (bookie.equals(Bookmakers.BET365.getName())) {
                kellyCriteriaCalc = calculateKellyCriteriaBet365(betGameDTO, opening);
            } else if (bookie.equals(Bookmakers.PINNACLE.getName())) {
                kellyCriteriaCalc = calculateKellyCriteriaPinnacle(betGameDTO, opening);
            }
        } else {
            if (bookie.equals(Bookmakers.ONEXBET.getName())) {
                kellyCriteriaCalc = calculateKellyCriteria1xBetWithPercentage(betGameDTO, opening, oddsPercentage);
            } else if (bookie.equals(Bookmakers.BET365.getName())) {
                kellyCriteriaCalc = calculateKellyCriteriaBet365WithPercentage(betGameDTO, opening,oddsPercentage);
            } else if (bookie.equals(Bookmakers.PINNACLE.getName())) {
                kellyCriteriaCalc = calculateKellyCriteriaPinnacleWithPercentage(betGameDTO, opening, oddsPercentage);
            }
        }

        return kellyCriteriaCalc;
    }
}

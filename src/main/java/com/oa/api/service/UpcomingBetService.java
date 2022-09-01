package com.oa.api.service;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.entity.RegisteredBetDTO;
import com.oa.api.model.FilterRequest;
import com.oa.api.model.RegisteredBet;
import com.oa.api.model.UpcomingBet;
import com.oa.api.repository.BetGameRepository;
import com.oa.api.repository.RegisteredBetRepository;
import com.oa.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UpcomingBetService {

    @Autowired
    private RegisteredBetRepository registeredBetRepository;

    private BetGameConverter betGameConverter = new BetGameConverter();

    public List<RegisteredBetDTO> saveAll(List<RegisteredBet> bets){
        List <RegisteredBetDTO> convertedBets = new ArrayList<>();
        for(RegisteredBet bet : bets){
            convertedBets.add(betGameConverter.convertRBModelToRBDTO(bet));
        }
        return (List<RegisteredBetDTO>) registeredBetRepository.saveAll(convertedBets);
    }

    private Double calculateLatestOdds(String bookie, BetGameDTO betGameDTO){
            switch (bookie){
                case "1xBet":
                    return betGameDTO.getLatest_1xbet_odds();
                case "Bet365":
                    return betGameDTO.getLatest_b365_odds();
                case "Pinnacle":
                    return betGameDTO.getLatest_pinnacle_odds();
            }

        return 0.0;
    }

    private Double calculateOpeningOdds(String bookie, BetGameDTO betGameDTO){
        switch (bookie){
            case "1xBet":
                return betGameDTO.getOpening_1xbet_odds();
            case "Bet365":
                return betGameDTO.getOpening_b365_odds();
            case "Pinnacle":
                return betGameDTO.getOpening_pinnacle_odds();
        }

        return 0.0;
    }

    public List<UpcomingBet> executeFiltering(FilterRequest request, List<HashMap<String, Object>> result){
        List<BetGameDTO> upcomingGames = new ArrayList<>();
        for(HashMap<String, Object> betGame : result){
            upcomingGames.add(betGameConverter.converModeltoDTO(betGame));
        }

        List<UpcomingBet> upcomingBets = new ArrayList<>();
        Double kellyFactor = request.getKellyFactor();
        String bookie = request.getBookie();

        upcomingGames = upcomingGames.stream().filter(betGameDTO -> betGameDTO.getMarket().equals(request.getMarket())).collect(Collectors.toList());

        if(kellyFactor == 0.00){
            for(BetGameDTO betGameDTO : upcomingGames){
                bookie = request.getBookie();
                if(criteriaMatch(betGameDTO,request)){
                    Double currentOdds = calculateLatestOdds(request.getBookie(), betGameDTO);
                    Double openingOdds = calculateOpeningOdds(request.getBookie(), betGameDTO);
                    if(request.getBookie().equals(Bookmakers.ONEXBET.getName()) && (currentOdds == null || currentOdds == 0.0)){
                        currentOdds = calculateLatestOdds(Bookmakers.BET365.getName(), betGameDTO);
                        openingOdds = calculateOpeningOdds(Bookmakers.BET365.getName(), betGameDTO);
                        bookie = Bookmakers.BET365.getName();
                    }
                    if(currentOdds != null && currentOdds > 0.00){
                        Double value = calculateValue(currentOdds, betGameDTO.getOur_odds());
                        if(matchConditions(currentOdds, value, kellyFactor, request)){
                            upcomingBets.add(convertBetGameToUpcoming(betGameDTO, currentOdds, openingOdds,value, kellyFactor, bookie));
                        }
                    }

                }
            }
        } else {
            for(BetGameDTO betGameDTO : upcomingGames){
                bookie = request.getBookie();
                Double kellyFactorCalc = 0.0;
                Double currentOdds = calculateLatestOdds(request.getBookie(), betGameDTO);
                Double openingOdds = calculateOpeningOdds(request.getBookie(), betGameDTO);
                if(request.getBookie().equals(Bookmakers.ONEXBET.getName()) && (currentOdds == null || currentOdds == 0.0)){
                    currentOdds = calculateLatestOdds(Bookmakers.BET365.getName(), betGameDTO);
                    openingOdds = calculateOpeningOdds(Bookmakers.BET365.getName(), betGameDTO);
                    bookie = Bookmakers.BET365.getName();
                }
                if(currentOdds != null && currentOdds > 0.00){
                    if(bookie.equals(Bookmakers.ONEXBET.getName())){
                        kellyFactorCalc = calculateKellyFactor1xBet(betGameDTO);
                    } else if(bookie.equals(Bookmakers.BET365.getName())){
                        kellyFactorCalc = calculateKellyFactorBet365(betGameDTO);
                    } else if(bookie.equals(Bookmakers.PINNACLE.getName())){
                        kellyFactorCalc = calculateKellyFactorPinnacle(betGameDTO);
                    }
                    if(criteriaMatch(betGameDTO,request)){

                            Double value = calculateValue(currentOdds, betGameDTO.getOur_odds());
                            if(matchConditions(currentOdds, value, kellyFactorCalc, request) && bookie.equals(request.getBookie())){
                                upcomingBets.add(convertBetGameToUpcoming(betGameDTO, currentOdds, openingOdds, value, kellyFactorCalc, bookie));
                            }
                        }
                }
            }
        }

        for(UpcomingBet upcomingBet : upcomingBets){
            upcomingBet.setDiffMovement(oddsMovement(upcomingBet));
        }

        List<RegisteredBet> betsToSave = calculateBetsToSave(upcomingBets);
        saveAll(betsToSave);
        return upcomingBets;
    }

    private List <RegisteredBet> calculateBetsToSave(List <UpcomingBet> upcomingBets){
        List <RegisteredBet> result = new ArrayList<>();

        for(UpcomingBet upcomingBet : upcomingBets){
            if((upcomingBet.getDiffMovement() <= 10.00 && upcomingBet.getMarket().equals(MarketMapper.HOME.getName()))
                || (upcomingBet.getDiffMovement() <= 5.00 && upcomingBet.getMarket().equals(MarketMapper.O25.getName()))
                || (upcomingBet.getMarket().equals(MarketMapper.U25.getName()))
                || (upcomingBet.getDiffMovement() <= 10.00 && upcomingBet.getMarket().equals(MarketMapper.AWAY.getName()))){
                result.add(new RegisteredBet(upcomingBet.getId(),MarketMapper.getKeyByName(upcomingBet.getMarket())));
            }
        }

        return result;
    }

    private Double oddsMovement(UpcomingBet upcomingBet){
        Double opening = upcomingBet.getOpeningOdds();
        Double current = upcomingBet.getBookieOdds();
        Double diff = current - opening;
        return BigDecimalRoundDoubleMain.roundDouble(((diff/opening)*100),2);
    }

    private UpcomingBet convertBetGameToUpcoming(BetGameDTO betGameDTO, Double odds, Double opening,Double value, Double kellyFactor, String bookie){
        UpcomingBet bet = new UpcomingBet();
        bet.setHomeTeam(betGameDTO.getHome_name());
        bet.setAwayTeam(betGameDTO.getAway_name());
        bet.setOurOdds(betGameDTO.getOur_odds());
        bet.setBookieOdds(odds);
        bet.setValue(value);
        bet.setKellyFactor(kellyFactor);
        bet.setProbability(betGameDTO.getProbability());
        bet.setMarket(MarketMapper.getNameByKey(betGameDTO.getMarket()));
        bet.setId(String.valueOf(betGameDTO.getGame_id()));
        bet.setDateKO(betGameDTO.getKo_human());
        bet.setOpeningOdds(opening);
        bet.setBookmaker(bookie);
        bet.setCompetition(betGameDTO.getCompetition_country() + " - " + betGameDTO.getCompetition_name());
        return bet;
    }

    private Double calculateValue(Double bookieOdds, Double ourOdds){
        Double diff = bookieOdds - ourOdds;
        return (diff/ourOdds) * 100.00;
    }

    private boolean matchConditions(Double odds, Double value, Double kellyFactor, FilterRequest request){
        boolean oddsValue = odds >= request.getMinOdds() && odds <= request.getMaxOdds()
                && value >= request.getMinValue() && value <= request.getMaxValue();

        boolean kelly = kellyFactor == 0.00 || kellyFactor >= request.getKellyFactor();

        return oddsValue && kelly;

    }

    private Double calculateKellyFactor1xBet(BetGameDTO betGameDTO){
        Double b_decimal_odds = betGameDTO.getLatest_1xbet_odds() -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyFactorBet365(BetGameDTO betGameDTO){
        Double b_decimal_odds = betGameDTO.getLatest_b365_odds() -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyFactorPinnacle(BetGameDTO betGameDTO){
        Double b_decimal_odds = betGameDTO.getLatest_pinnacle_odds() -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKelly(BetGameDTO betGameDTO, Double bDecimalOdds){
        Double p_win = betGameDTO.getProbability()/100;
        Double q_lose = 1.00 - p_win;
        return ((bDecimalOdds*p_win)-q_lose)/bDecimalOdds;
    }

    private boolean criteriaMatch(BetGameDTO game, FilterRequest request){
        return game.getProbability() != null
                && !isRegistered(game)
                && (game.getHome_played()>=request.getMinGamesPlayed() && game.getAway_played() >= request.getMinGamesPlayed())
                && game.getProbability() >= request.getMinProbability()
                && (request.isCountCups() || game.isCompetition_cup()==request.isCountCups())
                && (request.isCountFriendlies() || game.isCompetition_friendly()==request.isCountFriendlies());
    }

    private boolean isRegistered(BetGameDTO game){
      Long id = Long.valueOf(game.getGame_id() + Market.getIdByName(game.getMarket()).getId());
      return registeredBetRepository.findById(id).isPresent();

    }


}

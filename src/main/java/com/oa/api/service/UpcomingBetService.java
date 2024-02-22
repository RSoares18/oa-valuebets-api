package com.oa.api.service;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.entity.RegisteredBetDTO;
import com.oa.api.model.FilterRequest;
import com.oa.api.model.RegisteredBet;
import com.oa.api.model.UpcomingBet;
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

    private final static Double BANKROLL = 1750.0;
    private final static Double KELLY_FRACTIONAL = 0.04;

    String bookie;

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

        Set<String> requestPredict = new HashSet<>(Arrays.asList(request.getPredictability().split(",")));

        upcomingGames = upcomingGames.stream().filter(betGameDTO -> (betGameDTO.getMarket().equals(request.getMarket()) && requestPredict.contains(betGameDTO.getCompetition_predictability()))).collect(Collectors.toList());

        if(kellyFactor == 0.00){
            Double openingKellyFactor = kellyFactor;
            for(BetGameDTO betGameDTO : upcomingGames){
                bookie = request.getBookie();
                if(criteriaMatch(betGameDTO,request)){
                    Double currentOdds = calculateLatestOdds(request.getBookie(), betGameDTO);
                    Double openingOdds = calculateOpeningOdds(request.getBookie(), betGameDTO);
                    Double opening365 = calculateOpeningOdds(Bookmakers.BET365.getName(), betGameDTO);
                    Double openingPinnacle = calculateOpeningOdds(Bookmakers.PINNACLE.getName(), betGameDTO);
                    boolean is1xBet = request.getBookie().equals(Bookmakers.ONEXBET.getName());
                    boolean is1xBetOddsNull = is1xBet && (currentOdds == null || currentOdds == 0.0);
                    boolean is365Better = false;
                    boolean isPinnacleBetter = false;

                    if(!is1xBetOddsNull){
                        is365Better = opening365 != null && openingOdds != null && opening365 >= openingOdds;
                        isPinnacleBetter = openingPinnacle != null && openingOdds != null && openingPinnacle >= openingOdds;
                    }

                    if((is1xBet && (currentOdds == null || currentOdds == 0.0)) || is365Better){
                        currentOdds = calculateLatestOdds(Bookmakers.BET365.getName(), betGameDTO);
                        openingOdds = calculateOpeningOdds(Bookmakers.BET365.getName(), betGameDTO);
                        bookie = Bookmakers.BET365.getName();
                    } else if (isPinnacleBetter){
                        currentOdds = calculateLatestOdds(Bookmakers.PINNACLE.getName(), betGameDTO);
                        openingOdds = calculateOpeningOdds(Bookmakers.PINNACLE.getName(), betGameDTO);
                        bookie = Bookmakers.PINNACLE.getName();
                    }
                    if(currentOdds != null && currentOdds > 0.00){
                        Double value = calculateValue(currentOdds, betGameDTO.getOur_odds());
                        if(matchConditions(openingOdds, currentOdds,value, kellyFactor, openingKellyFactor, request)){
                            upcomingBets.add(convertBetGameToUpcoming(betGameDTO, currentOdds, openingOdds,value, kellyFactor,openingKellyFactor, bookie, request.getMinKellyFactor()));
                        }
                    }

                }
            }
        } else {
            for(BetGameDTO betGameDTO : upcomingGames){
                bookie = request.getBookie();
                Double kellyFactorCalc = 0.0;
                Double openingKellyFactorCalc = 0.0;
                Double currentOdds = calculateLatestOdds(request.getBookie(), betGameDTO);
                Double openingOdds = calculateOpeningOdds(request.getBookie(), betGameDTO);
                Double opening365 = calculateOpeningOdds(Bookmakers.BET365.getName(), betGameDTO);
                Double openingPinnacle = calculateOpeningOdds(Bookmakers.PINNACLE.getName(), betGameDTO);
                boolean is1xBet = request.getBookie().equals(Bookmakers.ONEXBET.getName());
                boolean is1xBetOddsNull = currentOdds == null || currentOdds == 0.0;
                boolean is365Better = false;
                boolean isPinnacleBetter = false;

                if(is1xBet && !is1xBetOddsNull){
                    is365Better = opening365 != null && openingOdds != null && opening365 >= openingOdds;
                    isPinnacleBetter = openingPinnacle != null && openingOdds != null && openingPinnacle >= openingOdds;
                }

                if((is1xBet && (currentOdds == null || currentOdds == 0.0)) || is365Better){
                    currentOdds = calculateLatestOdds(Bookmakers.BET365.getName(), betGameDTO);
                    openingOdds = calculateOpeningOdds(Bookmakers.BET365.getName(), betGameDTO);
                    bookie = Bookmakers.BET365.getName();
                } else if (isPinnacleBetter){
                    currentOdds = calculateLatestOdds(Bookmakers.PINNACLE.getName(), betGameDTO);
                    openingOdds = calculateOpeningOdds(Bookmakers.PINNACLE.getName(), betGameDTO);
                    bookie = Bookmakers.PINNACLE.getName();
                }
                if(currentOdds != null && currentOdds > 0.00){
                    if(bookie.equals(Bookmakers.ONEXBET.getName())){
                        openingKellyFactorCalc = calculateKellyFactor1xBet(betGameDTO, true);
                    } else if(bookie.equals(Bookmakers.BET365.getName())){
                        openingKellyFactorCalc = calculateKellyFactorBet365(betGameDTO, true);
                    } else if(bookie.equals(Bookmakers.PINNACLE.getName())){
                        openingKellyFactorCalc = calculateKellyFactorPinnacle(betGameDTO, true);
                    }
                    if(bookie.equals(Bookmakers.ONEXBET.getName())){
                        kellyFactorCalc = calculateKellyFactor1xBet(betGameDTO, false);
                    } else if(bookie.equals(Bookmakers.BET365.getName())){
                        kellyFactorCalc = calculateKellyFactorBet365(betGameDTO,false);
                    } else if(bookie.equals(Bookmakers.PINNACLE.getName())){
                        kellyFactorCalc = calculateKellyFactorPinnacle(betGameDTO,false);
                    }
                    if(criteriaMatch(betGameDTO,request)){

                            Double value = calculateValue(currentOdds, betGameDTO.getOur_odds());
                            if(matchConditions(openingOdds, currentOdds,value, kellyFactorCalc, openingKellyFactorCalc, request) && checkPinnacle(openingOdds, bookie, betGameDTO.getOpening_b365_odds(), betGameDTO.getOpening_1xbet_odds(),request.getMarket())){
                                upcomingBets.add(convertBetGameToUpcoming(betGameDTO, currentOdds, openingOdds, value, kellyFactorCalc, openingKellyFactorCalc,bookie,request.getMinKellyFactor()));
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
            if((upcomingBet.getMarket().equals(MarketMapper.HOME.getName()))
                || (upcomingBet.getMarket().equals(MarketMapper.O25.getName()))
                || (upcomingBet.getMarket().equals(MarketMapper.U25.getName()))
                || (upcomingBet.getMarket().equals(MarketMapper.AWAY.getName()))
                    || (upcomingBet.getMarket().equals(MarketMapper.U35.getName()))
                    || (upcomingBet.getMarket().equals(MarketMapper.BTTS.getName()))){
                result.add(new RegisteredBet(upcomingBet.getId(),MarketMapper.getKeyByName(upcomingBet.getMarket()), upcomingBet.getUnix(),Bookmakers.getIdByName(upcomingBet.getBookmaker())));
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

    private UpcomingBet convertBetGameToUpcoming(BetGameDTO betGameDTO, Double odds, Double opening,Double value, Double kellyFactor, Double openingKellyFactor, String bookie,Double minKCAccepted){
        UpcomingBet bet = new UpcomingBet();
        bet.setStake(BigDecimalRoundDoubleMain.roundDouble(BANKROLL*kellyFactor*KELLY_FRACTIONAL,2));
        bet.setHomeTeam(betGameDTO.getHome_name());
        bet.setAwayTeam(betGameDTO.getAway_name());
        bet.setOurOdds(betGameDTO.getOur_odds());
        bet.setBookieOdds(odds);
        bet.setValue(value);
        bet.setKellyFactor(kellyFactor);
        bet.setProbability(betGameDTO.getProbability());
        bet.setMinKCAccepted(minKCAccepted);
        bet.setMarket(MarketMapper.getNameByKey(betGameDTO.getMarket()));
        bet.setId(String.valueOf(betGameDTO.getGame_id()));
        bet.setDateKO(betGameDTO.getKo_human());
        bet.setOpeningOdds(opening);
        bet.setPredictability(betGameDTO.getCompetition_predictability());

        if(bookie.equals(Bookmakers.PINNACLE.getName())){
            bet.setOpening1xOdds(betGameDTO.getOpening_1xbet_odds() != null? betGameDTO.getOpening_1xbet_odds() : 0.00);
            bet.setOpening365Odds(betGameDTO.getOpening_b365_odds() != null? betGameDTO.getOpening_b365_odds() : 0.00);
        } else {
            bet.setOpeningPinnacleOdds(betGameDTO.getOpening_pinnacle_odds() != null? betGameDTO.getOpening_pinnacle_odds() : 0.00);
        }

        bet.setBookmaker(bookie);
        bet.setCompetition(betGameDTO.getCompetition_country() + " - " + betGameDTO.getCompetition_name());
        bet.setOpeningKellyFactor(openingKellyFactor);
        bet.setCompetitionProgress(betGameDTO.getCompetition_progress() == null ? 0 : betGameDTO.getCompetition_progress());
        bet.setUnix(betGameDTO.getUnix());
        return bet;
    }

    private Double calculateValue(Double bookieOdds, Double ourOdds){
        Double diff = bookieOdds - ourOdds;
        return (diff/ourOdds) * 100.00;
    }

    private boolean checkPinnacle(Double currentRequestOpeningOdds, String bookmaker, Double opening365odds, Double opening1xOdds, String market){
        if(!bookmaker.equals(Bookmakers.PINNACLE.getName())){
            return true;
        }
        else{
            if(market.equals(Market.UNDER_35.getName())){
                return true;
            }
            //RETURN TRUE FOR 1X EVEN IF PINNY IS HIGHER
            if(opening1xOdds != null && opening1xOdds <= currentRequestOpeningOdds){
                return true;
            }

            if(opening365odds != null && opening365odds <= currentRequestOpeningOdds){
                return false;
            }

        }
        return true;

    }

    private boolean matchConditions(Double openingOdds, Double currentOdds, Double value, Double kellyFactor, Double openingKellyFactor, FilterRequest request){
        boolean oddsValue = openingOdds >= request.getMinOdds() && openingOdds <= request.getMaxOdds()
                && currentOdds >= request.getMinOdds() && currentOdds <= request.getMaxOdds()
                && value >= request.getMinValue() && value <= request.getMaxValue();

        boolean kelly = kellyFactor == 0.00 || (kellyFactor >= request.getKellyFactor() && openingKellyFactor >= request.getKellyFactor());

        return oddsValue && kelly;

    }

    private Double calculateKellyFactor1xBet(BetGameDTO betGameDTO, boolean calculateForOpening){
        Double b_decimal_odds = calculateForOpening ? betGameDTO.getOpening_1xbet_odds() - 1.00 : betGameDTO.getLatest_1xbet_odds() -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyFactorBet365(BetGameDTO betGameDTO, boolean calculateForOpening){
        Double b_decimal_odds = calculateForOpening ? betGameDTO.getOpening_b365_odds() - 1.00 : betGameDTO.getLatest_b365_odds() -1.00;
        return calculateKelly(betGameDTO, b_decimal_odds);
    }

    private Double calculateKellyFactorPinnacle(BetGameDTO betGameDTO, boolean calculateForOpening){
        Double b_decimal_odds = calculateForOpening ? betGameDTO.getOpening_pinnacle_odds() - 1.00 : betGameDTO.getLatest_pinnacle_odds() -1.00;
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
                && (game.getCompetition_progress() == null || game.getCompetition_progress() <= request.getMaxProgress())
                && (game.getHome_played()>=request.getMinGamesPlayed() && game.getAway_played() >= request.getMinGamesPlayed())
                && game.getProbability() >= request.getMinProbability()
                && (request.isCountCups() || game.isCompetition_cup()==request.isCountCups())
                && (request.isCountFriendlies() || game.isCompetition_friendly()==request.isCountFriendlies());
    }

    private boolean isRegistered(BetGameDTO game){
      Long id = Long.valueOf(game.getGame_id() + Market.getIdByName(game.getMarket()).getId() + Bookmakers.getIdByName(bookie));
      Optional<RegisteredBetDTO> rb = registeredBetRepository.findById(id);
      return rb.isPresent() &&
              rb.get().getUnix() != null && rb.get().getUnix().equals(game.getUnix())
              && rb.get().getBookieId() != null && rb.get().getBookieId().equals(Bookmakers.getIdByName(bookie)) ;

    }


}

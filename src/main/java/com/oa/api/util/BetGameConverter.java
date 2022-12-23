package com.oa.api.util;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.entity.RegisteredBetDTO;
import com.oa.api.model.BetGame;
import com.oa.api.model.RegisteredBet;

import java.util.HashMap;
import java.util.List;

public class BetGameConverter {

    public BetGameConverter(){}

    public BetGame convertDTOtoModel(BetGameDTO betGameDTO){
        BetGame result = new BetGame();
        /*result.setId(betGameDTO.getId());
        result.setHome_name(betGameDTO.getHome_name());
        result.setAway_name(betGameDTO.getAway_name());
        result.setHome_goals(betGameDTO.getHome_goals());
        result.setAway_goals(betGameDTO.getAway_goals());
        result.setMarket(betGameDTO.getMarket());
        //result.setLeague(betGameDTO.getLeague());
        //result.setIs_cup(betGameDTO.isIs_cup());
        result.setKo_human(betGameDTO.getKo_human());
        //result.setOdds(String.valueOf(betGameDTO.getOdds()));
        //result.setPredictability(betGameDTO.getPredictability());
        //result.setProbability(String.valueOf(betGameDTO.getProbability()));
        result.setOur_odds(betGameDTO.getOur_odds());
        result.setValue(betGameDTO.getValue());
        result.setStatus(betGameDTO.getStatus());
        result.setUnix(betGameDTO.getUnix());
        Result result1 = new Result();
        result1.setResult(betGameDTO.getResult());
        result1.setScore(betGameDTO.getScore());
        result.setResult(result1);*/
        return result;
    }

    public RegisteredBetDTO convertRBModelToRBDTO(RegisteredBet bet){
        RegisteredBetDTO result = new RegisteredBetDTO();
        result.setId(Long.valueOf(bet.getId() + Market.getIdByName(bet.getMarket()).getId() + bet.getBookieId()));
        result.setUnix(bet.getUnix());
        result.setBookieId(bet.getBookieId());
        return result;
    }

    public BetGameDTO converModeltoDTO(HashMap<String, Object> betGame){
        BetGameDTO result = new BetGameDTO();
        String market = (String)betGame.get(BetGameFields.MARKET.getFieldName());
        result.setGame_id(Long.valueOf(betGame.get(BetGameFields.ID.getFieldName()) + Market.getIdByName(market).getId()));
        result.setMarket(market);
        result.setHome_name((String)betGame.get(BetGameFields.HOME_NAME.getFieldName()));
        result.setAway_name((String)betGame.get(BetGameFields.AWAY_NAME.getFieldName()));
        result.setStatus((String)betGame.get(BetGameFields.STATUS.getFieldName()));
        result.setUnix(Long.valueOf((Integer) betGame.get(BetGameFields.UNIX.getFieldName())));
        result.setKo_human((String)betGame.get(BetGameFields.KO_HUMAN.getFieldName()));
        result.setHome_goals((betGame.get(BetGameFields.HOME_GOALS.getFieldName()) != null ? (Integer)betGame.get(BetGameFields.HOME_GOALS.getFieldName()) : 0));
        result.setAway_goals((betGame.get(BetGameFields.AWAY_GOALS.getFieldName()) != null ? (Integer)betGame.get(BetGameFields.AWAY_GOALS.getFieldName()) : 0));
        result.setHome_played((betGame.get(BetGameFields.HOME_PLAYED.getFieldName()) != null ? (Integer)betGame.get(BetGameFields.HOME_PLAYED.getFieldName()) : 0));
        result.setAway_played((betGame.get(BetGameFields.AWAY_PLAYED.getFieldName()) != null ? (Integer)betGame.get(BetGameFields.AWAY_PLAYED.getFieldName()) : 0));
        result.setCorners((betGame.get(BetGameFields.CORNERS.getFieldName()) != null ? (Integer)betGame.get(BetGameFields.CORNERS.getFieldName()) : 0));

        HashMap<String, Object> compDetails = (HashMap<String, Object>) betGame.get(BetGameFields.COMPETITION.getFieldName());
        setCompetitionDetails(compDetails, result);

        List<HashMap<String, Object>> odds = (List<HashMap<String, Object>>) betGame.get(BetGameFields.ODDS.getFieldName());

        setBookmakerOdds(odds, result, Bookmakers.BET365);
        setBookmakerOdds(odds, result, Bookmakers.ONEXBET);
        setBookmakerOdds(odds, result, Bookmakers.PINNACLE);

        result.setProbability((Double.parseDouble((String.valueOf(betGame.get(BetGameFields.PROBABILITY.getFieldName()))))));
        result.setOur_odds((Double.parseDouble((String.valueOf(betGame.get(BetGameFields.OUR_ODDS.getFieldName()))))));
        result.setValue_percentage((Double.parseDouble((String.valueOf(betGame.get(BetGameFields.VALUE.getFieldName()))))));
        result.setPeak_odds(Double.parseDouble(String.valueOf(betGame.get(BetGameFields.PEAK_ODDS.getFieldName()))));
        result.setLatest_odds(Double.parseDouble(String.valueOf(betGame.get(BetGameFields.LATEST_ODDS.getFieldName()))));

        HashMap<String, Object> resultDetails = (HashMap<String, Object>) betGame.get(BetGameFields.RESULT.getFieldName());

        setResultDetails(resultDetails, result);

        return result;
    }

    private void setResultDetails(HashMap<String, Object> details, BetGameDTO betGameDTO){

        if(details != null){
            betGameDTO.setResult(details.get(BetGameFields.RESULT.getFieldName()) != null && (boolean) details.get(BetGameFields.RESULT.getFieldName()));
            betGameDTO.setScore(details.get(BetGameFields.SCORE.getFieldName()) != null ? String.valueOf(details.get(BetGameFields.SCORE.getFieldName())):"");
        }


    }

    private void setBookmakerOdds(List<HashMap<String, Object>> odds, BetGameDTO dto, Bookmakers bookmaker){
        switch(bookmaker){
            case BET365:
                HashMap<String, Object> odd365 = findOdd(odds, Bookmakers.BET365);
                if(odd365!= null){
                    dto.setLatest_b365_odds(Double.parseDouble(String.valueOf(odd365.get(BetGameFields.BOOKMAKER_LATEST_ODDS.getFieldName()))));
                    dto.setOpening_b365_odds(Double.parseDouble(String.valueOf(odd365.get(BetGameFields.BOOKMAKER_OPENING_ODDS.getFieldName()))));
                    dto.setPeak_b365_odds(Double.parseDouble(String.valueOf(odd365.get(BetGameFields.BOOKMAKER_PEAK_ODDS.getFieldName()))));
                    dto.setValue_b365(Double.parseDouble(String.valueOf(odd365.get(BetGameFields.BOOKMAKER_VALUE.getFieldName()))));
                    dto.setIsValue_b365((boolean) odd365.get(BetGameFields.BOOKMAKER_IS_VALUE.getFieldName()));
                } else{
                    dto.setLatest_b365_odds(null);
                    dto.setOpening_b365_odds(null);
                    dto.setPeak_b365_odds(null);
                    dto.setValue_b365(null);
                    dto.setIsValue_b365(false);
                }
                break;
            case ONEXBET:
                HashMap<String, Object> onexbet = findOdd(odds, Bookmakers.ONEXBET);
                if(onexbet!= null){
                    dto.setLatest_1xbet_odds(Double.parseDouble(String.valueOf(onexbet.get(BetGameFields.BOOKMAKER_LATEST_ODDS.getFieldName()))));
                    dto.setOpening_1xbet_odds(Double.parseDouble(String.valueOf(onexbet.get(BetGameFields.BOOKMAKER_OPENING_ODDS.getFieldName()))));
                    dto.setPeak_1xbet_odds(Double.parseDouble(String.valueOf(onexbet.get(BetGameFields.BOOKMAKER_PEAK_ODDS.getFieldName()))));
                    dto.setValue_1xbet(Double.parseDouble(String.valueOf(onexbet.get(BetGameFields.BOOKMAKER_VALUE.getFieldName()))));
                    dto.setIsValue_1xbet((boolean) onexbet.get(BetGameFields.BOOKMAKER_IS_VALUE.getFieldName()));
                } else {
                    dto.setLatest_1xbet_odds(null);
                    dto.setOpening_1xbet_odds(null);
                    dto.setPeak_1xbet_odds(null);
                    dto.setValue_1xbet(null);
                    dto.setIsValue_1xbet(false);
                }
                break;
            case PINNACLE:
                HashMap<String, Object> pinnacle = findOdd(odds, Bookmakers.PINNACLE);
                if(pinnacle!= null){
                    dto.setLatest_pinnacle_odds(Double.parseDouble(String.valueOf(pinnacle.get(BetGameFields.BOOKMAKER_LATEST_ODDS.getFieldName()))));
                    dto.setOpening_pinnacle_odds(Double.parseDouble(String.valueOf(pinnacle.get(BetGameFields.BOOKMAKER_OPENING_ODDS.getFieldName()))));
                    dto.setPeak_1xbet_odds(Double.parseDouble(String.valueOf(pinnacle.get(BetGameFields.BOOKMAKER_PEAK_ODDS.getFieldName()))));
                    dto.setValue_pinnacle(Double.parseDouble(String.valueOf(pinnacle.get(BetGameFields.BOOKMAKER_VALUE.getFieldName()))));
                    dto.setIsValue_pinnacle((boolean) pinnacle.get(BetGameFields.BOOKMAKER_IS_VALUE.getFieldName()));
                }  else {
                    dto.setLatest_pinnacle_odds(null);
                    dto.setOpening_pinnacle_odds(null);
                    dto.setPeak_pinnacle_odds(null);
                    dto.setValue_pinnacle(null);
                    dto.setIsValue_pinnacle(false);
                }
                break;
        }
    }

    private HashMap<String, Object> findOdd(List<HashMap<String, Object>> odds, Bookmakers bookie){
        for (HashMap<String, Object> odd : odds) {
            if ((Long.valueOf((Integer)odd.get(BetGameFields.BOOKMAKER_ID.getFieldName())) == bookie.getId())) {
                return odd;
            }
        }
        return null;
    }

    private void setCompetitionDetails(HashMap<String, Object> compDetails, BetGameDTO betGameDTO){
        betGameDTO.setCompetition_id(Long.valueOf((Integer) compDetails.get(BetGameFields.COMPETITION_ID.getFieldName())));
        betGameDTO.setCompetition_name((String) compDetails.get(BetGameFields.COMPETITION_NAME.getFieldName()));
        betGameDTO.setCompetition_season((String) compDetails.get(BetGameFields.COMPETITION_SEASON.getFieldName()));
        betGameDTO.setCompetition_season_id(Long.valueOf((Integer) compDetails.get(BetGameFields.COMPETITION_SEASON_ID.getFieldName())));
        betGameDTO.setCompetition_country((String) compDetails.get(BetGameFields.COMPETITION_COUNTRY.getFieldName()));
        betGameDTO.setCompetition_friendly((boolean) compDetails.get(BetGameFields.COMPETITION_IS_FRIENDLY.getFieldName()));
        betGameDTO.setCompetition_cup((boolean) compDetails.get(BetGameFields.COMPETITION_IS_CUP.getFieldName()));
        betGameDTO.setCompetition_predictability((String) compDetails.get(BetGameFields.COMPETITION_PREDICTABILITY.getFieldName()));
        betGameDTO.setCompetition_progress((Integer) compDetails.get(BetGameFields.COMPETITION_PROGRESS.getFieldName()));
    }
}

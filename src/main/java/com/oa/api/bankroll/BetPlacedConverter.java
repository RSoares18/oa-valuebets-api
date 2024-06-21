package com.oa.api.bankroll;

import com.oa.api.bankroll.model.Bet;
import com.oa.api.bankroll.model.BetResult;
import com.oa.api.util.BigDecimalRoundDoubleMain;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Component
public class BetPlacedConverter {

    public LocalDate getGameDate(HashMap<String, Object> betGame){
        String date = (String) betGame.get(BetPlacedFields.DATE.getFieldName());
        return LocalDateTime.parse(date.substring(0, date.length()-1)).plusHours(1).toLocalDate();
    }

    private String cropDate(String koDate){
        return koDate.substring(0,10);
    }

    public Bet convertToBetDTO(HashMap<String, Object> betGame){
        Bet bet = new Bet();
        HashMap<String,Object> betGameResult = (HashMap<String, Object>) betGame.get(BetPlacedFields.RESULT.getFieldName());
        BetResult betResult = convertToBetResult(betGameResult);

        if(betResult == null){
            return null;
        }
        bet.setBetResult(betResult);
        bet.setFixture_name((String) betGame.get(BetPlacedFields.FIXTURE_NAME.getFieldName()));
        bet.setMarket_title((String) betGame.get(BetPlacedFields.MARKET_TITLE.getFieldName()));
        bet.setStake(Double.parseDouble(String.valueOf(betGame.get(BetPlacedFields.STAKE.getFieldName())))*BankrollService.FIRST_UNIT_SIZE);
        bet.setBet((String) betGame.get(BetPlacedFields.BET.getFieldName()));
        bet.setOdds(Double.parseDouble(String.valueOf(betGame.get(BetPlacedFields.ODDS.getFieldName()))));
        bet.setDate(cropDate((String)betGame.get(BetPlacedFields.DATE.getFieldName())));
        return bet;
    }

    private BetResult convertToBetResult(HashMap<String, Object> betGameResult) {
        BetResult betResult = new BetResult();
        betResult.setText((String) betGameResult.get(BetPlacedFields.STATUS.getFieldName()));
        if(betResult.getText().equalsIgnoreCase("pending")){
            return null;
        }
        betResult.setProfit((BigDecimalRoundDoubleMain.roundDouble(Double.parseDouble(String.valueOf(betGameResult.get(BetPlacedFields.PROFIT.getFieldName()))), 2)*BankrollService.FIRST_UNIT_SIZE));
        return betResult;
    }
}

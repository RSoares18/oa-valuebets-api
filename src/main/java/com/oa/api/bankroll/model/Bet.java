package com.oa.api.bankroll.model;

import com.oa.api.util.BigDecimalRoundDoubleMain;
import com.oa.api.util.MarketMapper;

public class Bet {

    private String fixture_name;
    private String market_title;
    private double stake;
    private String bet;
    private double odds;
    private BetResult betResult;
    private String date;


    public String getFixture_name() {
        return fixture_name;
    }

    public void setFixture_name(String fixture_name) {
        this.fixture_name = fixture_name;
    }


    public String getMarket_title() {
        return market_title;
    }

    public void setMarket_title(String market_title) {
        this.market_title = market_title;
    }

    public double getStake() {
        return stake;
    }

    public void setStake(double stake) {
        this.stake = stake;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public BetResult getBetResult() {
        return betResult;
    }

    public void setBetResult(BetResult betResult) {
        this.betResult = betResult;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "\u26BD " + fixture_name + "\n" +
                        "\uD83D\uDCCA " + MarketMapper.getNameByShortName(bet) + "\n" +
                        "\uD83D\uDCC6 " + date + "\n" +
                        "\u26A1 " + roundDouble(stake) + "€ @ " + odds + " " + (betResult.getText().equalsIgnoreCase("Won") ? "\u2705 " : "\u274C ") + roundDouble(betResult.getProfit()) + "€" + "\n\n";

    }

    private double roundDouble (double value){
        return BigDecimalRoundDoubleMain.roundDouble(value, 2);
    }
}

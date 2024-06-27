package com.oa.api.bankroll.model;

import java.util.List;

public class BetsStatement {

    private String startDate;
    private String endDate;
    private int numBets;
    private int wins;
    private int losses;
    private double profit_loss_units;

    private double profit_loss_money;
    private List<Bet> bets;

    private double roi;
    private double staked;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumBets() {
        return numBets;
    }

    public void setNumBets(int numBets) {
        this.numBets = numBets;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getProfit_loss_units() {
        return profit_loss_units;
    }

    public void setProfit_loss_units(double profit_loss_units) {
        this.profit_loss_units = profit_loss_units;
    }

    public double getProfit_loss_money() {
        return profit_loss_money;
    }

    public void setProfit_loss_money(double profit_loss_money) {
        this.profit_loss_money = profit_loss_money;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getStaked() {
        return staked;
    }

    public void setStaked(double staked) {
        this.staked = staked;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    @Override
    public String toString() {
        return
                "RESULTS FROM " + startDate  + " TO " + endDate + "\n\n" +
                "\uD83D\uDCD2 " +  "Bets: " + numBets + "\n" +
                "\u2705 Wins: " + wins + "\n" +
                "\u274C Losses: " + losses + "\n" +
                "\uD83D\uDCC8 P/L U: " + profit_loss_units + "U" + "\n" +
                "\uD83D\uDCB0 P/L €: " + profit_loss_money + "€" + "\n" +
                        "\uD83D\uDCCA ROI %: " + roi + "%" + "\n" +
                        "\uD83D\uDCB2 Staked €: " + staked + "€" + "\n" +
                 showGames(bets);

    }

    private String removeUnwantedCharacters(String listString) {
        return listString.replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "");

    }

    private String showGames(List<Bet> bets){
        if(bets==null || bets.isEmpty()){
            return "";
        } else {
            return "\u26BD Games: " + "\n\n" + removeUnwantedCharacters(bets.toString()) + "\n\n";
        }
    }
}

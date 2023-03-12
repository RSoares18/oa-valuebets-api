package com.oa.api.model;

public class TestedGame {

    private int betNo;
    private String homeTeam;
    private String awayTeam;
    private String market;
    private Double odds;
    private Double stake;
    private Double profitLoss;
    private Double currentProfit;

    public TestedGame() {
    }

    public TestedGame(int betNo,String homeTeam, String awayTeam, String market, Double odds, Double stake, Double profitLoss, Double currentProfit) {
        this.betNo = betNo;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.market = market;
        this.odds = odds;
        this.stake = stake;
        this.profitLoss = profitLoss;
        this.currentProfit = currentProfit;
    }

    public int getBetNo() {
        return betNo;
    }

    public void setBetNo(int betNo) {
        this.betNo = betNo;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }

    public Double getCurrentProfit() {
        return currentProfit;
    }

    public void setCurrentProfit(Double currentProfit) {
        this.currentProfit = currentProfit;
    }
}

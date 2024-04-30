package com.oa.api.model;

import java.util.Date;

public class TestedGame {

    private int betNo;
    private String homeTeam;
    private String awayTeam;
    private String market;
    private Double odds;
    private Double stake;
    private Double profitLoss;
    private Double currentProfit;

    private Long unix;

    public TestedGame() {
    }

    public TestedGame(int betNo,String homeTeam, String awayTeam, String market, Double odds, Double stake, Double profitLoss, Double currentProfit, Long unix) {
        this.betNo = betNo;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.market = market;
        this.odds = odds;
        this.stake = stake;
        this.profitLoss = profitLoss;
        this.currentProfit = currentProfit;
        this.unix = unix;
    }

    public Long getUnix() {
        return unix;
    }

    public void setUnix(Long unix) {
        this.unix = unix;
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

    public static void main(String[] args) {
        System.out.println(new Date(1712188800L *1000));
    }

    @Override
    public String toString() {
        return "{" +
                "betNo=" + betNo +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", market='" + market + '\'' +
                ", odds=" + odds +
                ", stake=" + stake +
                ", profitLoss=" + profitLoss +
                ", currentProfit=" + currentProfit +
                ", unix=" + new Date(unix*1000) +
                '}';
    }
}

package com.oa.api.model;

import java.io.Serializable;

public class TestResponse implements Serializable {

    private String market;
    private Double minOdds;
    private Double maxOdds;
    private Double minProbability;
    private Double maxProbability;
    private Double minValue;
    private Double maxValue;
    private int numBets;
    private int wins;
    private int losses;
    private Double profit;
    private Double maxProfit;
    private Double maxDrawdown;
    private Double hitRate;
    private Double roi;
    private Double kellyCriteria;
    private Double kellyFactor;
    private Double myStake;
    private Double oddsPercentage;
    private int maxLeagueProgress;
    private int minGamesPlayed;
    private String country;
    private String startDate;
    private String endDate;

    public TestResponse(){}

    public TestResponse(String market, Double minOdds, Double maxOdds, Double minProbability, Double minValue, Double maxValue,
                        int numBets, int wins, int losses, Double profit, Double maxProfit, Double maxDrawdown, Double hitRate, Double roi) {
        this.market = market;
        this.minOdds = minOdds;
        this.maxOdds = maxOdds;
        this.minProbability = minProbability;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.numBets = numBets;
        this.wins = wins;
        this.losses = losses;
        this.profit = profit;
        this.hitRate = hitRate;
        this.roi = roi;
        this.maxProfit = maxProfit;
        this.maxDrawdown = maxDrawdown;
    }

    public Double getOddsPercentage() {
        return oddsPercentage;
    }

    public void setOddsPercentage(Double oddsPercentage) {
        this.oddsPercentage = oddsPercentage;
    }

    public Double getMyStake() {
        return myStake;
    }

    public void setMyStake(Double myStake) {
        this.myStake = myStake;
    }

    public Double getKellyFactor() {
        return kellyFactor;
    }

    public void setKellyFactor(Double kellyFactor) {
        this.kellyFactor = kellyFactor;
    }

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

    public Double getMaxProbability() {
        return maxProbability;
    }

    public void setMaxProbability(Double maxProbability) {
        this.maxProbability = maxProbability;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getKellyCriteria() {
        return kellyCriteria;
    }

    public void setKellyCriteria(Double kellyCriteria) {
        this.kellyCriteria = kellyCriteria;
    }

    public int getMaxLeagueProgress() {
        return maxLeagueProgress;
    }

    public void setMaxLeagueProgress(int maxLeagueProgress) {
        this.maxLeagueProgress = maxLeagueProgress;
    }

    public int getMinGamesPlayed() {
        return minGamesPlayed;
    }

    public void setMinGamesPlayed(int minGamesPlayed) {
        this.minGamesPlayed = minGamesPlayed;
    }

    public Double getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(Double maxProfit) {
        this.maxProfit = maxProfit;
    }

    public Double getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(Double maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Double getMinOdds() {
        return minOdds;
    }

    public void setMinOdds(Double minOdds) {
        this.minOdds = minOdds;
    }

    public Double getMaxOdds() {
        return maxOdds;
    }

    public void setMaxOdds(Double maxOdds) {
        this.maxOdds = maxOdds;
    }

    public Double getMinProbability() {
        return minProbability;
    }

    public void setMinProbability(Double minProbability) {
        this.minProbability = minProbability;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
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

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getHitRate() {
        return hitRate;
    }

    public void setHitRate(Double hitRate) {
        this.hitRate = hitRate;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    @Override
    public String toString() {
        return "{" +
                "market='" + market + '\'' +
                ", minOdds=" + minOdds +
                ", maxOdds=" + maxOdds +
                ", minProbability=" + minProbability +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", numBets=" + numBets +
                ", wins=" + wins +
                ", losses=" + losses +
                ", profit=" + profit +
                ", hitRate=" + hitRate +
                ", roi=" + roi +
                '}';
    }
}

package com.oa.api.model;

import java.io.Serializable;
import java.util.List;

public class MultipleTestResponse implements Serializable {

    private List<String> markets;
    private int numBets;
    private int wins;
    private int losses;
    private Double profit;
    private Double maxProfit;
    private Double maxDrawdown;
    private Double hitRate;
    private Double roi;
    private String startDate;
    private String endDate;
    private List <TestedGame> testedGames;

    public MultipleTestResponse() {
    }

    public MultipleTestResponse(List<String> markets, int numBets, int wins, int losses, Double profit, Double maxProfit, Double maxDrawdown, Double hitRate, Double roi, String startDate, String endDate) {
        this.markets = markets;
        this.numBets = numBets;
        this.wins = wins;
        this.losses = losses;
        this.profit = profit;
        this.maxProfit = maxProfit;
        this.maxDrawdown = maxDrawdown;
        this.hitRate = hitRate;
        this.roi = roi;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<TestedGame> getTestedGames() {
        return testedGames;
    }

    public void setTestedGames(List<TestedGame> testedGames) {
        this.testedGames = testedGames;
    }

    public List<String> getMarkets() {
        return markets;
    }

    public void setMarkets(List<String> markets) {
        this.markets = markets;
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

    @Override
    public String toString() {
        return "MultipleTestResponse{" +
                "markets=" + markets +
                ", numBets=" + numBets +
                ", wins=" + wins +
                ", losses=" + losses +
                ", profit=" + profit +
                ", maxProfit=" + maxProfit +
                ", maxDrawdown=" + maxDrawdown +
                ", hitRate=" + hitRate +
                ", roi=" + roi +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

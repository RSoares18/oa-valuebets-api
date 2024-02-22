package com.oa.api.model;

import java.io.Serializable;

public class FilterRequest implements Serializable {

    private String filterName;
    private String bookie;
    private String market;
    private Double minOdds;
    private Double maxOdds;
    private Double minProbability;
    private Double minValue;
    private Double maxValue;
    private Double kellyFactor;
    private Double minKellyFactor;
    private boolean countFriendlies;
    private boolean countCups;
    private int minGamesPlayed;
    private int maxProgress;
    private String predictability;

    public FilterRequest(String filterName,int minGamesPlayed,String bookie, String market, Double minOdds, Double maxOdds, Double minProbability,
                         Double minValue, Double maxValue, Double kellyFactor, Double minKellyFactor, boolean countFriendlies, boolean countCups, int maxProgress, String predictability) {
        this.filterName = filterName;
        this.bookie = bookie;
        this.market = market;
        this.minOdds = minOdds;
        this.maxOdds = maxOdds;
        this.minProbability = minProbability;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.kellyFactor = kellyFactor;
        this.minKellyFactor = minKellyFactor;
        this.countFriendlies = countFriendlies;
        this.countCups = countCups;
        this.minGamesPlayed = minGamesPlayed;
        this.maxProgress = maxProgress;
        this.predictability = predictability;
    }

    public Double getMinKellyFactor() {
        return minKellyFactor;
    }

    public void setMinKellyFactor(Double minKellyFactor) {
        this.minKellyFactor = minKellyFactor;
    }

    public String getPredictability() {
        return predictability;
    }

    public void setPredictability(String predictability) {
        this.predictability = predictability;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public FilterRequest() {
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getMinGamesPlayed() {
        return minGamesPlayed;
    }

    public void setMinGamesPlayed(int minGamesPlayed) {
        this.minGamesPlayed = minGamesPlayed;
    }

    public String getBookie() {
        return bookie;
    }

    public void setBookie(String bookie) {
        this.bookie = bookie;
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

    public Double getKellyFactor() {
        return kellyFactor;
    }

    public void setKellyFactor(Double kellyFactor) {
        this.kellyFactor = kellyFactor;
    }

    public boolean isCountFriendlies() {
        return countFriendlies;
    }

    public void setCountFriendlies(boolean countFriendlies) {
        this.countFriendlies = countFriendlies;
    }

    public boolean isCountCups() {
        return countCups;
    }

    public void setCountCups(boolean countCups) {
        this.countCups = countCups;
    }
}

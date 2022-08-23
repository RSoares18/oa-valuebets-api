package com.oa.api.model;

import java.io.Serializable;

public class TestRequest implements Serializable {

    private String bookie;
    private String market;
    private Double minOdds;
    private Double maxOdds;
    private Double minProbability;
    private Double minValue;
    private Double maxValue;
    private Double kellyFactor;
    private boolean countFriendlies;
    private boolean countCups;
    private boolean openingOdds;
    private int minGamesPlayed;

    public TestRequest(int minGamesPlayed, String bookie, String market,Double minOdds, Double maxOdds, Double minProbability,
                       Double minValue, Double maxValue, Double kellyFactor, boolean countFriendlies, boolean countCups, boolean openingOdds) {
        this.bookie = bookie;
        this.market = market;
        this.minOdds = minOdds;
        this.maxOdds = maxOdds;
        this.minProbability = minProbability;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.countFriendlies = countFriendlies;
        this.countCups = countCups;
        this.openingOdds = openingOdds;
        this.kellyFactor = kellyFactor;
        this.minGamesPlayed = minGamesPlayed;
    }

    public int getMinGamesPlayed() {
        return minGamesPlayed;
    }

    public void setMinGamesPlayed(int minGamesPlayed) {
        this.minGamesPlayed = minGamesPlayed;
    }

    public Double getKellyFactor() {
        return kellyFactor;
    }

    public void setKellyFactor(Double kellyFactor) {
        this.kellyFactor = kellyFactor;
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

    public boolean isOpeningOdds() {
        return openingOdds;
    }

    public void setOpeningOdds(boolean openingOdds) {
        this.openingOdds = openingOdds;
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
                ", countFriendlies=" + countFriendlies +
                ", countCups=" + countCups +
                ", openingOdds=" + openingOdds +
                '}';
    }
}

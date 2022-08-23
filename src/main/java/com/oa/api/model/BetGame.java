package com.oa.api.model;

import java.util.List;

public class BetGame {

    private Long id;
    private String market;
    private String home_name;
    private String away_name;
    private String status;
    private Long unix;
    private String ko_human;
    private int home_goals;
    private int away_goals;
    private int home_played;
    private int away_played;
    private int corners;
    private Competition competition;
    private List<Odd> odds;
    private String best_odds;
    private double our_odds;
    private double value;
    private double probability;
    private Result result;

    public BetGame(){}

    public BetGame(Long id, String market, String home_name, String away_name, String status, Long unix, String ko_human, int home_goals,
                   int away_goals, int home_played, int away_played, int corners, Competition competition, List<Odd> odds,
                   String best_odds, double our_odds, double value, double probability, Result result) {
        this.id = id;
        this.market = market;
        this.home_name = home_name;
        this.away_name = away_name;
        this.status = status;
        this.unix = unix;
        this.ko_human = ko_human;
        this.home_goals = home_goals;
        this.away_goals = away_goals;
        this.home_played = home_played;
        this.away_played = away_played;
        this.corners = corners;
        this.competition = competition;
        this.odds = odds;
        this.best_odds = best_odds;
        this.our_odds = our_odds;
        this.value = value;
        this.probability = probability;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUnix() {
        return unix;
    }

    public void setUnix(Long unix) {
        this.unix = unix;
    }

    public String getKo_human() {
        return ko_human;
    }

    public void setKo_human(String ko_human) {
        this.ko_human = ko_human;
    }

    public int getHome_goals() {
        return home_goals;
    }

    public void setHome_goals(int home_goals) {
        this.home_goals = home_goals;
    }

    public int getAway_goals() {
        return away_goals;
    }

    public void setAway_goals(int away_goals) {
        this.away_goals = away_goals;
    }

    public int getHome_played() {
        return home_played;
    }

    public void setHome_played(int home_played) {
        this.home_played = home_played;
    }

    public int getAway_played() {
        return away_played;
    }

    public void setAway_played(int away_played) {
        this.away_played = away_played;
    }

    public int getCorners() {
        return corners;
    }

    public void setCorners(int corners) {
        this.corners = corners;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Odd> getOdds() {
        return odds;
    }

    public void setOdds(List<Odd> odds) {
        this.odds = odds;
    }

    public String getBest_odds() {
        return best_odds;
    }

    public void setBest_odds(String best_odds) {
        this.best_odds = best_odds;
    }

    public double getOur_odds() {
        return our_odds;
    }

    public void setOur_odds(double our_odds) {
        this.our_odds = our_odds;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

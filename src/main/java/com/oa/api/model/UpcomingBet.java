package com.oa.api.model;

import com.oa.api.util.BigDecimalRoundDoubleMain;

public class UpcomingBet {

    private String id;
    private String dateKO;
    private String homeTeam;
    private String awayTeam;
    private String market;
    private Double ourOdds;
    private Double bookieOdds;
    private Double openingOdds;
    private Double value;
    private Double kellyFactor;
    private Double probability;
    private Double diffMovement;
    private String bookmaker;
    private String competition;
    private Double openingKellyFactor;

    public UpcomingBet(String competition,String bookmaker, Double diffMovement,String id, String dateKO,Double openingOdds,String market, String homeTeam, String awayTeam, Double ourOdds, Double bookieOdds, Double value, Double kellyFactor, Double probability) {
        this.competition = competition;
        this.diffMovement = diffMovement;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.ourOdds = ourOdds;
        this.bookieOdds = bookieOdds;
        this.value = value;
        this.kellyFactor = kellyFactor;
        this.probability = probability;
        this.market = market;
        this.openingOdds = openingOdds;
        this.id = id;
        this.dateKO = dateKO;
        this.bookmaker = bookmaker;
    }

    public UpcomingBet() {
    }

    public Double getOpeningKellyFactor() {
        return openingKellyFactor;
    }

    public void setOpeningKellyFactor(Double openingKellyFactor) {
        this.openingKellyFactor = openingKellyFactor;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getBookmaker() {
        return bookmaker;
    }

    public void setBookmaker(String bookmaker) {
        this.bookmaker = bookmaker;
    }

    public Double getDiffMovement() {
        return diffMovement;
    }

    public void setDiffMovement(Double diffMovement) {
        this.diffMovement = diffMovement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateKO() {
        return dateKO;
    }

    public void setDateKO(String dateKO) {
        this.dateKO = dateKO;
    }

    public Double getOpeningOdds() {
        return openingOdds;
    }

    public void setOpeningOdds(Double openingOdds) {
        this.openingOdds = openingOdds;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
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

    public Double getOurOdds() {
        return ourOdds;
    }

    public void setOurOdds(Double ourOdds) {
        this.ourOdds = ourOdds;
    }

    public Double getBookieOdds() {
        return bookieOdds;
    }

    public void setBookieOdds(Double bookieOdds) {
        this.bookieOdds = bookieOdds;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getKellyFactor() {
        return kellyFactor;
    }

    public void setKellyFactor(Double kellyFactor) {
        this.kellyFactor = kellyFactor;
    }

    @Override
    public String toString() {
        return
                "\uD83D\uDEA8 NEW VALUE ALERT \n" +
                "\uD83C\uDD94 " + id + "\n" +
                "\uD83D\uDCC6 " + dateKO +"\n" +
                "\uD83D\uDCCA Market: " + market + "\n" +
                "\uD83D\uDCDA Bookmaker: " + bookmaker + "\n" +
                "\uD83C\uDFC6 " + competition +  "\n" +
                "\u26BD " + homeTeam + " vs " + awayTeam + "\n" +
                "\uD83D\uDCBB Probability: " + probability + "% " + "(" + ourOdds + ")" + "\n" +
                "\u26A1 Current Odds: " + bookieOdds + " (" + diffMovement + "%) " + "\n" +
                "\u26AA Opening Odds: " + openingOdds + "\n" +
                "\uD83D\uDCC8 Value: " + BigDecimalRoundDoubleMain.roundDouble(value,2) + "%" + "\n" +
                "\uD83D\uDCCA Opening Kelly Factor: " + BigDecimalRoundDoubleMain.roundDouble(openingKellyFactor,3) + "\n" +
                "\uD83D\uDCCA Current Kelly Factor: " + BigDecimalRoundDoubleMain.roundDouble(kellyFactor,3) + "\n\n\n\n";
    }
}

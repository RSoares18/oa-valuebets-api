package com.oa.api.model;

import com.oa.api.util.BigDecimalRoundDoubleMain;
import com.oa.api.util.Bookmakers;
import com.oa.api.util.Market;
import com.oa.api.util.MarketMapper;

public class UpcomingBet {

    private String id;
    private String dateKO;
    private String homeTeam;
    private String awayTeam;
    private String market;
    private Double ourOdds;
    private Double bookieOdds;
    private Double openingOdds;
    private Double opening365Odds;
    private Double opening1xOdds;
    private Double value;
    private Double kellyFactor;
    private Double probability;
    private Double diffMovement;
    private String bookmaker;
    private String competition;
    private Double openingKellyFactor;
    private Double stake;
    private int competitionProgress;
    private Long unix;

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

    public Double getOpening365Odds() {
        return opening365Odds;
    }

    public void setOpening365Odds(Double opening365Odds) {
        this.opening365Odds = opening365Odds;
    }

    public Double getOpening1xOdds() {
        return opening1xOdds;
    }

    public void setOpening1xOdds(Double opening1xOdds) {
        this.opening1xOdds = opening1xOdds;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }

    public Long getUnix() {
        return unix;
    }

    public void setUnix(Long unix) {
        this.unix = unix;
    }

    public int getCompetitionProgress() {
        return competitionProgress;
    }

    public void setCompetitionProgress(int competitionProgress) {
        this.competitionProgress = competitionProgress;
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
                "\uD83C\uDFC1 Progress: " + competitionProgress +  "% \n" +
                "\u26BD " + homeTeam + " vs " + awayTeam + "\n" +
                "\uD83D\uDCBB Probability: " + probability + "% " + "(" + ourOdds + ")" + "\n" +
                "\u26A1 Current Odds: " + bookieOdds + " (" + diffMovement + "%) " + "\n" +
                "\u26AA Opening Odds: " + openingOdds + "\n" +
                        showFor365Or1xBetRequests(bookmaker, market, probability*0.01) +
                        showForPinnacleRequests(bookmaker, market, probability*0.01) +
                "\uD83D\uDCC8 Value: " + BigDecimalRoundDoubleMain.roundDouble(value,2) + "%" + "\n" +
                "\uD83D\uDCCA Opening Kelly Factor: " + BigDecimalRoundDoubleMain.roundDouble(openingKellyFactor,3) + "\n" +
                "\uD83D\uDCCA Current Kelly Factor: " + BigDecimalRoundDoubleMain.roundDouble(kellyFactor,3) + "\n" +
                "\uD83D\uDCB2 Stake: " + stake + "€" + "\n\n\n\n";
    }

    private String showFor365Or1xBetRequests(String bookmaker, String market, Double probability){
        if((bookmaker.equals(Bookmakers.ONEXBET.getName()) || bookmaker.equals(Bookmakers.BET365.getName())) && !market.equals(Market.UNDER_35.getName())){
            if(MarketMapper.getKeyByName(market).equals(Market.AWAY_WIN.getName())){
                double minKCOdds = BigDecimalRoundDoubleMain.roundDouble(((probability - 1)/(0.15-probability) )+ 1,2);
                return "\u2797 Min. Odds (KC): " + minKCOdds + "\n";
            }
            if(MarketMapper.getKeyByName(market).equals(Market.HOME_WIN.getName())){
                double minKCOdds = BigDecimalRoundDoubleMain.roundDouble(((probability - 1)/(0.20-probability)) + 1,2);
                return "\u2797 Min. Odds (KC): " + minKCOdds + "\n";
            }
        }
        return "";
    }

    private String showForPinnacleRequests(String bookmaker, String market, Double probability){
        double minKCOdds = 0;
        if(bookmaker.equals(Bookmakers.PINNACLE.getName()) && MarketMapper.getKeyByName(market).equals(Market.HOME_WIN.getName())){
            minKCOdds = BigDecimalRoundDoubleMain.roundDouble(((probability - 1)/(0.20-probability) )+ 1,2);
        }

        if(bookmaker.equals(Bookmakers.PINNACLE.getName()) && MarketMapper.getKeyByName(market).equals(Market.AWAY_WIN.getName())){
            minKCOdds = BigDecimalRoundDoubleMain.roundDouble(((probability - 1)/(0.15-probability) )+ 1,2);
        }
        if(minKCOdds != 0){
            String line365 = opening365Odds != null && opening365Odds != 0.0 ? "\u2797 Bet365 Opening Odd: " + opening365Odds + "(Min KC Odds " + minKCOdds + ")" + "\n" : "";
            String line1x = opening1xOdds != null && opening1xOdds != 0.0 ? "\u2797 1xBet Opening Odd: " + opening1xOdds + "(Min KC Odds " + minKCOdds + ")" + "\n" : "";
            if(bookmaker.equals(Bookmakers.PINNACLE.getName()) && !MarketMapper.getKeyByName(market).equals(Market.UNDER_35.getName())){
                return line365 + line1x;
            }
        }

        return "";
    }

    private double getLower(Double valueA, Double valueB){
        if(valueA > valueB){
            return valueB;
        } else {
            return valueA;
        }

    }
}

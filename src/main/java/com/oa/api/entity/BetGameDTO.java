package com.oa.api.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BG01")
public class BetGameDTO implements Serializable {

    @Id
    private Long game_id;
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
    private long competition_id;
    private String competition_name;
    private String competition_season;
    private long competition_season_id;
    private String competition_country;
    private boolean competition_friendly;
    private boolean competition_cup;
    private String competition_predictability;
    @Nullable
    private Double latest_1xbet_odds;
    @Nullable
    private Double opening_1xbet_odds;
    @Nullable
    private Double peak_1xbet_odds;
    @Nullable
    private boolean isValue_1xbet;
    @Nullable
    private Double value_1xbet;
    @Nullable
    private Double latest_pinnacle_odds;
    @Nullable
    private Double peak_pinnacle_odds;
    @Nullable
    private Double opening_pinnacle_odds;
    @Nullable
    private boolean isValue_pinnacle;
    @Nullable
    private Double value_pinnacle;
    @Nullable
    private Double latest_b365_odds;
    @Nullable
    private Double opening_b365_odds;
    @Nullable
    private Double peak_b365_odds;
    @Nullable
    private boolean isValue_b365;
    @Nullable
    private Double value_b365;
    @Nullable
    private Double latest_odds;
    @Nullable
    private Double our_odds;
    @Nullable
    private Double peak_odds;
    @Nullable
    private Double value_percentage;
    @Nullable
    private Double probability;
    private boolean result;
    private String score;

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long id) {
        this.game_id = id;
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

    public long getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(long competition_id) {
        this.competition_id = competition_id;
    }

    public String getCompetition_name() {
        return competition_name;
    }

    public void setCompetition_name(String competition_name) {
        this.competition_name = competition_name;
    }

    public String getCompetition_season() {
        return competition_season;
    }

    public void setCompetition_season(String competition_season) {
        this.competition_season = competition_season;
    }

    public long getCompetition_season_id() {
        return competition_season_id;
    }

    public void setCompetition_season_id(long competition_season_id) {
        this.competition_season_id = competition_season_id;
    }

    public String getCompetition_country() {
        return competition_country;
    }

    public void setCompetition_country(String competition_country) {
        this.competition_country = competition_country;
    }

    public boolean isCompetition_friendly() {
        return competition_friendly;
    }

    public void setCompetition_friendly(boolean competition_friendly) {
        this.competition_friendly = competition_friendly;
    }

    public boolean isCompetition_cup() {
        return competition_cup;
    }

    public void setCompetition_cup(boolean competition_cup) {
        this.competition_cup = competition_cup;
    }

    public String getCompetition_predictability() {
        return competition_predictability;
    }

    public void setCompetition_predictability(String competition_predictability) {
        this.competition_predictability = competition_predictability;
    }

    public Double getLatest_1xbet_odds() {
        return latest_1xbet_odds;
    }

    public void setLatest_1xbet_odds(Double latest_1xbet_odds) {
        this.latest_1xbet_odds = latest_1xbet_odds;
    }

    public Double getOpening_1xbet_odds() {
        return opening_1xbet_odds;
    }

    public void setOpening_1xbet_odds(Double opening_1xbet_odds) {
        this.opening_1xbet_odds = opening_1xbet_odds;
    }

    public boolean isValue_1xbet() {
        return isValue_1xbet;
    }

    public void setIsValue_1xbet(boolean value_1xbet) {
        isValue_1xbet = value_1xbet;
    }

    public Double getValue_1xbet() {
        return value_1xbet;
    }

    public void setValue_1xbet(Double value_1xbet) {
        this.value_1xbet = value_1xbet;
    }

    public Double getLatest_pinnacle_odds() {
        return latest_pinnacle_odds;
    }

    public void setLatest_pinnacle_odds(Double latest_pinnacle_odds) {
        this.latest_pinnacle_odds = latest_pinnacle_odds;
    }

    public Double getOpening_pinnacle_odds() {
        return opening_pinnacle_odds;
    }

    public void setOpening_pinnacle_odds(Double opening_pinnacle_odds) {
        this.opening_pinnacle_odds = opening_pinnacle_odds;
    }

    public boolean isValue_pinnacle() {
        return isValue_pinnacle;
    }

    public void setIsValue_pinnacle(boolean value_pinnacle) {
        isValue_pinnacle = value_pinnacle;
    }

    public Double getValue_pinnacle() {
        return value_pinnacle;
    }

    public void setValue_pinnacle(Double value_pinnacle) {
        this.value_pinnacle = value_pinnacle;
    }

    public Double getLatest_b365_odds() {
        return latest_b365_odds;
    }

    public void setLatest_b365_odds(Double latest_b365_odds) {
        this.latest_b365_odds = latest_b365_odds;
    }

    public Double getOpening_b365_odds() {
        return opening_b365_odds;
    }

    public void setOpening_b365_odds(Double opening_b365_odds) {
        this.opening_b365_odds = opening_b365_odds;
    }

    public boolean isValue_b365() {
        return isValue_b365;
    }

    public void setIsValue_b365(boolean value_b365) {
        isValue_b365 = value_b365;
    }

    public Double getValue_b365() {
        return value_b365;
    }

    public void setValue_b365(Double value_b365) {
        this.value_b365 = value_b365;
    }

    public Double getBest_odds() {
        return latest_odds;
    }

    public void setLatest_odds(Double latest_odds) {
        this.latest_odds = latest_odds;
    }

    public Double getOur_odds() {
        return our_odds;
    }

    public void setOur_odds(Double our_odds) {
        this.our_odds = our_odds;
    }

    public Double getValue_percentage() {
        return value_percentage;
    }

    public void setValue_percentage(Double value) {
        this.value_percentage = value;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Nullable
    public Double getPeak_odds() {
        return peak_odds;
    }

    public void setPeak_odds(@Nullable Double peak_odds) {
        this.peak_odds = peak_odds;
    }

    @Nullable
    public Double getPeak_1xbet_odds() {
        return peak_1xbet_odds;
    }

    public void setPeak_1xbet_odds(@Nullable Double peak_1xbet_odds) {
        this.peak_1xbet_odds = peak_1xbet_odds;
    }

    public void setValue_1xbet(boolean value_1xbet) {
        isValue_1xbet = value_1xbet;
    }

    @Nullable
    public Double getPeak_pinnacle_odds() {
        return peak_pinnacle_odds;
    }

    public void setPeak_pinnacle_odds(@Nullable Double peak_pinnacle_odds) {
        this.peak_pinnacle_odds = peak_pinnacle_odds;
    }

    public void setValue_pinnacle(boolean value_pinnacle) {
        isValue_pinnacle = value_pinnacle;
    }

    @Nullable
    public Double getPeak_b365_odds() {
        return peak_b365_odds;
    }

    public void setPeak_b365_odds(@Nullable Double peak_b365_odds) {
        this.peak_b365_odds = peak_b365_odds;
    }

    public void setValue_b365(boolean value_b365) {
        isValue_b365 = value_b365;
    }

    @Nullable
    public Double getLatest_odds() {
        return latest_odds;
    }
}

package com.oa.api.model;

public class Competition {

    private long id;
    private String name;
    private String season;
    private long season_id;
    private String country;
    private boolean is_cup;
    private boolean is_friendly;
    private String predictability;

    public Competition(long id, String name, String season, long season_id, String country, boolean is_cup, boolean is_friendly, String predictability) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.season_id = season_id;
        this.country = country;
        this.is_cup = is_cup;
        this.is_friendly = is_friendly;
        this.predictability = predictability;
    }

    public Competition(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public long getSeason_id() {
        return season_id;
    }

    public void setSeason_id(long season_id) {
        this.season_id = season_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isIs_cup() {
        return is_cup;
    }

    public void setIs_cup(boolean is_cup) {
        this.is_cup = is_cup;
    }

    public boolean isIs_friendly() {
        return is_friendly;
    }

    public void setIs_friendly(boolean is_friendly) {
        this.is_friendly = is_friendly;
    }

    public String getPredictability() {
        return predictability;
    }

    public void setPredictability(String predictability) {
        this.predictability = predictability;
    }
}

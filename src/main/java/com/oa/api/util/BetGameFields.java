package com.oa.api.util;

public enum BetGameFields {

    ID("id"), //integer
    MARKET("market"),
    HOME_NAME("home_name"),
    AWAY_NAME("away_name"),
    STATUS("status"),
    UNIX("unix"), //integer
    KO_HUMAN("ko_human"),
    HOME_GOALS("home_goals"), //integer
    AWAY_GOALS("away_goals"), //integer
    HOME_PLAYED("home_played"), //integer
    AWAY_PLAYED("away_played"), //integer
    CORNERS("corners"), //integer
    COMPETITION("competition"), //linkedhashmap
    ODDS("odds"), //arraylist
    LATEST_ODDS("latest_odds"),
    PEAK_ODDS("peak_odds"),
    OUR_ODDS("our_odds"), //double
    VALUE("value"), //double
    PROBABILITY("probability"), //double
    RESULT("result"), //hashmap

    //RESULT HASHMAP
    SCORE("score"), //key = result; value = linkedhashmap -> result, score

    //COMPETITION HASHMAP
    //key = competition; value = linkedhashmap (keys->) id, name, season, season_id, country, is_cup, is_friendly, predictability
    COMPETITION_ID("id"), //integer
    COMPETITION_NAME("name"),
    COMPETITION_SEASON("season"),
    COMPETITION_SEASON_ID("season_id"), //integer
    COMPETITION_COUNTRY("country"),
    COMPETITION_IS_CUP("is_cup"), //boolean
    COMPETITION_IS_FRIENDLY("is_friendly"), //boolean
    COMPETITION_PREDICTABILITY("predictability"),
    COMPETITION_PROGRESS("progress"),

    //BOOKMAKER HASHMAP
    BOOKMAKER_ID("bookmaker_id"),
    BOOKMAKER_NAME("bookmaker_name"),
    BOOKMAKER_LATEST_ODDS("latest"),
    BOOKMAKER_OPENING_ODDS("opening"),
    BOOKMAKER_PEAK_ODDS("peak"),
    BOOKMAKER_IS_VALUE("is_value"),
    BOOKMAKER_VALUE("value");


    private String fieldName;

    BetGameFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

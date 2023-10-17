package com.oa.api.util;

import java.util.Arrays;

public enum Market {
    HOME_WIN("1", "home_win_probability"),
    AWAY_WIN("2", "away_win_probability"),
    DRAW("3", "draw_probability"),
    UNDER_25("4", "u25_probability"),
    OVER_25("5", "o25_probability"),
    BTTS("6", "btts_probability"),
    OVER_85_CORNERS("7", "o85_corners_probability"),
    UNDER_35("8", "u35_probability"),
    OVER_35("9", "o35_probability"),
    HOME_O15("10", "home_goals_15_probability"),
    AWAY_O15("11", "away_goals_15_probability"),
    OVER_15("12", "o15_probability"),
    UNDER_15("13", "u15_probability")
    ;

    private String id;
    private String name;

    Market(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Market getIdByName(String name){
        return Arrays.stream(Market.values()).filter(market -> market.getName().equals(name)).findFirst().orElse(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.oa.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MarketMapper {

    U25("u25_probability", "Under 2.5", "u25", "under_25"),
    U35("u35_probability", "Under 3.5", "u35", "under_35"),
    O25("o25_probability", "Over 2.5", "o25", "over_25"),
    HOME("home_win_probability", "Home Win", "homeWin", "home"),
    AWAY("away_win_probability", "Away Win", "awayWin", "away"),
    DRAW("draw_probability", "Draw", "draw", "draw"),
    BTTS("btts_probability", "BTTS", "btts", "btts")
    ;

    MarketMapper(String key, String name, String abbv, String shortName) {
        this.abbv = abbv;
        this.key = key;
        this.name = name;
        this.shortName = shortName;
    }

    private String key;
    private String name;
    private String abbv;

    private String shortName;

    public String getAbbv() {
        return abbv;
    }

    public void setAbbv(String abbv) {
        this.abbv = abbv;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByKey(String key){
        List<MarketMapper> result = Arrays.stream(MarketMapper.values()).filter(marketMapper -> marketMapper.getKey().equals(key)).collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0).getName() : "";
    }

    public static String getNameByShortName(String shortName){
        List<MarketMapper> result = Arrays.stream(MarketMapper.values()).filter(marketMapper -> marketMapper.getShortName().equals(shortName)).collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0).getName() : "";
    }

    public static String getAbbvBykey(String key){
        List<MarketMapper> result = Arrays.stream(MarketMapper.values()).filter(marketMapper -> marketMapper.getKey().equals(key)).collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0).getAbbv() : "";
    }

    public static String getKeyByName(String name){
        List<MarketMapper> result = Arrays.stream(MarketMapper.values()).filter(marketMapper -> marketMapper.getName().equals(name)).collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0).getKey() : "";
    }

    public String getShortName() {
        return shortName;
    }
}

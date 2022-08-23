package com.oa.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MarketMapper {

    U25("u25_probability", "Under 2.5"),
    O25("o25_probability", "Over 2.5"),
    HOME("home_win_probability", "Home Win"),
    AWAY("away_win_probability", "Away Win"),
    DRAW("draw_probability", "Draw"),
    BTTS("btts_probability", "BTTS")
    ;

    MarketMapper(String key, String name) {
        this.key = key;
        this.name = name;
    }

    private String key;
    private String name;

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

    public static String getKeyByName(String name){
        List<MarketMapper> result = Arrays.stream(MarketMapper.values()).filter(marketMapper -> marketMapper.getName().equals(name)).collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0).getKey() : "";
    }
}

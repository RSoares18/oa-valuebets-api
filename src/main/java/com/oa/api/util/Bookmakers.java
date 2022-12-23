package com.oa.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Bookmakers {

    ONEXBET(3,"1xBet"),
    PINNACLE (1, "Pinnacle"),
    BET365(2, "Bet365");

    private long id;
    private String name;

    Bookmakers(long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public static Long getIdByName(String name){
        List<Bookmakers> result = Arrays.stream(Bookmakers.values()).filter(bookie -> bookie.getName().equals(name)).collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0).getId() : 0L;
    }
}

package com.oa.api.model;

public class RegisteredBet {

    private String id;
    private String market;

    public RegisteredBet(String id) {
        this.id = id;
    }

    public RegisteredBet(String id, String market) {
        this.id = id;
        this.market = market;
    }

    public RegisteredBet() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}

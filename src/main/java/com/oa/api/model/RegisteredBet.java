package com.oa.api.model;

public class RegisteredBet {

    private String id;
    private String market;
    private Long unix;

    public Long getUnix() {
        return unix;
    }

    public void setUnix(Long unix) {
        this.unix = unix;
    }

    public RegisteredBet(String id) {
        this.id = id;
    }

    public RegisteredBet(String id, String market) {
        this.id = id;
        this.market = market;
    }

    public RegisteredBet(String id, String market, Long unix) {
        this.id = id;
        this.market = market;
        this.unix = unix;
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

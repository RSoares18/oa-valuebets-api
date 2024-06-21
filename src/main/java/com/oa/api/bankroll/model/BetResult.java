package com.oa.api.bankroll.model;

public class BetResult {

    private String text;
    private double profit;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}

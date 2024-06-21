package com.oa.api.bankroll;

public enum BetPlacedFields {
    FIXTURE_NAME("fixture_name"),
    MARKET_TITLE("market_title"),
    STAKE ("stake"),
    BET("bet"),
    ODDS ("odds"),
    DATE("date"),
    RESULT("result"),
    STATUS("text"),
    PROFIT("profit"),
    ;


    BetPlacedFields(String fieldName) {
        this.fieldName = fieldName;
    }

    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

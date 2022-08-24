package com.oa.api.model;

public class Odd {

    private long bookmaker_id;
    private String bookmaker_name;
    private double latest;
    private double opening;
    private double peak;
    private boolean is_value;
    private double value;

    public Odd(long bookmaker_id, String bookmaker_name, double latest, double opening, boolean is_value, double value, double peak) {
        this.bookmaker_id = bookmaker_id;
        this.bookmaker_name = bookmaker_name;
        this.latest = latest;
        this.opening = opening;
        this.is_value = is_value;
        this.value = value;
    }

    public Odd(){}

    public double getPeak() {
        return peak;
    }

    public void setPeak(double peak) {
        this.peak = peak;
    }

    public long getBookmaker_id() {
        return bookmaker_id;
    }

    public void setBookmaker_id(long bookmaker_id) {
        this.bookmaker_id = bookmaker_id;
    }

    public String getBookmaker_name() {
        return bookmaker_name;
    }

    public void setBookmaker_name(String bookmaker_name) {
        this.bookmaker_name = bookmaker_name;
    }

    public double getLatest() {
        return latest;
    }

    public void setLatest(double latest) {
        this.latest = latest;
    }

    public double getOpening() {
        return opening;
    }

    public void setOpening(double opening) {
        this.opening = opening;
    }

    public boolean isIs_value() {
        return is_value;
    }

    public void setIs_value(boolean is_value) {
        this.is_value = is_value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

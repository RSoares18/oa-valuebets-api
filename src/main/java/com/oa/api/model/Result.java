package com.oa.api.model;

public class Result {

    private boolean result;
    private String score;

    public Result(){

    }

    public Result(boolean result, String score) {
        this.result = result;
        this.score = score;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

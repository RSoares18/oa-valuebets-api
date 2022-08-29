package com.oa.api.util;

import com.oa.api.model.FilterRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpcomingRequests {

    private List<FilterRequest> allRequests;

    public UpcomingRequests(){
        allRequests = Arrays.asList(createOversRequest(), createUndersRequest(), createHomeWinsRequest(), createAwayWinsRequest());
    }

    public FilterRequest createOversRequest(){
        FilterRequest oversRequest = new FilterRequest();
        oversRequest.setMarket("o25_probability");
        oversRequest.setBookie("1xBet");
        oversRequest.setCountCups(false);
        oversRequest.setCountFriendlies(false);
        oversRequest.setMinOdds(1.50);
        oversRequest.setMaxOdds(10.00);
        oversRequest.setMinProbability(55.00);
        oversRequest.setMaxValue(10000.00);
        oversRequest.setMinValue(10.00);
        oversRequest.setKellyFactor(0.00);
        oversRequest.setMinGamesPlayed(0);
        return oversRequest;
    }

    public FilterRequest createUndersRequest(){
        FilterRequest undersRequest = new FilterRequest();
        undersRequest.setMarket("u25_probability");
        undersRequest.setBookie("1xBet");
        undersRequest.setCountCups(false);
        undersRequest.setCountFriendlies(false);
        undersRequest.setMinOdds(2.70);
        undersRequest.setMaxOdds(10.00);
        undersRequest.setMinProbability(30.00);
        undersRequest.setMaxValue(10000.00);
        undersRequest.setMinValue(20.00);
        undersRequest.setKellyFactor(0.00);
        undersRequest.setMinGamesPlayed(3);
        return undersRequest;

    }

    public FilterRequest createHomeWinsRequest(){
        FilterRequest homeWinRequest = new FilterRequest();
        homeWinRequest.setMarket("home_win_probability");
        homeWinRequest.setBookie("1xBet");
        homeWinRequest.setCountCups(false);
        homeWinRequest.setCountFriendlies(false);
        homeWinRequest.setMinOdds(1.70);
        homeWinRequest.setMaxOdds(10.00);
        homeWinRequest.setMinProbability(0.00);
        homeWinRequest.setMaxValue(10000.00);
        homeWinRequest.setMinValue(0.00);
        homeWinRequest.setKellyFactor(0.24);
        homeWinRequest.setMinGamesPlayed(3);
        return homeWinRequest;

    }

    public FilterRequest createAwayWinsRequest(){
        FilterRequest awayWinRequest = new FilterRequest();
        awayWinRequest.setMarket("away_win_probability");
        awayWinRequest.setBookie("1xBet");
        awayWinRequest.setCountCups(false);
        awayWinRequest.setCountFriendlies(false);
        awayWinRequest.setMinOdds(1.70);
        awayWinRequest.setMaxOdds(10.00);
        awayWinRequest.setMinProbability(0.00);
        awayWinRequest.setMaxValue(10000.00);
        awayWinRequest.setMinValue(0.00);
        awayWinRequest.setKellyFactor(0.24);
        awayWinRequest.setMinGamesPlayed(3);
        return awayWinRequest;

    }

    public List<FilterRequest> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<FilterRequest> allRequests) {
        this.allRequests = allRequests;
    }
}

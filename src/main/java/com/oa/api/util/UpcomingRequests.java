package com.oa.api.util;

import com.oa.api.model.FilterRequest;

import java.util.Arrays;
import java.util.List;

public class UpcomingRequests {

    private List<FilterRequest> allRequests;

    public UpcomingRequests(){
        FilterRequest oversRequest = new FilterRequest("Overs", 1,"1xBet", "o25_probability", 1.55, 1.79,45.00,0.00,10000.00,0.10,false, false,90);
        FilterRequest oldUndersRequest = new FilterRequest("Unders 2.70-30-20", 3,"Bet365", "u25_probability", 2.70, 50.00,30.00,20.00,10000.00,0.00,false, false,90);
        FilterRequest undersRequest = new FilterRequest("Unders 85% Odds", 3,"Bet365", "u25_probability", 2.00, 50.00,30.00,35.00,10000.00,0.00,false, false,90);
        FilterRequest undersRequestPinnacle = new FilterRequest("Unders Pinnacle", 0,"Pinnacle", "u25_probability", 1.50, 50.00,30.00,0.00,10000.00,0.15,false, false,90);
        FilterRequest homeWinsRequest = new FilterRequest("Home Wins 2.50-40-0.20", 3,"Bet365", "home_win_probability", 2.50, 50.00,40.00,0.00,10000.00,0.20,false, false,90);
        FilterRequest homeWinsRequest2 = new FilterRequest("Home Wins 2.30-30-0.15", 3,"Bet365", "home_win_probability", 2.30, 50.00,30.00,0.00,10000.00,0.15,false, false,90);
        FilterRequest homeWinsRequestPinnacle = new FilterRequest("Home Wins Pinnacle",3,"Pinnacle", "home_win_probability", 2.30, 50.00,30.00,0.00,10000.00,0.15,false, false,90);
        FilterRequest awayWinsRequest = new FilterRequest("Away Wins",2,"1xBet", "away_win_probability", 2.50, 50.00,40.00,0.00,10000.00,0.20,false, false,90);
        allRequests = Arrays.asList(oldUndersRequest, undersRequestPinnacle, homeWinsRequest2, homeWinsRequest, homeWinsRequestPinnacle);
    }

    public List<FilterRequest> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<FilterRequest> allRequests) {
        this.allRequests = allRequests;
    }
}

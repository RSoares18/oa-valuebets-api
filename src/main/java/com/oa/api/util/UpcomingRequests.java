package com.oa.api.util;

import com.oa.api.model.FilterRequest;

import java.util.Arrays;
import java.util.List;

public class UpcomingRequests {

    private List<FilterRequest> allRequests;

    public UpcomingRequests(){
        FilterRequest oversRequest = new FilterRequest(1,"1xBet", "o25_probability", 1.55, 1.79,45.00,0.00,10000.00,0.10,false, false,90);
        FilterRequest undersRequest = new FilterRequest(3,"Bet365", "u25_probability", 2.70, 50.00,30.00,20.00,10000.00,0.00,false, false,90);
        FilterRequest undersRequestPinnacle = new FilterRequest(0,"Pinnacle", "u25_probability", 1.75, 50.00,30.00,0.00,10000.00,0.15,false, false,90);
        FilterRequest homeWinsRequest = new FilterRequest(3,"Bet365", "home_win_probability", 2.50, 50.00,40.00,0.00,10000.00,0.20,false, false,90);
        FilterRequest homeWinsRequestPinnacle = new FilterRequest(3,"Pinnacle", "home_win_probability", 2.30, 50.00,30.00,0.00,10000.00,0.15,false, false,90);
        FilterRequest awayWinsRequest = new FilterRequest(2,"1xBet", "away_win_probability", 2.50, 50.00,40.00,0.00,10000.00,0.20,false, false,90);
        allRequests = Arrays.asList(undersRequest, undersRequestPinnacle, homeWinsRequest, homeWinsRequestPinnacle);
    }

    public List<FilterRequest> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<FilterRequest> allRequests) {
        this.allRequests = allRequests;
    }
}

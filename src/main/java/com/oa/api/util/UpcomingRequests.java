package com.oa.api.util;

import com.oa.api.model.FilterRequest;

import java.util.Arrays;
import java.util.List;

public class UpcomingRequests {

    private List<FilterRequest> allRequests;

    public UpcomingRequests(){

        FilterRequest unders35RequestPinnacle = new FilterRequest("Unders 3.5 Pinnacle", 3,"Pinnacle", "u35_probability", 1.50, 50.00,30.00,0.00,10000.00,0.15,0.15,false, false,90,"medium,good,high");
        FilterRequest unders35Request1xBet = new FilterRequest("Unders 3.5 1X", 3,"1xBet", "u35_probability", 1.50, 50.00,30.00,0.00,10000.00,0.15,0.15,false, false,90,"medium,good,high");
        FilterRequest unders35Request365 = new FilterRequest("Unders 3.5 365", 3,"Bet365", "u35_probability", 1.50, 50.00,30.00,0.00,10000.00,0.15,0.15,false, false,90,"medium,good,high");
        FilterRequest homeWinsRequest1x = new FilterRequest("Home Wins 2.50-30-0.20", 2,"1xBet", "home_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,false, false,100,"good,high");
        FilterRequest homeWinsRequestLow1x = new FilterRequest("Home Wins 1.50-30-0.35", 2,"1xBet", "home_win_probability", 1.50, 2.49,30.00,0.00,10000.00,0.35,0.35,false, false,100,"good,high");
        FilterRequest homeWinsRequestLowPinny = new FilterRequest("Home Wins 1.50-30-0.35", 2,"Pinnacle", "home_win_probability", 1.50, 2.49,30.00,0.00,10000.00,0.35,0.35,false, false,100,"good,high");
        FilterRequest homeWinsRequest2 = new FilterRequest("Home Wins 2.50-30-0.20", 2,"Bet365", "home_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,false, false,100,"good,high");
        FilterRequest homeWinsRequestPinnacle = new FilterRequest("Home Wins Pinnacle",2,"Pinnacle", "home_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,false, false,100,"good,high");
        FilterRequest awayWinsRequest1x = new FilterRequest("Away Wins 2.50-30-0.20", 3,"1xBet", "away_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,false, false,100,"good,high");
        FilterRequest awayWinsRequest2 = new FilterRequest("away Wins 2.50-30-0.20", 3,"Bet365", "away_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,false, false,100,"good,high");
        FilterRequest awayWinsRequestPinnacle = new FilterRequest("Away Wins Pinnacle",3,"Pinnacle", "away_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,false, false,100,"good,high");
        allRequests = Arrays.asList(unders35Request1xBet,unders35RequestPinnacle,unders35Request365, homeWinsRequest2, homeWinsRequest1x, homeWinsRequestPinnacle, awayWinsRequest1x, awayWinsRequest2, awayWinsRequestPinnacle);
    }

    public List<FilterRequest> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<FilterRequest> allRequests) {
        this.allRequests = allRequests;
    }
}

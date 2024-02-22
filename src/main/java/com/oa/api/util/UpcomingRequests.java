package com.oa.api.util;

import com.oa.api.model.FilterRequest;

import java.util.Arrays;
import java.util.List;

public class UpcomingRequests {

    private List<FilterRequest> allRequests;

    public UpcomingRequests(){

        FilterRequest bttsRequestPinnacle = new FilterRequest("BTTS Pinnacle", 0,"Pinnacle", "btts_probability", 1.50, 50.00,20.00,0.00,10000.00,0.10,0.09,true, true,100,"poor,medium,good,high");
        FilterRequest bttsRequest1xBet = new FilterRequest("BTTS 1X", 0,"1xBet", "btts_probability", 2.00, 50.00,20.00,0.00,10000.00,0.10,0.09,true, true,100,"poor,medium,good,high");
        FilterRequest unders35RequestPinnacle = new FilterRequest("Unders 3.5 Pinnacle", 3,"Pinnacle", "u35_probability", 1.50, 50.00,30.00,0.00,10000.00,0.15,0.15,true, true,90,"medium,good,high");
        FilterRequest unders35Request1xBet = new FilterRequest("Unders 3.5 1X", 3,"1xBet", "u35_probability", 1.50, 50.00,30.00,0.00,10000.00,0.15,0.15,true, true,90,"medium,good,high");
        FilterRequest homeWinsRequest1x = new FilterRequest("Home Wins 2.50-30-0.20", 2,"1xBet", "home_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,true, true,100,"good,high");
        FilterRequest homeWinsRequest2 = new FilterRequest("Home Wins 2.50-30-0.20", 2,"Bet365", "home_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,true, true,100,"good,high");
        FilterRequest homeWinsRequestPinnacle = new FilterRequest("Home Wins Pinnacle",2,"Pinnacle", "home_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,true, true,100,"good,high");
        FilterRequest homeWinsLowRequest1x = new FilterRequest("Home Wins 1.50-30-0.35", 2,"1xBet", "home_win_probability", 1.50, 2.49,30.00,0.00,10000.00,0.35,0.35,true, true,100,"medium,good,high");
        FilterRequest homeWinsLowRequest2 = new FilterRequest("Home Wins 1.50-30-0.35", 2,"Bet365", "home_win_probability", 1.50, 2.49,30.00,0.00,10000.00,0.35,0.35,true, true,100,"medium,good,high");
        FilterRequest homeWinsLowRequestPinnacle = new FilterRequest("Home Wins Pinnacle 1.50-30-0.35",2,"Pinnacle", "home_win_probability", 1.50, 2.49,30.00,0.00,10000.00,0.35,0.35,true, true,100,"medium,good,high");
        FilterRequest awayWinsRequest1x = new FilterRequest("Away Wins 2.50-30-0.20", 3,"1xBet", "away_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,true, true,100,"good,high");
        FilterRequest awayWinsRequest2 = new FilterRequest("away Wins 2.50-30-0.20", 3,"Bet365", "away_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,true, true,100,"good,high");
        FilterRequest awayWinsRequestPinnacle = new FilterRequest("Away Wins Pinnacle",3,"Pinnacle", "away_win_probability", 2.50, 50.00,30.00,0.00,10000.00,0.20,0.20,true, true,100,"good,high");
        allRequests = Arrays.asList(bttsRequestPinnacle, bttsRequest1xBet,unders35Request1xBet,unders35RequestPinnacle, homeWinsLowRequest1x, homeWinsLowRequest2, homeWinsLowRequestPinnacle,homeWinsRequest2, homeWinsRequest1x, homeWinsRequestPinnacle, awayWinsRequest1x, awayWinsRequest2, awayWinsRequestPinnacle);
    }

    public List<FilterRequest> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<FilterRequest> allRequests) {
        this.allRequests = allRequests;
    }
}
